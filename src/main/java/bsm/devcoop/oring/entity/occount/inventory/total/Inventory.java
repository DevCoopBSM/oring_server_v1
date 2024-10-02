package bsm.devcoop.oring.entity.occount.inventory.total;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 입고 데이터를 관리하는 Entity

@Entity
@Table(name = "common_investment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId; // 입고 아이디

    private int itemId; // 상품 아이디

    private String itemName; // 상품 이름

    private int itemQuantity; // 상품 입고 수량

    private LocalDate lastUpdated; // 마지막 입고 날짜

    private String managedEmail; // 담당자 이메일

    private String reason; // 입고 여부 ( 정기 입고, 정정 )

    @Builder
    public Inventory(
            Long inventoryId,
            int itemId,
            String itemName,
            int itemQuantity,
            LocalDate lastUpdated,
            String managedEmail,
            String reason
    ) {
        this.inventoryId = inventoryId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.lastUpdated = lastUpdated;
        this.managedEmail = managedEmail;
        this.reason = reason;
    }
}
