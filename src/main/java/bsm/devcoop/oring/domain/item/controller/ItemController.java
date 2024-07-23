package bsm.devcoop.oring.domain.item.controller;

import bsm.devcoop.oring.domain.item.Item;
import bsm.devcoop.oring.domain.item.controller.dto.ItemsResponse;
import bsm.devcoop.oring.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<?> ItemList() {
        log.info("Get All Item List Started");

        List<Item> itemList = itemService.getAllItems();

        ItemsResponse response = ItemsResponse.builder()
                .itemList(itemList)
                .build();

        return ResponseEntity.ok().body(response);
    }
}
