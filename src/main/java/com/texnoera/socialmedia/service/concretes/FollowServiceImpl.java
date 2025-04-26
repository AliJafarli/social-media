package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.AlreadyFollowingException;
import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.FollowMapper;
import com.texnoera.socialmedia.model.entity.Follow;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.FollowRequest;
import com.texnoera.socialmedia.model.response.follow.FollowResponse;
import com.texnoera.socialmedia.repository.FollowRepository;
import com.texnoera.socialmedia.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public FollowResponse add(FollowRequest followAddRequest) {
        if (userService.isFollowing(followAddRequest.getUserId(), followAddRequest.getFollowingId())) {
            throw new AlreadyFollowingException(ExceptionConstants.ALREADY_FOLLOWING_EXCEPTION.getUserMessage());
        }

        User follower = userRepository.findById(followAddRequest.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.FOLLOWER_USER_NOT_FOUND_BY_ID.getUserMessage()));

        User following = userRepository.findById(followAddRequest.getFollowingId())
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.FOLLOWING_USER_NOT_FOUND_BY_ID.getUserMessage()));

        Follow follow = new Follow();
        follow.setUser(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

        return followMapper.followToResponse(follow);
//        if (userService.isFollowing(followAddRequest.getUserId(), followAddRequest.getFollowingId())) {
//            throw new AlreadyFollowingException(ExceptionConstants.ALREADY_FOLLOWING_EXCEPTION.getUserMessage());
//        }
//        Follow follow = followMapper.addRequestToFollow(followAddRequest);
//        followRepository.save(follow);
//        return followMapper.followToResponse(follow);
    }

    @Override
    public void delete(FollowRequest followRequest) {

        followRepository.
                findByUser_IdAndFollowing_Id(followRequest.getUserId(),
                        followRequest.getFollowingId()).
                ifPresent(followRepository::delete);
    }
}
