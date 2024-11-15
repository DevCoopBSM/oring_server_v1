package bsm.devcoop.oring.entity.occount.inventory.total;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// 입고 데이터를 관리하는 Entity

@Builder
@Entity
@Table(name = "occount_inventory")
@Data
@AllArgsConstructor
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
}
