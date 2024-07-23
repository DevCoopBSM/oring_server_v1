package bsm.devcoop.oring.domain.item;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    private int itemId; // 상품 아이디

    private String code; // 상품 바코드
    private String itemName; // 상품명
    private String itemExplain; // 상품 설명
    private int itemPrice; // 상품 가격
    private String category; // 상품 카테고리
    private int itemQuantity; // 상품 개수

    @OneToMany(
            mappedBy = "lastUpdated",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<LocalDate> lastUpdatedList = new ArrayList<>(); // 제일 최근 상품 재고 업데이트 날

    @Builder
    public Item(
            int itemId, String code, String itemName,
            int itemPrice, String category, int itemQuantity) {
        this.itemId = itemId;
        this.code = code;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.category = category;
        this.itemQuantity = itemQuantity;
    }

}
