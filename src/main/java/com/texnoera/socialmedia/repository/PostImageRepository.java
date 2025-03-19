package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    Optional<PostImage> findPostImageByPost_Id(Long postId);
}
