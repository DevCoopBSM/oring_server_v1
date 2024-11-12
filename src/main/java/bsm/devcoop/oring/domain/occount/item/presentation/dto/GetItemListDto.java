package bsm.devcoop.oring.domain.occount.item.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class GetItemListDto {
    @Builder
    @Getter
    public static class Items {
        private int itemId;
        private String itemImage;
        private String itemName;
        private int itemPrice;
        private int itemQuantity;
    }

    @Builder
    @Getter
    public static class ItemsResponse {
        private String itemCategory;
        private List<Items> itemList;
    }
}
