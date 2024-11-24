package bsm.devcoop.oring.domain.chat.presentation;

import bsm.devcoop.oring.domain.chat.presentation.dto.ChatDto;
import bsm.devcoop.oring.domain.chat.service.ChatService;
import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import bsm.devcoop.oring.entity.chat.ChatRoom;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "chat", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/")
    public ResponseEntity<?> getAllChatRoom() {
        log.info("모든 채팅방 불러오기");
        ChatDto.AllResponse response = chatService.getAll();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/myRooms")
    public ResponseEntity<?> getMyAllChatRoom() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUserEmail();

        log.info("사용자 {} 의 모든 채팅방 불러오기", userEmail);

        ChatDto.AllResponse response = chatService.getMyAll(userEmail);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> getChatRoomInfo(@PathVariable String chatRoomId) {
        log.info("특정 채팅방의 내역 불러오기 : {}", chatRoomId);

        try {
            ChatRoom chatRoom = chatService.getRoomInfo(chatRoomId);

            ChatDto.InfoResponse response = ChatDto.InfoResponse.builder()
                    .chatRoom(chatRoom)
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (GlobalException e) {
            return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getMessage());
        }
    }
}
