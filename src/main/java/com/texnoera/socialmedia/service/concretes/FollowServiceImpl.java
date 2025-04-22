package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.AlreadyFollowingException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.FollowMapper;
import com.texnoera.socialmedia.model.entity.Follow;
import com.texnoera.socialmedia.model.request.FollowRequest;
import com.texnoera.socialmedia.model.response.follow.FollowResponse;
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
    public FollowResponse add(FollowRequest followAddRequest) {

        if (userService.isFollowing(followAddRequest.getUserId(), followAddRequest.getFollowingId())) {
            throw new AlreadyFollowingException(ExceptionConstants.ALREADY_FOLLOWING_EXCEPTION.getUserMessage());
        }
        Follow follow = followMapper.addRequestToFollow(followAddRequest);
        followRepository.save(follow);
        return followMapper.followToResponse(follow);
    }

    @Override
    public void delete(FollowRequest followRequest) {

        followRepository.
                findByUser_IdAndFollowing_Id(followRequest.getUserId(),
                        followRequest.getFollowingId()).
                ifPresent(followRepository::delete);
    }
}
