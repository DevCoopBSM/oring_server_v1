package bsm.devcoop.oring.domain.occount.item.service;

import bsm.devcoop.oring.domain.occount.item.presentation.dto.CreateDto;
import bsm.devcoop.oring.domain.occount.item.presentation.dto.GetItemDto;
import bsm.devcoop.oring.domain.occount.item.presentation.dto.GetItemListDto;
import bsm.devcoop.oring.entity.occount.inventory.snapshot.repository.SnapshotsRepository;
import bsm.devcoop.oring.entity.occount.inventory.total.repository.InventoryRepository;
import bsm.devcoop.oring.entity.occount.item.Items;
import bsm.devcoop.oring.entity.occount.item.repository.ItemRepository;
import bsm.devcoop.oring.entity.occount.item.types.ItemCategory;
import bsm.devcoop.oring.entity.occount.receipt.repository.KioskReceiptRepository;
import bsm.devcoop.oring.entity.occount.receipt.repository.ReceiptRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    // Repository
    private final InventoryRepository inventoryRepository;
    private final SnapshotsRepository snapshotsRepository;
    private final ReceiptRepository receiptRepository;
    private final KioskReceiptRepository kioskReceiptRepository;

    // itemCode -> item
    public Items getItemByCode(String itemCode) {
        return itemRepository.findByItemCode(itemCode);
    }

    // itemId -> itemCode
    public int getItemIdByCode(String itemCode) {
        Items items = this.getItemByCode(itemCode);

        return items == null? 0 : items.getItemId();
    }

    // item Save
    public void saveItem(CreateDto.Request request) {
        Items items = Items.builder()
                .itemCode(request.getItemCode())
                .itemName(request.getItemName())
                .itemImage(request.getItemImage())
                .itemExplain(request.getItemExplain())
                .itemPrice(request.getItemPrice())
                .itemCategory(request.getItemCategory())
                .build();
        itemRepository.save(items);
    }

    // itemList ( all )
    public GetItemListDto.ItemsResponse getAll() {
        // ALL -> 이름 순일지 새롭게 들어온 신상품 순일지도 정하는 게 좋을 듯
        log.info("List<Items> 를 List<GetDto.Item> 으로 변환하기");
        List<GetItemListDto.Items> itemList = (itemRepository.findAll()).stream()
                .limit(20)
                .map(items -> {
                    log.info("상품 개수 새로고침");
                    this.updateItemQuantity(items, LocalDate.now());

                    return GetItemListDto.Items.builder()
                            .itemId(items.getItemId())
                            .itemImage(items.getItemImage())
                            .itemName(items.getItemName())
                            .itemCategory(items.getItemCategory())
                            .itemPrice(items.getItemPrice())
                            .itemQuantity(items.getItemQuantity())
                            .build();
                }).toList();

        log.info("GetDto.ItemResponse 객체 생성 후 반환하기");
        return this.responseList("전체", itemList);
    }

    // itemList ( Category )
    public GetItemListDto.ItemsResponse getAllWithCategory(ItemCategory itemCategory) {
        log.info("카테고리 {} 에 맞춰 List<Items> 를 List<GetDto.Item> 으로 변환하기", itemCategory);
        List<GetItemListDto.Items> itemList = itemRepository.findAllByItemCategory(itemCategory).stream()
                .limit(20)
                .map(items -> {
                    log.info("상품 개수 새로고침");
                    this.updateItemQuantity(items, LocalDate.now());

                    return GetItemListDto.Items.builder()
                            .itemId(items.getItemId())
                            .itemImage(items.getItemImage())
                            .itemName(items.getItemName())
                            .itemPrice(items.getItemPrice())
                            .itemQuantity(items.getItemQuantity())
                            .build();
                }).toList();

        log.info("GetDto.ItemResponse 객체 생성 후 반환하기");
        return this.responseList(itemCategory.toString(), itemList);
    }

    /*
    public GetItemListDto.ItemsResponse getAllWithCategory(List<GetItemListDto.Items> itemList, ItemCategory filter) {
        log.info("카테고리 {} 에 맞춰 List<GetDto.Item> 를 필터링하기", filter);

        if (filter == ItemCategory.기타) filter = ItemCategory.잡화;

        ItemCategory itemCategory = filter;

        List<GetItemListDto.Items> filteredItemList = itemList.stream()
                .filter(items -> items.getItemCategory() == itemCategory)
                .toList();

        log.info("GetDto.ItemResponse 객체 생성 후 반환하기");
        return this.responseList(itemCategory.toString(), itemList);
    }
    */

    // itemList Response 생성
    private GetItemListDto.ItemsResponse responseList(String itemCategory, List<GetItemListDto.Items> itemList) {
        log.info("GetDto.ItemResponse 반환 완료");

        return GetItemListDto.ItemsResponse.builder()
                .itemCategory(itemCategory)
                .itemList(itemList)
                .build();
    }


    // itemInfo
    public GetItemDto.ItemResponse getItemInfo(int itemId) throws GlobalException {
        log.info("상세 정보 불러올 상품 : {}", itemId);

        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND, "상품을 찾을 수 없습니다."));

        return this.responseInfo(item);
    }

    public GetItemDto.ByCodeResponse getItemInfoByCode(String itemCode) throws GlobalException {
        log.info("상세 정보 불러올 상품 : {}", itemCode);

        Items items = itemRepository.findByItemCode(itemCode);
        String itemName = items.getItemName();

        return GetItemDto.ByCodeResponse.builder()
                .itemNameCompany(itemName.split("_")[0])
                .itemName(itemName.split("_")[1])
                .itemNameEtc(itemName.split("_")[2])
                .itemCode(items.getItemCode())
                .build();
    }

    // itemInfo Response 생성
    private GetItemDto.ItemResponse responseInfo(Items item) {
        log.info("Items 객체를 GetItemDto.ItemResponse 로 변환 후 반환하기");

        GetItemDto.Item itemInfo = GetItemDto.Item.builder()
                .itemId(item.getItemId())
                .itemCode(item.getItemCode())
                .itemImage(item.getItemImage())
                .itemName(item.getItemName())
                .itemExplain(item.getItemExplain())
                .itemCategory(item.getItemCategory().toString())
                .itemPrice(item.getItemPrice())
                .itemQuantity(item.getItemQuantity())
                .build();

        return GetItemDto.ItemResponse.builder()
                .itemInfo(itemInfo)
                .build();
    }

    // itemQuantity 구하기
    private void updateItemQuantity(Items item, LocalDate itemQtyDate) {
        log.info("상품 재고 개수 호출");

        int itemId = item.getItemId();
        String itemCode = item.getItemCode();

        log.info("상품 아이디 : {} 상품 바코드 : {}", itemId, itemCode);

        int snapshotQty = snapshotsRepository.findSumQuantity(itemId, itemQtyDate);
        int inventoryQty = inventoryRepository.findSumQuantity(itemId, itemQtyDate);
        int receiptQty = receiptRepository.findSumQuantity(itemId, itemQtyDate);
        int kioskQty = kioskReceiptRepository.findSumQuantity(itemCode, itemQtyDate);

        int itemQuantity = (inventoryQty + snapshotQty) - (receiptQty + kioskQty);

        item.updateItemQuantity(itemQuantity);
    }
}
