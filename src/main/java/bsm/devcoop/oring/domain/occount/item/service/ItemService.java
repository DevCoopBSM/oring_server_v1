package bsm.devcoop.oring.domain.occount.item.service;

import bsm.devcoop.oring.domain.occount.item.presentation.dto.GetDto;
import bsm.devcoop.oring.entity.occount.item.Items;
import bsm.devcoop.oring.entity.occount.item.repository.ItemRepository;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
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

    public GetDto.ItemResponse getAll() {
        // ALL -> 이름 순일지 새롭게 들어온 신상품 순일지도 정하는 게 좋을 듯

        log.info("List<Items> 를 List<GetDto.Item> 으로 변환하기");
        List<GetDto.Item> itemList = (itemRepository.findAll()).stream()
                .map(items -> {
                    return GetDto.Item.builder()
                            .itemImage(items.getItemImage())
                            .itemName(items.getItemName())
                            .itemPrice(items.getItemPrice())
                            .itemQuantity(items.getItemQuantity())
                            .build();
                }).toList();

        log.info("GetDto.ItemResponse 객체 생성 후 반환하기");
        return this.response("전체", itemList);
    }

    public GetDto.ItemResponse getAllWithCategory(ItemCategory itemCategory) {
        log.info("카테고리 {} 에 맞춰 List<Items> 를 List<GetDto.Item> 으로 변환하기", itemCategory);
        List<GetDto.Item> itemList = (itemRepository.findAllByItemCategory(itemCategory)).stream()
                .map(items -> {
                    return GetDto.Item.builder()
                            .itemImage(items.getItemImage())
                            .itemName(items.getItemName())
                            .itemPrice(items.getItemPrice())
                            .itemQuantity(items.getItemQuantity())
                            .build();
                }).toList();

        log.info("GetDto.ItemResponse 객체 생성 후 반환하기");
        return this.response(itemCategory.toString(), itemList);
    }

    private GetDto.ItemResponse response(String itemCategory, List<GetDto.Item> itemList) {
        log.info("GetDto.ItemResponse 반환 완료");

        return GetDto.ItemResponse.builder()
                .itemCategory(itemCategory)
                .itemList(itemList)
                .build();
    }
}
