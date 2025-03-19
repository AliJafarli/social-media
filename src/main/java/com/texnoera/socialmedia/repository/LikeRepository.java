package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByUser_Id(Long userId);
    List<Like> findAllByPost_Id(Long postId);
    void deleteLikeById(Long id);
    Optional<Like> findByUser_IdAndPost_Id(Long userId, Long postId);
}
