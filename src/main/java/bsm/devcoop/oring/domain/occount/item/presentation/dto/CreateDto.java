package bsm.devcoop.oring.domain.occount.item.presentation.dto;

import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import lombok.Builder;
import lombok.Getter;

public class CreateDto {
    @Builder
    @Getter
    public static class Request {
        private String itemCode;
        private String itemName;
        private String itemImage;
        private String itemExplain;
        private int itemPrice;
        private ItemCategory itemCategory;
    }
}
