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
