package bsm.devcoop.oring.domain.occount.inventory.service;

import bsm.devcoop.oring.domain.occount.inventory.presentation.dto.CreateInventoryDto;
import bsm.devcoop.oring.domain.occount.item.presentation.dto.CreateDto;
import bsm.devcoop.oring.domain.occount.item.service.ItemService;
import bsm.devcoop.oring.domain.occount.receipt.service.ReceiptService;
import bsm.devcoop.oring.entity.occount.inventory.snapshot.Snapshots;
import bsm.devcoop.oring.entity.occount.inventory.snapshot.repository.SnapshotsRepository;
import bsm.devcoop.oring.entity.occount.inventory.total.Inventory;
import bsm.devcoop.oring.entity.occount.inventory.total.repository.InventoryRepository;
import bsm.devcoop.oring.domain.occount.inventory.presentation.dto.ReadInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final SnapshotsRepository snapshotsRepository;

    private final ItemService itemService;
    private final ReceiptService receiptService;

    // 기존 재고 추가 = 스냅샷
    public CreateInventoryDto.NewResponse createSnapshot(CreateInventoryDto.NewRequest request, String managedEmail) {
        log.info("요청으로부터 재고 등록 상품 리스트 추출");
        List<CreateInventoryDto.NewRequestItem> itemList = request.getItems();

        List<CreateInventoryDto.NewResponseResult> resultList = itemList.stream()
                .map(item -> {
                    String itemCode = item.getItemCode();
                    if (itemCode == null) {
                        log.error("올바르지 않은 상품 바코드");
                        return this.createResponseResult(item, false, "해당 바코드 값이 올바르지 않습니다.");
                    } else if (itemService.getItemByCode(itemCode) == null) {
                        log.error("존재하지 않는 아이템");
                        return this.createResponseResult(item, false, "해당 상품이 존재하지 않습니다.");
                    }

                    // Client 딴에서 상품 바코드나 상품명 입력 시 나머지 둘 다 보정되게끔 하는 API 설정 필요!!
                    int itemId = itemService.getItemIdByCode(itemCode);

                    if (itemId == 0) {
                        CreateDto.Request itemSaveReq = CreateDto.Request.builder()
                                .itemCode(itemCode)
                                .itemName(item.getItemName())
                                .build();
                        itemService.saveItem(itemSaveReq);
                    }

                    int receiptSoldQty = receiptService.getReceiptsSoldQty(itemId, LocalDate.now());

                    log.info("스냅샷 ( if + 인벤토리 ) 생성");
                    if (receiptSoldQty == 0) {
                        saveSnapshot(itemId, item, managedEmail);
                        return this.createResponseResult(item, true, "해당 상품은 스냅샷에 등록되었습니다.");
                    } else {
                        saveSnapshot(itemId, item, managedEmail);
                        saveInventory(itemId, receiptSoldQty, item, managedEmail);
                        return this.createResponseResult(item, true, "해당 상품은 스냅샷에 등록되었습니다. 판매 내용 보정치 : " + receiptSoldQty);
                    }
                }).toList();

        log.info("결과 List 반환");
        return CreateInventoryDto.NewResponse.builder()
                .results(resultList)
                .build();
    }

    // 변동 재고 추가
    public CreateInventoryDto.NewResponse createInventory(CreateInventoryDto.NewRequest request, String managedEmail) {
        log.info("요청으로부터 재고 등록 상품 리스트 추출");
        List<CreateInventoryDto.NewRequestItem> itemList = request.getItems();

        List<CreateInventoryDto.NewResponseResult> resultList = itemList.stream()
                .map(item -> {
                    String itemCode = item.getItemCode();
                    if (itemCode == null) {
                        log.error("올바르지 않은 상품 바코드");
                        return this.createResponseResult(item, false, "해당 바코드 값이 올바르지 않습니다.");
                    } else if (itemService.getItemByCode(itemCode) == null) {
                        log.error("존재하지 않는 아이템");
                        return this.createResponseResult(item, false, "해당 상품이 존재하지 않습니다.");
                    }

                    // Client 딴에서 상품 바코드나 상품명 입력 시 나머지 둘 다 보정되게끔 하는 API 설정 필요!!
                    int itemId = itemService.getItemIdByCode(itemCode);
                    int receiptSoldQty = receiptService.getReceiptsSoldQty(itemId, LocalDate.now());

                    log.info("스냅샷 존재 여부로 스냅샷 or 인벤토리 생성");
                    if (this.findSnapshot(itemId) == null) {
                        saveSnapshot(itemId, item, managedEmail);
                        return this.createResponseResult(item, true, "해당 상품은 스냅샷에 등록되었습니다.");
                    } else {
                        saveInventory(itemId, receiptSoldQty, item, managedEmail);
                        return this.createResponseResult(item, true, "해당 상품은 인벤토리에 등록되었습니다");
                    }
                }).toList();

        log.info("결과 List 반환");
        return CreateInventoryDto.NewResponse.builder()
                .results(resultList)
                .build();
    }

    // CreateInventory Result Response 생성
    private CreateInventoryDto.NewResponseResult createResponseResult(CreateInventoryDto.NewRequestItem item, boolean isSuccess, String message) {
        return CreateInventoryDto.NewResponseResult.builder()
                .itemCode(item.getItemCode())
                .itemQuantity(item.getItemQuantity())
                .success(isSuccess)
                .message(message)
                .build();
    }

    private void saveSnapshot(int itemId, CreateInventoryDto.NewRequestItem item, String managedEmail) {
        Snapshots snapshots = Snapshots.builder()
                .snapshotDate(LocalDate.now())
                .itemId(itemId)
                .itemName(item.getItemName())
                .itemQuantity(item.getItemQuantity())
                .managedEmail(managedEmail)
                .build();

        log.info("스냅샷 생성 완료, 저장 : {}", itemId);
        snapshotsRepository.save(snapshots);
    }

    public void saveInventory(int itemId, int receiptSoldQty, CreateInventoryDto.NewRequestItem item, String managedEmail) {
        Inventory inventory = Inventory.builder()
                .itemId(itemId)
                .itemName(item.getItemName())
                .itemQuantity(receiptSoldQty)
                .lastUpdated(LocalDate.now())
                .managedEmail(managedEmail)
                .reason("auto : 스냅샷 생성 이전 당일 판매량 보정")
                .build();

        log.info("인벤토리 생성 완료, 저장 : {}", itemId);
        inventoryRepository.save(inventory);
    }

    public Snapshots findSnapshot(int itemId) {
        return (snapshotsRepository.findByItemId(itemId)).get(0);
    }

    public List<Inventory> getBetween(LocalDate startDate, LocalDate endDate) {
        return inventoryRepository.findAllByLastUpdatedBetweenOrderByLastUpdatedDesc(startDate, endDate);
    }

    public List<Snapshots> getAllSnapshotsBy(LocalDate stdDate) {
        return snapshotsRepository.findAllBySnapshotDate(stdDate);
    }

    public List<ReadInventoryDto.ByDay> getAllClosestSnapshots(List<Snapshots> snapshotsList) {
        Map<Integer, ReadInventoryDto.ByDay> snapshotMap = new HashMap<>();

        for (Snapshots snapshots : snapshotsList) {
            int itemId = snapshots.getItemId();
            LocalDate snapshotDate = snapshots.getSnapshotDate();

            snapshotMap.compute(itemId, (key, existingSnapshot) -> {
                if (existingSnapshot == null || snapshotDate.isAfter(existingSnapshot.getSnapshotDate())) {
                    return ReadInventoryDto.ByDay.builder()
                            .itemId(itemId)
                            .snapshotDate(snapshotDate)
                            .itemName(snapshots.getItemName())
                            .itemQuantity(snapshots.getItemQuantity())
                            .build();
                } else {
                    return existingSnapshot;
                }
            });
        }

        return new ArrayList<>(snapshotMap.values());
    }
}
