package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByUser_Id(Integer userId, Pageable pageable);
    Page<Comment> findAllByPost_Id(Integer postId, Pageable pageable);

    void deleteById(Integer id);
}
