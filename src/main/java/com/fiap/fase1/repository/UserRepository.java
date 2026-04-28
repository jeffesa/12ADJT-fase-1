package com.fiap.fase1.repository;

import com.fiap.fase1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    List<User> findByNameContainingIgnoreCase(String name);
}
