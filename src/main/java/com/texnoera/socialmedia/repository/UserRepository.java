package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteById(Long id);
    User findByEmail(String email);
}
