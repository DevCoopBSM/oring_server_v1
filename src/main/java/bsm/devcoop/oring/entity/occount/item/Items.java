package bsm.devcoop.oring.entity.occount.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

// item Entity

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

    private String itemExplain; // 상품명

    private String itemCategory; // 상품 카테고리

    private int itemPrice; // 상품 가격
}
