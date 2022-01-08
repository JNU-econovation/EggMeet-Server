package com.fivepotato.eggmeetserver.service.User;

import com.fivepotato.eggmeetserver.domain.User.LoginType;
import com.fivepotato.eggmeetserver.domain.User.User;
import com.fivepotato.eggmeetserver.domain.User.UserRepository;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Boolean getIsExistUserByEmail(LoginType loginType, String email) {
        return userRepository.existsByLoginTypeAndEmail(loginType, email);
    }

    public Boolean getIsExistUserByName(String name) {
        return userRepository.existsByName(name);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_EMAIL + email));
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_USERID + userId));
    }
}
