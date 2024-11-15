package bsm.devcoop.oring.domain.occount.kiosk.presentation;//package bssm.devcoop.occount.domain.kiosk.presentation;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/kiosk", produces = "application/json; charset=UTF8")
//@RequiredArgsConstructor
//@Slf4j
//public class KioskController {
//    private final KioskController kioskController;
//
////    @PostMapping("/payments")
////    public ResponseEntity<?> payPoint(@RequestBody Payments payments) {
////        log.info("paymentsDto = {}", payments);
////
////        try {
////            UserPointRequest userPointRequestDto = payments.userPointRequest();
////
////            log.info("userPointRequestDto = {}", userPointRequestDto);
////            Object result = selfCounterService.deductPoints(userPointRequestDto);
////            log.info("result = {}", result);
////            if (result instanceof ResponseEntity && ((ResponseEntity<?>) result).getStatusCode().isError()) {
////                throw new RuntimeException("결제를 하는 동안 에러가 발생하였습니다");
////            }
////
////            PayLogRequest payLogRequestDto = payments.payLogRequest();
////            log.info("payLogRequestDto = {}", payLogRequestDto);
////            ResponseEntity<Object> responseEntity = selfCounterService.savePayLog(payLogRequestDto);
////            log.info("responseEntity = {}", responseEntity);
////            if (responseEntity.getStatusCode().isError()) {
////                throw new RuntimeException("결제 정보를 저장하는 동안 에러가 발생하였습니다");
////            }
////
////            System.out.println("save kiosk check");
////            ResponseEntity<Object> saveReceiptResponseEntity = selfCounterService.saveReceipt(payments.kioskRequest());
////            log.info("saveReceiptResponseEntity = {}", saveReceiptResponseEntity);
////            if (saveReceiptResponseEntity.getStatusCode().isError()) {
////                throw new RuntimeException("영수증을 출력하는 동안 에러가 발생하였습니다");
////            }
////
////
////            return ResponseEntity.ok().build();
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 중 서버 에러가 발생하였습니다");
////        }
////    }
//}
