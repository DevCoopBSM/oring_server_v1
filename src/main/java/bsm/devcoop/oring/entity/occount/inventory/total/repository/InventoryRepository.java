package bsm.devcoop.oring.entity.occount.inventory.total.repository;


import bsm.devcoop.oring.entity.occount.inventory.total.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByLastUpdatedBetweenOrderByLastUpdatedDesc(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(i.itemQuantity), 0) FROM Inventory AS i WHERE i.itemId = :itemId and i.lastUpdated > :lastUpdated")
    int findSumQuantity(@Param("itemId") int itemId, @Param("lastUpdated") LocalDate lastUpdated);
}
