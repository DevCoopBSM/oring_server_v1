package bsm.devcoop.oring.entity.occount.receipt.repository;


import bsm.devcoop.oring.entity.occount.receipt.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

    List<Receipt> findAllByItemIdAndSaleDate(int itemId, LocalDate saleDate);

    @Query("SELECT COALESCE(SUM(r.saleQty), 0) FROM Receipt AS r WHERE r.itemId = :itemId and r.saleDate > :saleDate")
    int findSumQuantity(@Param("itemId") int itemId, @Param("saleDate") LocalDate saleDate);
}