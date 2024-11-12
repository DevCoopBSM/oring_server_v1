package bsm.devcoop.oring.domain.ai.presentation;

import bsm.devcoop.oring.domain.ai.service.AIService;
import bsm.devcoop.oring.domain.occount.item.presentation.dto.AIDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ai", produces = "application/json; UTF8")
@RequiredArgsConstructor
@Slf4j
public class AIController {
    private final AIService aiService;

    @PostMapping("/recommend")
    public ResponseEntity<?> recommendItemList(@RequestBody AIDto.Request request) {
        log.info("AI 기반 상품 추천 API 호출");

        try {
            AIDto.RecommendAIResponse aiResponse = aiService.callRecommendApi(request);

            log.info("AI 응답 기반 추천 상품 리스트 정리");
            AIDto.RecommendResponse response = aiService.getRecommendList(aiResponse.getItemNameList());

            log.info("AI 기반 상품 추천 완료");
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/warning")
    public ResponseEntity<?> warningItem(@RequestBody AIDto.Request request) {
        log.info("실시간 재고 경고 시스템 API 호출");

        try {
            AIDto.WarningAIResponse response = aiService.callWarningApi(request);

            log.info("실시간 재고 경고 완료");
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/peck")
    public ResponseEntity<?> peakTime() {
        log.info("피크 타임 경고 시스템 API");
        
        AIDto.PeakTimeAIResponse response = aiService.callPeakTimeApi();

        log.info("피크 타임 경고 완료");
        return ResponseEntity.ok().body(response);
    }
}
