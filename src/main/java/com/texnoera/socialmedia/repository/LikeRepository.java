package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findAllByUser_Id(Integer userId);
    List<Like> findAllByPost_Id(Integer postId);
    void deleteLikeById(Integer id);
    Optional<Like> findByUser_IdAndPost_Id(Integer userId, Integer postId);
}
