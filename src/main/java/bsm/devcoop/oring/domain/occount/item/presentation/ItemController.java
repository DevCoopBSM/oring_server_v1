package bsm.devcoop.oring.domain.occount.item.presentation;

import bsm.devcoop.oring.domain.occount.item.presentation.dto.GetItemDto;
import bsm.devcoop.oring.domain.occount.item.presentation.dto.GetItemListDto;
import bsm.devcoop.oring.domain.occount.item.service.ItemService;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import bsm.devcoop.oring.global.exception.GlobalException;
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

        GetItemListDto.ItemsResponse response = itemService.getAll();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{itemCategory}")
    public ResponseEntity<?> getItemListWithCategory(@PathVariable ItemCategory itemCategory) {
        log.info("특정 카테고리 내의 모든 아이템 읽기");

        GetItemListDto.ItemsResponse response = itemService.getAllWithCategory(itemCategory);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/info/{itemId}")
    public ResponseEntity<?> getItemInfo(@PathVariable int itemId) {
        log.info("특정 아이템의 상세 정보 불러오기");

        try {
            GetItemDto.ItemResponse response = itemService.getItemInfo(itemId);

            return ResponseEntity.ok().body(response);
        } catch (GlobalException e) {
            return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getMessage());
        }
    }
}
