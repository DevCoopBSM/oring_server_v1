package bsm.devcoop.oring.domain.account.service;

import bsm.devcoop.oring.entity.account.student.Student;
import bsm.devcoop.oring.entity.account.student.repository.StudentRepository;
import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.entity.account.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public User getUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    public String getUserCodeByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        return user.getUserCode();
    }

    public Student getStudentByEmail(String stuEmail) {
        return studentRepository.findByStuEmail(stuEmail);
    }
}
