package bsm.devcoop.oring.domain.notify.service;

import bsm.devcoop.oring.domain.notify.presentation.dto.NotifyDto;
import bsm.devcoop.oring.domain.notify.repository.EmitterRepository;
import bsm.devcoop.oring.entity.notify.Notify;
import bsm.devcoop.oring.entity.notify.repository.NotificationRepository;
import bsm.devcoop.oring.entity.notify.types.NotifyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotifyService {
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    @Value("${emitter.expiration}")
    private long exprTime;

    public SseEmitter subscribe(String userEmail, String lastEventId) {
        log.info("SSE Emitter 구독 시작");

        String emitterId = userEmail + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(exprTime));

        log.info("시간 초과 혹은 비동기 요청 실패 시 자동 삭제");
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        sendToClient(emitter, emitterId, userEmail + " First Connection Success!");

        if (!lastEventId.isEmpty()) {
            log.info("이벤트 캐시 SSE 전부 전송");
            Map<String, Object> eventMap = emitterRepository.findAllEventCacheByUserEmail(userEmail);
            eventMap.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        log.info("구독 완료");
        return emitter;
    }

    public void send(String notifyTitle, String notifyContent, NotifyType notifyType, String notifyUrl, String receiveUserEmail) {
        Notify notify = notificationRepository.save(this.create(notifyTitle, notifyContent, notifyType, notifyUrl, receiveUserEmail));

        Map<String, SseEmitter> emitterMap = emitterRepository.findAllEmitterByUserEmail(receiveUserEmail);
        emitterMap.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notify);
                    sendToClient(emitter, key, NotifyDto.NewResponse.builder().notify(notify).build());
                }
        );
    }

    private Notify create(String notifyTitle, String notifyContent, NotifyType notifyType, String notifyUrl, String receiveUserEmail) {
        return Notify.builder()
                .notifyTitle(notifyTitle)
                .notifyContent(notifyContent)
                .notifyType(notifyType)
                .notifyUrl(notifyUrl)
                .receiveUserEmail(receiveUserEmail)
                .build();
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            // throw new BadRequestException("알림 전송에 실패하였습니다.");
            log.error("알림 전송에 실패하였습니다.");
        }
    }


    // Custom Notify

    public void sendAIWarningNotify(int itemId, String itemName, String receiveUserEmail) {
        log.info("AI 재고 소진 알림");

        String notifyTitle = itemName + " 이/가 품절 직전이에요!";
        String notifyContent = "다 팔리기 전에 얼른 가서 사는 건 어때요?";
        String notifyUrl = "/api/item/info/" + itemId;

        this.send(notifyTitle, notifyContent, NotifyType.CHAT_ANSWER, notifyUrl, receiveUserEmail);
    }

    public void sendInventoryNewNotify(int itemId, String receiveUserEmail) {
        log.info("즐겨찾는 상품 재입고 알림");

        String notifyTitle = "찜해 둔 상품이 재입고 되었어요.";
        String notifyContent = "상품을 한 번 둘러보고 오늘 간식을 결정해 보아요.";
        String notifyUrl = "/api/chat/info/" + itemId;

        this.send(notifyTitle, notifyContent, NotifyType.CHAT_ANSWER, notifyUrl, receiveUserEmail);
    }

    public void sendChatAnswerNotify(String chatRoomId, String receiveUserEmail) {
        log.info("문의 채팅 수신 알림");

        String notifyTitle = "매점의 소리에 새로운 답변이 달렸어요.";
        String notifyContent = "매점부 친구의 답변을 확인해 볼까요?";
        String notifyUrl = "/api/chat/" + chatRoomId;

        this.send(notifyTitle, notifyContent, NotifyType.CHAT_ANSWER, notifyUrl, receiveUserEmail);
    }

    public void sendVoteNewNotify(String chatRoomId, String receiveUserEmail) {
        log.info("새 총회 개설 알림");

        String notifyTitle = "총회가 시작되었어요!";
        String notifyContent = "새롭게 코드를 발급받고 참여해 볼까요?";
        String notifyUrl = "";

        this.send(notifyTitle, notifyContent, NotifyType.CHAT_ANSWER, notifyUrl, receiveUserEmail);
    }
}
