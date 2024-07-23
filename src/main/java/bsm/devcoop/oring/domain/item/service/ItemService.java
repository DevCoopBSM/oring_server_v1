package bsm.devcoop.oring.domain.item.service;

import bsm.devcoop.oring.domain.item.Item;
import bsm.devcoop.oring.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
