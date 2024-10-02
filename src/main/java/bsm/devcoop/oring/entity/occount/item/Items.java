package bsm.devcoop.oring.entity.occount.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

// item Entity

@Entity
@Table(name = "occount_items")
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Items {
    @Id
    private int itemId; // 상품 아이디

    private String itemCode; // 상품 바코드

    private String itemName; // 상품명

    private String itemExplain; // 상품명

    private String itemCategory; // 상품 카테고리

    private int itemPrice; // 상품 가격

    // 생성자
    @Builder
    public Items(
            int itemId, String itemCode, String itemName,
            String itemExplain, String itemCategory, int itemPrice
    ) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemExplain = itemExplain;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
    }
}
