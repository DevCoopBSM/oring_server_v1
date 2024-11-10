package bsm.devcoop.oring.domain.occount.transaction.presentation;

import bsm.devcoop.oring.domain.occount.transaction.presentation.dto.LogDto;
import bsm.devcoop.oring.domain.occount.transaction.service.LogService;
import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction", produces = "application/json; UTF8")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final LogService logService;

    @GetMapping("/log")
    public ResponseEntity<?> getAllLog() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUserEmail();

        log.info("사용자 {}의 결제 및 충전 로그 조회", userEmail);

        try {
            LogDto.LogResponse response = logService.getAllChargeAndPayLog(userEmail);

            return ResponseEntity.ok().body(response);
        } catch (GlobalException e) {
            return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getMessage());
        }
    }
}
