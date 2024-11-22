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
}
