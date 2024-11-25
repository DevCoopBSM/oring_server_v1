package bsm.devcoop.oring.domain.chat.presentation.dto;

import bsm.devcoop.oring.entity.chat.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ChatDto {
    @Builder
    @Getter
    public static class All {
        private String chatRoomId;
        private String chatRoomName;
        private String createUserName;
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    public static class AllResponse {
        private List<All> chatRoomList;
    }

    @Builder
    @Getter
    public static class InfoResponse {
        private ChatRoom chatRoom;
    }
}
