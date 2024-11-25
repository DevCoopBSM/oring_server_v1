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

    public void send(String notifyContent, NotifyType notifyType, String notifyUrl, String receiveUserEmail) {
        Notify notify = notificationRepository.save(this.create(notifyContent, notifyType, notifyUrl, receiveUserEmail));

        Map<String, SseEmitter> emitterMap = emitterRepository.findAllEmitterByUserEmail(receiveUserEmail);
        emitterMap.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notify);
                    sendToClient(emitter, key, NotifyDto.NewResponse.builder().notify(notify).build());
                }
        );
    }

    private Notify create(String notifyContent, NotifyType notifyType, String notifyUrl, String receiveUserEmail) {
        return Notify.builder()
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
}
