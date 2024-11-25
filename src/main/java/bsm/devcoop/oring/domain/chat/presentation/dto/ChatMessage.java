package bsm.devcoop.oring.domain.chat.presentation.dto;

import bsm.devcoop.oring.entity.chat.types.MessageType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessage {
    private String chatRoomId;
    private String userName;
    private MessageType messageType;
    private String message;
    private LocalDateTime messageTime;

    public void initChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}

