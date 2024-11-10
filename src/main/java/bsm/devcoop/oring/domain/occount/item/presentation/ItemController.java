package bsm.devcoop.oring.domain.occount.item.presentation;

import bsm.devcoop.oring.domain.occount.item.presentation.dto.GetDto;
import bsm.devcoop.oring.domain.occount.item.service.ItemService;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/item", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<?> getItemList() {
        log.info("모든 아이템 읽기");

        GetDto.ItemResponse response = itemService.getAll();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{itemCategory}")
    public ResponseEntity<?> getItemListWithCategory(@PathVariable ItemCategory itemCategory) {
        log.info("특정 카테고리 내의 모든 아이템 읽기");

        GetDto.ItemResponse response = itemService.getAllWithCategory(itemCategory);

        return ResponseEntity.ok().body(response);
    }
}
