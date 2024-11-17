package bsm.devcoop.oring.domain.occount.inventory.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class CreateInventoryDto {
    @Getter
    public static class NewRequestItem {
        private String itemCode;
        private String itemName;
        private int itemQuantity;
        private String reason;
    }

    @Getter
    public static class NewRequest {
        private List<NewRequestItem> items;
    }

    @Builder
    @Getter
    public static class NewResponseResult {
        private String itemCode;
        private int itemQuantity;
        private boolean success;
        private String message;
    }

    @Builder
    @Getter
    public static class NewResponse {
        private List<NewResponseResult> results;
    }
}
