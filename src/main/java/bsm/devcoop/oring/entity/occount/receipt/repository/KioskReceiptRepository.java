package bsm.devcoop.oring.entity.occount.receipt.repository;

import bsm.devcoop.oring.entity.occount.receipt.KioskReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface KioskReceiptRepository extends JpaRepository<KioskReceipt, Long> {
    List<KioskReceipt> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}
