package bsm.devcoop.oring.domain.ai.service;

import bsm.devcoop.oring.domain.occount.item.presentation.dto.AIDto;
import bsm.devcoop.oring.entity.occount.item.Items;
import bsm.devcoop.oring.entity.occount.item.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AIService {
    @Value("${ai.api}")
    private String aiApiUrl;

    private final ObjectMapper objectMapper;
    private final RestTemplateBuilder restTemplate;

    private final ItemRepository itemRepository;

    public AIDto.RecommendResponse getRecommendList(List<String> itemNameList) {
        log.info("상품명으로 추천 상품 리스트 찾아오기");

        List<AIDto.Recommend> recommendList = itemNameList.stream()
                .map(itemName -> {
                    Items item = itemRepository.findByItemName(itemName);
                    return AIDto.Recommend.builder()
                            .itemId(item.getItemId())
                            .itemName(item.getItemName())
                            .itemImage(item.getItemImage())
                            .itemPrice(item.getItemPrice())
                            .itemQuantity(item.getItemQuantity())
                            .build();
                }).toList();

        return AIDto.RecommendResponse.builder()
                .recommendList(recommendList)
                .build();
    }

    public AIDto.RecommendAIResponse callRecommendApi(AIDto.Request request) {
        String url = aiApiUrl + "/recommend_item/";
        log.info("URL : {}", url);

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        String body;

        try {
            body = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            log.error("JSON 생성 중 오류가 발생했습니다.", e);
            throw new RuntimeException("JSON 생성 실패", e);
        }

        log.info("바디 text json 설정 : {}", body);

        HttpEntity<String> requestBody = new HttpEntity<>(body, headers);
        return restTemplate.build().postForEntity(url, requestBody, AIDto.RecommendAIResponse.class).getBody();
    }

    public AIDto.WarningAIResponse callWarningApi(AIDto.Request request) {
        String url = aiApiUrl + "/item_warning/";
        log.info("URL : {}", url);

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        String body;

        try {
            body = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            log.error("JSON 생성 중 오류가 발생했습니다.", e);
            throw new RuntimeException("JSON 생성 실패", e);
        }

        log.info("바디 text json 설정 : {}", body);

        HttpEntity<String> requestBody = new HttpEntity<>(body, headers);
        return restTemplate.build().postForEntity(url, requestBody, AIDto.WarningAIResponse.class).getBody();
    }

    public AIDto.PeakTimeAIResponse callPeakTimeApi() {
        String url = aiApiUrl + "/peak_time/";
        log.info("URL : {}", url);

        return restTemplate.build().getForEntity(url, AIDto.PeakTimeAIResponse.class).getBody();
    }
}
