package com.fivepotato.eggmeetserver.service.User;

import com.fivepotato.eggmeetserver.domain.Ban.BanRepository;
import com.fivepotato.eggmeetserver.domain.User.User;
import com.fivepotato.eggmeetserver.domain.User.UserRepository;
import com.fivepotato.eggmeetserver.dto.User.UserProfileDto;
import com.fivepotato.eggmeetserver.dto.ban.BanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserAdminService {

    private final UserRepository userRepository;
    private final BanRepository banRepository;

    public boolean getIsBannedUserByEmail(String email) {
        return banRepository.existsByBannedEmail(email);
    }

    public void banUser(BanDto banDto) {
        banRepository.save(banDto.toEntity());
    }

    public List<UserProfileDto> getAllUserProfileDto() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserProfileDto::new).collect(Collectors.toList());
    }
}
