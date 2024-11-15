package bsm.devcoop.oring.domain.occount.receipt.presentation.dto;

import bsm.devcoop.oring.entity.occount.receipt.Receipt;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ReceiptDto {
    @Builder
    @Getter
    public static class ReceiptResponse {
        private List<Receipt> receiptList;
    }


    @Builder
    @Getter
    public static class SumReceipt {
        private int itemId;
        private String itemName;
        private int saleQty;
        private LocalDate lastUpdated;

        public void addSaleQty(int addQty) {
            this.saleQty += addQty;
        }

        public void updateLastUpdate(LocalDate update) {
            this.lastUpdated = update;
        }
    }

    @Builder
    @Getter
    public static class SumReceiptResponse {
        private List<SumReceipt> sumReceiptList;
    }
}
