package bsm.devcoop.oring.domain.occount.receipt.service;

import bsm.devcoop.oring.domain.occount.item.service.ItemService;
import bsm.devcoop.oring.domain.occount.receipt.presentation.dto.ReceiptDto;
import bsm.devcoop.oring.entity.occount.receipt.KioskReceipt;
import bsm.devcoop.oring.entity.occount.receipt.Receipt;
import bsm.devcoop.oring.entity.occount.receipt.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final ItemService itemService;

    @Transactional(readOnly = true)
    public int getReceiptsSoldQty(int itemId, LocalDate today) {
        return receiptRepository.findAllByItemIdAndSaleDate(itemId, today).stream()
                .mapToInt(Receipt::getSaleQty)
                .sum();
    }

    private void addOrUpdateSumReceipt(
            Map<Integer, ReceiptDto.SumReceipt> combine,
            int itemId, String itemName, int saleQty, LocalDate saleDate
    ) {
        combine.compute(itemId, (key, existingReceipt) -> {
            if (existingReceipt == null) {
                return ReceiptDto.SumReceipt.builder()
                        .itemId(itemId)
                        .itemName(itemName)
                        .saleQty(saleQty)
                        .lastUpdated(saleDate)
                        .build();
            } else {
                existingReceipt.addSaleQty(saleQty);
                if (saleDate.isAfter(existingReceipt.getLastUpdated())) {
                    existingReceipt.updateLastUpdate(saleDate);
                }
                return existingReceipt;
            }
        });
    }
}
