package bsm.devcoop.oring.domain.occount.inventory.presentation.dto;

import bsm.devcoop.oring.entity.occount.inventory.total.Inventory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ReadInventoryDto {
    // inventoryCheck Dto
    @Builder
    @Getter
    public static class AllResponse {
        private List<Inventory> inventoryList;
    }

    // inventoryByDay Dto
    @Builder
    @Getter
    public static class ByDay {
        private int itemId;
        private LocalDate snapshotDate;
        private String itemName;
        private int itemQuantity;
    }

    @Builder
    @Getter
    public static class ByDayResponse {
        private List<ByDay> snapshotList;
    }
}
