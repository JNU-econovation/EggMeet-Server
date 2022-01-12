package com.fivepotato.eggmeetserver.config;

import com.fivepotato.eggmeetserver.exception.AppTokenAccessDeniedHandler;
import com.fivepotato.eggmeetserver.exception.AppTokenAuthenticationEntryPointHandler;
import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final AppTokenProvider appTokenProvider;
        private final AppTokenAuthenticationEntryPointHandler appTokenAuthenticationEntryPointHandler;
        private final AppTokenAccessDeniedHandler appTokenAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

        // h2 database 테스트가 원활하도록 관련 API 들은 전부 무시
        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    .antMatchers("/h2-console/**", "/favicon.ico", "/v2/api-docs", "/configuration/ui",
                            "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()

                    // 시큐리티는 기본적으로 세션을 사용
                    // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    // h2-console 을 위한 설정을 추가
                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()

                    // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/ws/chat").permitAll()
//
//                    .antMatchers("/member/list", "/restaurant/list", "/title/**", "/theme/**", "/special/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST, "/notice", "/restaurant").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.PUT, "/notice/{\\d+}", "/restaurant/{\\d+}").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/notice/{\\d+}", "/restaurant/{\\d+}").hasRole("ADMIN")

                    .anyRequest().authenticated()

                    // exception handling 할 때 우리가 만든 클래스를 추가
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(appTokenAuthenticationEntryPointHandler) // 유효한 자격 증명(AppToken)없이 접근 시, 401
                    .accessDeniedHandler(appTokenAccessDeniedHandler) // 필요한 권한 없이 접근 시, 403

                    // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                    .and()
                    .apply(new AppTokenSecurityConfig(appTokenProvider));
        }
}
