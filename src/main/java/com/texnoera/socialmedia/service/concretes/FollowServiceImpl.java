package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.FollowMapper;
import com.texnoera.socialmedia.model.request.FollowRequest;
import com.texnoera.socialmedia.repository.FollowRepository;
import com.texnoera.socialmedia.service.abstracts.FollowService;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final FollowMapper followMapper;
    private final UserService userService;

    @Override
    public void add(FollowRequest followAddRequest) {

        if (userService.isFollowing(followAddRequest.getUserId(), followAddRequest.getFollowingId())) {
            return;
        }
        followRepository.save(followMapper.addRequestToFollow(followAddRequest));
    }

    @Override
    public void delete(FollowRequest followRequest) {

        followRepository.
                findByUser_IdAndFollowing_Id(followRequest.getUserId(),
                        followRequest.getFollowingId()).
                ifPresent(followRepository::delete);
    }
}
