package bsm.devcoop.oring.entity.occount.inventory.snapshot.repository;

import bsm.devcoop.oring.entity.occount.inventory.snapshot.Snapshots;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SnapshotsRepository extends JpaRepository<Snapshots, Long> {
    Snapshots findByItemId(int itemId);

    List<Snapshots> findAllBySnapshotDate(LocalDate stdDate);
}
