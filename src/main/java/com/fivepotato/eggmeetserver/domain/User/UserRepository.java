package com.fivepotato.eggmeetserver.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginTypeAndEmail(LoginType loginType, String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Page<User> findUsersByMentorAreaIsNotNullAndNicknameContaining(Pageable pageable, String nickname);

    Page<User> findUsersByMenteeAreaIsNotNullAndNicknameContaining(Pageable pageable, String nickname);
}
