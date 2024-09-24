package bsm.devcoop.oring.domain.account.service;

import bsm.devcoop.oring.domain.account.User;
import bsm.devcoop.oring.domain.account.repository.UserRepository;
import bsm.devcoop.oring.domain.account.student.Student;
import bsm.devcoop.oring.domain.account.student.repository.StudentRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import bsm.devcoop.oring.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public boolean isUserEmailExist(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) throws GlobalException {
        User user = userRepository.findByUserEmail(email);
        if (user == null) {
            throw new GlobalException(ErrorCode.Not_Found, "사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    @Transactional
    public String getUserEmailByJwt(String jwtToken) throws GlobalException {
        if (jwtUtil.isExpired(jwtToken)) {
            throw new GlobalException(ErrorCode.Bad_Request, "만료된 혹은 유효하지 않은 토큰입니다.");
        }

        return jwtUtil.getUserEmail(jwtToken);
    }

    @Transactional
    public Student getStudentByEmail(String email) throws GlobalException {
        Student student = studentRepository.findByStuEmail(email);
        if (student == null) {
            throw new GlobalException(ErrorCode.Not_Found, "학생을 찾을 수 없습니다.");
        }
        return student;
    }

    @Transactional
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Transactional(readOnly = true)
    public boolean matchPasswords(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    public User getUserFromHeader(String header) throws GlobalException {
        log.info("Get Email from Header");

        String token = header.substring(7); // "Bearer " 이후의 토큰 부분만 가져옵니다.
        log.info("Token : {}", token);

        // 토큰이 만료되었을 경우, 검사 없이 지나간다
        if (jwtUtil.isExpired(token)) {
            log.warn("Expired token");
            throw new GlobalException(ErrorCode.Bad_Request, "Expired Token");
        }

        // 토큰에서 email 추출
        String email = jwtUtil.getUserEmail(token);
        
        // email 로 유저 정보 찾기
        return userRepository.findByUserEmail(email);
    }

    @Transactional
    public String createVoteJwt(String userName) throws GlobalException {
        log.info("Create JWT by userName : {}", userName);

        return jwtUtil.createVoteAuthorizationJwt(userName);
    }
}
