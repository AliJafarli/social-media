package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUser_Id(Long userId);
    List<Comment> findAllByPost_Id(Long postId);
    void deleteById(Long id);
}
