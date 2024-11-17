package bsm.devcoop.oring.domain.occount.inventory.presentation;

import bsm.devcoop.oring.entity.occount.inventory.snapshot.Snapshots;
import bsm.devcoop.oring.entity.occount.inventory.total.Inventory;
import bsm.devcoop.oring.domain.occount.inventory.presentation.dto.CreateInventoryDto;
import bsm.devcoop.oring.domain.occount.inventory.presentation.dto.ReadInventoryDto;
import bsm.devcoop.oring.domain.occount.inventory.service.InventoryService;
import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/inventory", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    // 특정 날짜 사이의 인벤토리 읽기
    @GetMapping("/")
    public ResponseEntity<?> inventoryCheck(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        log.info("Get Inventories between {} ~ {}", startDate, endDate);

        List<Inventory> inventoryList = inventoryService.getBetween(startDate, endDate);

        ReadInventoryDto.AllResponse response = ReadInventoryDto.AllResponse.builder()
                .inventoryList(inventoryList)
                .build();

        log.info("Get Inventories Success");
        return ResponseEntity.ok().body(response);
    }

    // 해당 날짜의 재고 변동 사항 조회
    @GetMapping("/byday/{stdDate}")
    public ResponseEntity<?> inventoryByDay(@PathVariable LocalDate stdDate) {
        log.info("Get Inventory changes At : {}", stdDate);

        // 해당 날짜의 재고 스냅샷 전부 조회
        log.info("Get All Snapshots At Date");
        List<Snapshots> snapshotsList = inventoryService.getAllSnapshotsBy(stdDate);

        List<ReadInventoryDto.ByDay> snapshotList = inventoryService.getAllClosestSnapshots(snapshotsList);

        ReadInventoryDto.ByDayResponse response = ReadInventoryDto.ByDayResponse.builder()
                .snapshotList(snapshotList)
                .build();

        return ResponseEntity.ok().body(response);
    }

    // 기준 재고 추가 ( = 스냅샷 생성 )
    @PostMapping("/snapshot")
    public ResponseEntity<?> createSnapshot(@RequestBody CreateInventoryDto.NewRequest request) {
        log.info("Register new Item in Inventory Started");

        // 처리 담당자의 이메일을 토큰에서 추출해 저장
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String managedEmail = userDetails.getUserEmail();
        log.info("Managed Email : {}", managedEmail);

        CreateInventoryDto.NewResponse response = inventoryService.createSnapshot(request, managedEmail);

        return ResponseEntity.ok().body(response);
    }

    // 변동 재고 추가
    @PostMapping("/inventory")
    public ResponseEntity<?> stockChange(@RequestBody CreateInventoryDto.NewRequest request) {
        log.info("Register new Item in Inventory Started");

        // 처리 담당자의 이메일을 토큰에서 추출해 저장
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String managedEmail = userDetails.getUserEmail();
        log.info("Managed Email : {}", managedEmail);

        CreateInventoryDto.NewResponse response = inventoryService.createInventory(request, managedEmail);

        return ResponseEntity.ok().body(response);
    }
}
