package bsm.devcoop.oring.entity.occount.inventory.snapshot;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "occount_inventorySnapshots")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Snapshots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snapshotId;

    private LocalDate snapshotDate; // 스냅샷 생성날

    private int itemId;

    private String itemName;

    @Column(name = "quantity")
    private int itemQuantity; // 스냅샷 재고 수량

    private String managedEmail;
}
