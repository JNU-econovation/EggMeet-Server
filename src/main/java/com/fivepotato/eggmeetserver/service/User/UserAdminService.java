package com.fivepotato.eggmeetserver.service.User;

import com.fivepotato.eggmeetserver.domain.Ban.BanRepository;
import com.fivepotato.eggmeetserver.dto.ban.BanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAdminService {

    private final UserService userService;
    private final BanRepository banRepository;

    public boolean getIsBannedUserByEmail(String email) {
        return banRepository.existsByBannedEmail(email);
    }

    public void banUser(BanDto banDto) {
        banRepository.save(banDto.toEntity());
    }
}
