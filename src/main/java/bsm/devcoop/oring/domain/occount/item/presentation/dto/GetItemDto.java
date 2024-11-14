package bsm.devcoop.oring.domain.occount.item.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class GetItemDto {
    @Builder
    @Getter
    public static class Item {
        private int itemId;
        private String itemCode;
        private String itemImage;
        private String itemName;
        private String itemExplain;
        private String itemCategory;
        private int itemPrice;
        private int itemQuantity;
    }

    @Builder
    @Getter
    public static class ItemResponse {
        private Item itemInfo;
    }
}
