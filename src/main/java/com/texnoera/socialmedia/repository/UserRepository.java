package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteById(Long id);
    User findByEmail(String email);

    Optional<User> findUserByEmailAndDeletedFalse(String email);
    Optional<User> findUserByEmail(String email);
}
