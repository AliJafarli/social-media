package com.texnoera.socialmedia.repository;


import com.texnoera.socialmedia.model.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByUser_Id(Long userId);
    Optional<Follow> findByUser_IdAndFollowing_Id(Long userId, Long followingId);

}
