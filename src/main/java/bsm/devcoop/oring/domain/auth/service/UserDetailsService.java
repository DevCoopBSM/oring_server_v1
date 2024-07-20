package bsm.devcoop.oring.domain.auth.service;

import bsm.devcoop.oring.domain.auth.CustomUserDetails;
import bsm.devcoop.oring.domain.auth.User;
import bsm.devcoop.oring.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }

        return new CustomUserDetails(user);
    }
}
