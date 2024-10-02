package bsm.devcoop.oring.entity.account.user.service;

import bsm.devcoop.oring.entity.account.user.CustomUserDetails;
import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.entity.account.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with userEmail : " + userEmail);
        }

        return new CustomUserDetails(user, new BCryptPasswordEncoder());
    }
}
