package bsm.devcoop.oring.domain.ai.presentation;

import bsm.devcoop.oring.domain.account.service.UserService;
import bsm.devcoop.oring.domain.ai.service.AIService;
import bsm.devcoop.oring.domain.occount.item.presentation.dto.AIDto;
import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ai", produces = "application/json; UTF8")
@RequiredArgsConstructor
@Slf4j
public class AIController {
    private final AIService aiService;

    private final UserService userService;

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendItemList() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUserEmail();

        String userCode = userService.getUserCodeByEmail(userEmail);

        log.info("AI 기반 상품 추천 API 호출, 사용자 : {}", userCode);

        AIDto.Request request = AIDto.Request.builder()
                .userCode(userCode)
                .build();

        try {
            AIDto.RecommendAIResponse aiResponse = aiService.callRecommendApi(request);

            log.info("AI 응답 기반 추천 상품 리스트 정리");
            AIDto.RecommendResponse response = aiService.getRecommendList(aiResponse.getItem_name());

            log.info("AI 기반 상품 추천 완료");
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/warning")
    public ResponseEntity<?> warningItem() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUserEmail();

        String userCode = userService.getUserCodeByEmail(userEmail);

        log.info("실시간 재고 경고 시스템 API 호출, 사용자 : {}", userCode);

        AIDto.Request request = AIDto.Request.builder()
                .userCode(userCode)
                .build();

        try {
            AIDto.WarningAIResponse aiResponse = aiService.callWarningApi(request);

            AIDto.WarningResponse response = AIDto.WarningResponse.builder()
                    .itemName(aiResponse.getItem_name())
                    .build();

            log.info("실시간 재고 경고 완료");
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/peak")
    public ResponseEntity<?> peakTime() {
        log.info("피크 타임 경고 시스템 API");
        
        AIDto.PeakTimeAIResponse response = aiService.callPeakTimeApi();

        log.info("피크 타임 경고 완료");
        return ResponseEntity.ok().body(response);
    }
}
