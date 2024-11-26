package bsm.devcoop.oring.entity.occount.item.repository;

import bsm.devcoop.oring.entity.occount.item.Items;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
    Items findByItemName(String itemName);

    Items findByItemCode(String itemCode);

    List<Items> findAllByItemCategory(ItemCategory itemCategory);
}
