package bsm.devcoop.oring.entity.occount.receipt.repository;


import bsm.devcoop.oring.entity.occount.receipt.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

    List<Receipt> findAllByItemIdAndSaleDate(int itemId, LocalDate saleDate);
}