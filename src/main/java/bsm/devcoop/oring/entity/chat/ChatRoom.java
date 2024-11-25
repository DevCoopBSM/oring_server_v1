package bsm.devcoop.oring.entity.chat;

import bsm.devcoop.oring.domain.chat.presentation.dto.ChatMessage;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Table(name = "oring_chatroom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    @Id
    private String chatRoomId;

    private String chatRoomName; // 첫 메시지 내용

    private String createUserEmail;

    private String createUserName; // 채팅방 생성 유저 ( 사용자 학번 + " " + 사용자 이름 )

    private LocalDate createdAt;

    @Builder.Default
    @JdbcTypeCode((SqlTypes.JSON))
    private List<ChatMessage> messageList = new ArrayList<>();

    public void initChatRoomName(String message) {
        this.chatRoomName = message;
    }

    public void addMessage(ChatMessage chatMessage) {
        this.messageList.add(chatMessage);
    }
}
