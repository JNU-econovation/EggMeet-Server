package com.fivepotato.eggmeetserver.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.fivepotato.eggmeetserver.domain.user.User user = userService.getUserByEmail(email);
        return createUserDetails(user);
    }


    private UserDetails createUserDetails(com.fivepotato.eggmeetserver.domain.user.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());

        return new User(
                String.valueOf(user.getId()),
                user.getEncodedEmail(),
                Collections.singleton(grantedAuthority)
        );
    }
}
