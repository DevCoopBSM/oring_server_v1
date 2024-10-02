package bsm.devcoop.oring.entity.occount.item.repository;

import bsm.devcoop.oring.entity.occount.item.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
    Items findItemsByItemCode(String itemCode);

    int findItemIdByItemCode(String itemCode);
}
