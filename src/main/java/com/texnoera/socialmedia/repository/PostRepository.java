package com.texnoera.socialmedia.repository;

import com.texnoera.socialmedia.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAllByUser_Id(Integer userId, Pageable pageable);
    Page<Post> findAllByUser_IdIn(List<Integer> userIds, Pageable pageable);

    void deleteById(Integer id);

    @EntityGraph(attributePaths = {"likes", "user"})
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "postImages")
    Optional<Post> findWithImagesById(Integer id);


}
