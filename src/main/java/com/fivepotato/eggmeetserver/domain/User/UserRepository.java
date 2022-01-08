package com.fivepotato.eggmeetserver.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginTypeAndEmail(LoginType loginType, String email);

    boolean existsByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

}
