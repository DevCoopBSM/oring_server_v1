package bsm.devcoop.oring.entity.occount.inventory.snapshot.repository;

import bsm.devcoop.oring.entity.occount.inventory.snapshot.Snapshots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SnapshotsRepository extends JpaRepository<Snapshots, Long> {
    List<Snapshots> findAllBySnapshotDate(LocalDate stdDate);

    @Query(value = "SELECT s FROM Snapshots s WHERE s.itemId = :itemId ORDER BY s.snapshotDate ASC")
    List<Snapshots> findByItemId(@Param("itemId") int itemId);

    @Query("SELECT COALESCE(SUM(s.itemQuantity), 0) FROM Snapshots AS s WHERE s.itemId = :itemId and s.snapshotDate > :snapshotDate")
    int findSumQuantity(@Param("itemId") int itemId, @Param("snapshotDate") LocalDate snapshotDate);
}
