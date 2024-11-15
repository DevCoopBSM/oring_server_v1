package bsm.devcoop.oring.domain.occount.receipt.presentation;

import bsm.devcoop.oring.domain.occount.kiosk.service.KioskService;
import bsm.devcoop.oring.domain.occount.receipt.presentation.dto.ReceiptDto;
import bsm.devcoop.oring.domain.occount.receipt.service.ReceiptService;
import bsm.devcoop.oring.entity.occount.receipt.KioskReceipt;
import bsm.devcoop.oring.entity.occount.receipt.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/receipt", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Slf4j
public class ReceiptController {
    private final ReceiptService receiptService;
    private final KioskService kioskService;

    // 특정 기간 내의 Receipt 읽기
    @GetMapping("/receiptcheck")
    public ResponseEntity<?> getReceipts(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        log.info("Get Receipts between {} ~ {}", startDate, endDate);

        List<Receipt> receiptList = receiptService.getAll(startDate, endDate);

        if (receiptList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 기간 Receipt 가 존재하지 않습니다.");
        }

        ReceiptDto.ReceiptResponse response = ReceiptDto.ReceiptResponse.builder()
                .receiptList(receiptList)
                .build();

        return ResponseEntity.ok().body(response);
    }


    // 특정 기간 내의 모든 Receipt ( Receipt + KioskReceipt )
    @GetMapping("/stockvariance")
    public ResponseEntity<?> receiptCheck(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        log.info("Get Receipts and KioskReceipts between {} ~ {}", startDate, endDate);

        // Receipt
        List<Receipt> receiptList = receiptService.getAll(startDate, endDate);

        // Kiosk Kiosk
        List<KioskReceipt> kioskReceiptList = kioskService.getAll(startDate, endDate);

        List<ReceiptDto.SumReceipt> sumReceiptList = receiptService.combineReceipts(receiptList, kioskReceiptList);

        ReceiptDto.SumReceiptResponse response = ReceiptDto.SumReceiptResponse.builder()
                .sumReceiptList(sumReceiptList)
                .build();

        return ResponseEntity.ok().body(response);
    }

}
