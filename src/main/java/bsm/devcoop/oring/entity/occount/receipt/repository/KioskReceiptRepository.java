package bsm.devcoop.oring.entity.occount.receipt.repository;

import bsm.devcoop.oring.entity.occount.receipt.KioskReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface KioskReceiptRepository extends JpaRepository<KioskReceipt, Long> {
    List<KioskReceipt> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(k.saleQty), 0) FROM KioskReceipt AS k WHERE k.itemCode = :itemCode and k.saleDate > :saleDate")
    int findSumQuantity(@Param("itemCode") String itemCode, @Param("saleDate") LocalDate saleDate);
}
