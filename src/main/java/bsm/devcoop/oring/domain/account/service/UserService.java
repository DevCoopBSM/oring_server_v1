package bsm.devcoop.oring.domain.account.service;

import bsm.devcoop.oring.entity.account.student.Student;
import bsm.devcoop.oring.entity.account.student.repository.StudentRepository;
import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.entity.account.user.repository.UserRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
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

    public String getUserEmailById(String userNumber) throws GlobalException {
        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND, "존재하지 않는 사용자입니다."));
        return user.getUserEmail();
    }



    public Student getStudentByEmail(String stuEmail) {
        return studentRepository.findByStuEmail(stuEmail);
    }
}
