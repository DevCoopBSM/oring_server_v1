package bsm.devcoop.oring.entity.occount.inventory.snapshot;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "occount_inventorySnapshots")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Snapshots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snapshotId;

    private LocalDate snapshotDate; // 스냅샷 생성날

    private int itemId;

    private String itemName;

    private int itemQuantity; // 스냅샷 재고 수량

    private String managedEmail;

    @Builder
    public Snapshots(
            Long snapshotId, LocalDate snapshotDate, int itemId,
            String itemName, int itemQuantity, String managedEmail
    ) {
        this.snapshotId = snapshotId;
        this.snapshotDate = snapshotDate;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.managedEmail = managedEmail;
    }
}
