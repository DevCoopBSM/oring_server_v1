package bsm.devcoop.oring.entity.occount.transaction.pay.repository;

import bsm.devcoop.oring.entity.occount.transaction.pay.PayLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayLogRepository extends JpaRepository<PayLog, Long> {
    List<PayLog> findAllByUserCode(String userCode);
    List<PayLog> findAllByUserCodeOrderByPayDateDesc(String userCode);
}