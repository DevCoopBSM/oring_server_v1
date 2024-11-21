package bsm.devcoop.oring.domain.account.service;

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

    public String getUserCodeByEmail(String userEmail) {
        return userRepository.findUserCodeByUserEmail(userEmail);
    }
}
