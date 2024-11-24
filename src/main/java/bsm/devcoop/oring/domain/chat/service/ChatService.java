package bsm.devcoop.oring.domain.chat.service;

import bsm.devcoop.oring.domain.account.service.UserService;
import bsm.devcoop.oring.domain.chat.presentation.dto.ChatDto;
import bsm.devcoop.oring.domain.chat.presentation.dto.ChatMessage;
import bsm.devcoop.oring.entity.account.student.Student;
import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.entity.chat.ChatRoom;
import bsm.devcoop.oring.entity.chat.repository.ChatRoomRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    public ChatDto.AllResponse getAll() {
        return this.createAllResponse(chatRoomRepository.findAll());
    }

    public ChatDto.AllResponse getMyAll(String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        return this.createAllResponse(chatRoomRepository.findAllByCreateUserNumber(user.getUserNumber()));
    }

    private ChatDto.AllResponse createAllResponse(List<ChatRoom> chatRoomList) {
        List<ChatDto.All> response = chatRoomList.stream()
                .map(chatRoom -> {
                    return ChatDto.All.builder()
                            .chatRoomId(chatRoom.getChatRoomId())
                            .chatRoomName(chatRoom.getChatRoomName())
                            .createUserNumber(chatRoom.getCreateUserNumber())
                            .chatRoomName(chatRoom.getChatRoomName())
                            .createdAt(chatRoom.getCreatedAt())
                            .build();
                }).toList();

        return ChatDto.AllResponse.builder()
                .chatRoomList(response)
                .build();
    }

    public ChatRoom getRoomInfo(String chatRoomId) throws GlobalException {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND, "채팅방을 찾을 수 없습니다."));
    }

    public String createRoom(String userEmail, String message) {
        log.info("새 채팅방 개설 : {}", userEmail);

        User user = userService.getUserByEmail(userEmail);
        Student student = userService.getStudentByEmail(userEmail);

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .chatRoomName(message)
                .createUserNumber(user.getUserNumber())
                .createUserName(student.getStuNumber() + " " + student.getStuName())
                .createdAt(LocalDate.now())
                .build();
        chatRoomRepository.save(chatRoom);

        log.info("채팅방 개설 성공");
        return chatRoom.getChatRoomId();
    }

    public void saveChatMessage(ChatMessage chatMessage) throws GlobalException {
        log.info("메시지 저장");

        try {
            ChatRoom chatRoom = this.getRoomInfo(chatMessage.getChatRoomId());
            chatRoom.addMessage(chatMessage);
        } catch (GlobalException e) {
            throw e;
        }
    }
}
