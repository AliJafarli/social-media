package com.texnoera.socialmedia.repository;


import com.texnoera.socialmedia.model.entity.Follow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    List<Follow> findAllByUser_Id(Integer userId);
    Optional<Follow> findByUser_IdAndFollowing_Id(Integer userId, Integer followingId);

}
