package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    void deleteById(Integer id);
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findUserByEmailAndDeletedFalse(String email);
    Optional<User> findUserByEmail(String email);

    Optional<User> findByUsername(String username);
}
