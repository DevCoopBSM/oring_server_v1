package bsm.devcoop.oring.entity.occount.item.repository;

import bsm.devcoop.oring.entity.occount.item.Items;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
    Items findByItemName(String itemName);

    List<Items> findAllByItemCategory(ItemCategory itemCategory);
}
