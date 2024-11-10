package bsm.devcoop.oring.entity.occount.item;

import bsm.devcoop.oring.entity.occount.item.types.EventType;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "occount_items")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Items {
    @Id
    private int itemId; // 상품 아이디

    private String itemCode; // 상품 바코드

    private String itemName; // 상품명

    private String itemImage;

    private String itemExplain; // 상품명

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory; // 상품 카테고리

    private int itemPrice; // 상품 가격

    private int itemQuantity; // 상품 개수

    @Enumerated(EnumType.STRING)
    private EventType event;

    private LocalDate eventStartDate;

    private LocalDate eventEndDate;
}