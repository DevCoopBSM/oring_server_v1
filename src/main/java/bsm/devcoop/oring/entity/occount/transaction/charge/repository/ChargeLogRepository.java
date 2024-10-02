package bsm.devcoop.oring.entity.occount.transaction.charge.repository;

import bsm.devcoop.oring.entity.occount.transaction.charge.ChargeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChargeLogRepository extends JpaRepository<ChargeLog, Long> {

    // 사용자 코드로 모든 ChargeLog 조회
    List<ChargeLog> findAllByUserCode(String userCode);

    List<ChargeLog> findAllByUserCodeOrderByChargeDateDesc(String userCode);

    // PaymentId로 ChargeLog 조회
    Optional<ChargeLog> findByPaymentId(String paymentId);

    // 특정 사용자 코드와 날짜 범위, 충전 타입으로 ChargeLog 조회
    @Query("SELECT c FROM ChargeLog c WHERE c.userCode = :userCode AND c.chargeDate BETWEEN :startDate AND :endDate AND c.chargeType = :chargeType")
    List<ChargeLog> findByUserCodeAndDateRangeAndChargeType(
        @Param("userCode") String userCode,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("chargeType") String chargeType
    );
    // 특정 사용자 코드와 날짜 범위, 충전 타입, 그리고 refundState가 NULL인 경우의 ChargeLog 조회
    List<ChargeLog> findAllByUserCodeAndChargeDateBetweenAndChargeTypeInAndRefundStateIsNull(
        String userCode, LocalDateTime start, LocalDateTime end, List<String> chargeTypes);

}