package com.fivepotato.eggmeetserver.config;

import com.fivepotato.eggmeetserver.provider.security.AppTokenFilter;
import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 직접 만든 AppTokenProvider 와 AppTokenFilter 를 SecurityConfig 에 적용할 때 사용
@RequiredArgsConstructor
public class AppTokenSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AppTokenProvider appTokenProvider;

    // AppTokenProvider를 주입받아서 AppTokenFilter를 통해 Security 로직에 필터를 등록

    @Override
    public void configure(HttpSecurity http) {
        AppTokenFilter customFilter = new AppTokenFilter(appTokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
