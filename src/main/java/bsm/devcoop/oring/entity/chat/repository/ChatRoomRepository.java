package bsm.devcoop.oring.entity.chat.repository;

import bsm.devcoop.oring.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    List<ChatRoom> findAllByCreateUserEmail(String createUserEmail);
}
