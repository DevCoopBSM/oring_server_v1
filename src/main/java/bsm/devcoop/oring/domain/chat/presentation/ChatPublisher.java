package bsm.devcoop.oring.domain.chat.presentation;

import bsm.devcoop.oring.domain.account.service.UserService;
import bsm.devcoop.oring.domain.chat.presentation.dto.ChatMessage;
import bsm.devcoop.oring.domain.chat.service.ChatService;
import bsm.devcoop.oring.domain.notify.service.NotifyService;
import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.entity.chat.ChatRoom;
import bsm.devcoop.oring.entity.chat.types.MessageType;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatPublisher {
    private final ChatService chatService;
    private final NotifyService notifyService;
    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/messaging")
    public void message(StompHeaderAccessor accessor, ChatMessage chatMessage) {
        log.info("메시지 : {}", chatMessage.toString());

        String header = accessor.getFirstNativeHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("존재하지 않거나 잘못된 토큰입니다.");
        }

        String token = header.substring(7);
        String userEmail = jwtUtil.getUserEmail(token);

        if (MessageType.ENTER.equals(chatMessage.getMessageType())) {
            log.info("채팅방 첫 개설");
            chatMessage.initChatRoomId(chatService.createRoom(userEmail, chatMessage.getMessage()));
        }

        try {
            chatService.saveChatMessage(chatMessage);
        } catch (GlobalException e) {
            log.error("채팅방에 메시지를 정상적으로 저장하지 못했습니다.");
        }

        String chatRoomId = chatMessage.getChatRoomId();

        log.info("메시지 전송");
        messageTemplate.convertAndSend("/subChat/chatRoom/" + chatRoomId, chatMessage);

        try {
            ChatRoom chatRoom = chatService.getRoomInfo(chatRoomId);
            String createUserEmail = chatRoom.getCreateUserEmail();

            if (!userEmail.equals(createUserEmail)) {
                log.info("메시지 전송 알림 호출");
                notifyService.sendChatAnswerNotify(chatRoomId, createUserEmail);
            }
        } catch (GlobalException e) {
            log.error("존재하지 않는 채팅방입니다.");
        }

        log.info("메시지 전송 완료");
    }
}
