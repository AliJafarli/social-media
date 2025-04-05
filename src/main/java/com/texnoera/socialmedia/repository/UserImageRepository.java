package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Integer> {

    Optional<UserImage> findByUser_Id(Integer userId);
}
