package bsm.devcoop.oring.entity.occount.inventory.total.repository;


import bsm.devcoop.oring.entity.occount.inventory.total.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByLastUpdatedBetweenOrderByLastUpdatedDesc(LocalDate startDate, LocalDate endDate);
}
