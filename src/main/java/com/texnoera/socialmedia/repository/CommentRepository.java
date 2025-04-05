package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByUser_Id(Integer userId);
    List<Comment> findAllByPost_Id(Integer postId);
    void deleteById(Integer id);
}
