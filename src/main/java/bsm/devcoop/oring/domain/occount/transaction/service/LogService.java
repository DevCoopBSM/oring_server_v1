package bsm.devcoop.oring.domain.occount.transaction.service;

import bsm.devcoop.oring.domain.occount.transaction.presentation.dto.LogDto;
import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.entity.account.user.repository.UserRepository;
import bsm.devcoop.oring.entity.occount.transaction.charge.ChargeLog;
import bsm.devcoop.oring.entity.occount.transaction.charge.repository.ChargeLogRepository;
import bsm.devcoop.oring.entity.occount.transaction.pay.PayLog;
import bsm.devcoop.oring.entity.occount.transaction.pay.repository.PayLogRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class LogService {
    private final ChargeLogRepository chargeLogRepository;
    private final PayLogRepository payLogRepository;
    private final UserRepository userRepository;

    public List<ChargeLog> getAllChargeLog(String userCode) {
        return chargeLogRepository.findAllByUserCodeOrderByChargeDateDesc(userCode);
    }

    public List<PayLog> getAllPayLog(String userCode) {
        return payLogRepository.findAllByUserCodeOrderByPayDateDesc(userCode);
    }

    public List<ChargeLog> getAllChargeLogByEmail(String userEmail) throws GlobalException {
        try {
            User user = userRepository.findByUserEmail(userEmail);
            return this.getAllChargeLog(user.getUserCode());
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }
    }

    public List<PayLog> getAllPayLogByEmail(String userEmail) throws GlobalException {
        try {
            User user = userRepository.findByUserEmail(userEmail);
            return this.getAllPayLog(user.getUserCode());
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }
    }

    public LogDto.LogResponse getAllChargeAndPayLog(String userEmail) throws GlobalException {
        List<ChargeLog> chargeLogList = this.getAllChargeLogByEmail(userEmail);
        List<PayLog> payLogList = this.getAllPayLogByEmail(userEmail);

        List<LogDto.Log> chargeLogDtoList = chargeLogList.stream()
                .map(chargeLog -> {
                    return LogDto.Log.builder()
                            .logType(LogDto.LogType.CHARGE_LOG)
                            .logName(chargeLog.getChargeType())
                            .logPrice(chargeLog.getChargedPoint())
                            .logAfterPoint(chargeLog.getAfterPoint())
                            .logDateTime(chargeLog.getChargeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build();
                }).toList();

        List<LogDto.Log> payLogDtoList = payLogList.stream()
                .map(payLog -> {
                    return LogDto.Log.builder()
                            .logType(LogDto.LogType.PAY_LOG)
                            .logName(payLog.getPayType())
                            .logPrice(payLog.getPayedPoint())
                            .logAfterPoint(payLog.getAfterPoint())
                            .logDateTime(payLog.getPayDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build();
                }).toList();

        Map<String, List<LogDto.Log>> sortedLogMap = Stream.concat(chargeLogDtoList.stream(), payLogDtoList.stream())
                .collect(Collectors.groupingBy(log -> log.getLogDateTime().split(" ")[0],
                        LinkedHashMap::new, // 순서 유지
                        Collectors.toList()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, List<LogDto.Log>>comparingByKey().reversed()) // 키 기준 내림차순 정렬
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // 순서 유지
                ));

        LogDto.LogResponse response = LogDto.LogResponse.builder()
                .logMap(sortedLogMap)
                .build();

        return response;
    }

    // 당일 충전량 계산
    public int getTotalOnlineChargeToday(User user) {
        // 한국 시간대 기준으로 당일의 시작과 끝 시간 계산
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime startOfDayKST = LocalDate.now(koreaZone).atStartOfDay(koreaZone);
        ZonedDateTime endOfDayKST = LocalDate.now(koreaZone).atTime(LocalTime.MAX).atZone(koreaZone);

        // UTC 로 변환
        ZonedDateTime startOfDayUTC = startOfDayKST.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endOfDayUTC = endOfDayKST.withZoneSameInstant(ZoneOffset.UTC);

        // ZonedDateTime 에서 LocalDateTime 으로 변환 (SQL 쿼리에서는 시간대 정보가 없으므로 LocalDateTime 을 사용)
        LocalDateTime startOfDay = startOfDayUTC.toLocalDateTime();
        LocalDateTime endOfDay = endOfDayUTC.toLocalDateTime();

        // 당일 온라인 충전 내역 조회
        List<ChargeLog> todayChargeLogs = chargeLogRepository.findAllByUserCodeAndChargeDateBetweenAndChargeTypeInAndRefundStateIsNull(
            user.getUserCode(), startOfDay, endOfDay, List.of("2", "3"));

        // 충전된 포인트 합산
        return todayChargeLogs.stream()
            .mapToInt(ChargeLog::getChargedPoint)
            .sum();
    }
}

