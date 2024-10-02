package bsm.devcoop.oring.entity.occount.transaction.service;

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
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LogService {
    private final ChargeLogRepository chargeLogRepository;
    private final PayLogRepository payLogRepository;
    private final UserRepository userRepository;

    public void saveChargeLog(ChargeLog chargeLog) {
        chargeLogRepository.save(chargeLog);
    }

    public void savePayLog(PayLog payLog) {
        payLogRepository.save(payLog);
    }

    public List<ChargeLog> getAllChargeLog(String userCode) {
        return chargeLogRepository.findAllByUserCodeOrderByChargeDateDesc(userCode);
    }

    public List<PayLog> getAllPayLog(String userCode) {
        return payLogRepository.findAllByUserCodeOrderByPayDateDesc(userCode);
    }

    public List<ChargeLog> getAllChargeLogByEmail(String userEmail) throws GlobalException {
        User user = userRepository.findByUserEmail(userEmail);

        if (user == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND, "Can not found User : " + userEmail);
        }

        return chargeLogRepository.findAllByUserCodeOrderByChargeDateDesc(user.getUserCode());
    }

    @Transactional
    public List<PayLog> getAllPayLogByEmail(String userEmail) throws GlobalException {
        User user = userRepository.findByUserEmail(userEmail);

        if (user == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND, "Can not found User : " + userEmail);
        }

        return payLogRepository.findAllByUserCodeOrderByPayDateDesc(user.getUserCode());
    }

    // 당일 충전량 계산
    @Transactional
    public int getTotalOnlineChargeToday(User user) {
        // 한국 시간대 기준으로 당일의 시작과 끝 시간 계산
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime startOfDayKST = LocalDate.now(koreaZone).atStartOfDay(koreaZone);
        ZonedDateTime endOfDayKST = LocalDate.now(koreaZone).atTime(LocalTime.MAX).atZone(koreaZone);
    
        // UTC로 변환
        ZonedDateTime startOfDayUTC = startOfDayKST.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endOfDayUTC = endOfDayKST.withZoneSameInstant(ZoneOffset.UTC);
    
        // ZonedDateTime에서 LocalDateTime으로 변환 (SQL 쿼리에서는 시간대 정보가 없으므로 LocalDateTime을 사용)
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

