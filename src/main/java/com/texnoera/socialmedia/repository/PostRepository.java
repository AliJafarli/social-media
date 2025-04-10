package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByUser_IdOrderByIdDesc(Integer userId);
    void deleteById(Integer id);
}
