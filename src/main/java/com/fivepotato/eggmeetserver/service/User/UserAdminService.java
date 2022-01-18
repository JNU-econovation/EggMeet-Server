package com.fivepotato.eggmeetserver.service.user;

import com.fivepotato.eggmeetserver.domain.ban.BanRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.domain.user.UserRepository;
import com.fivepotato.eggmeetserver.dto.user.UserProfileDto;
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


    public void banUser(BanDto banDto) {
        banRepository.save(banDto.toEntity());
    }

    public List<UserProfileDto> getAllUserProfileDto() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserProfileDto::new).collect(Collectors.toList());
    }
}
