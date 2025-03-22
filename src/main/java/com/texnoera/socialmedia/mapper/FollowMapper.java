package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.Follow;
import com.texnoera.socialmedia.model.request.FollowRequest;
import com.texnoera.socialmedia.model.response.follow.FollowResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FollowMapper {

    @Mapping(source = "following.id",target = "followingId")
    @Mapping(source = "user.id",target = "followerId")
    @Mapping(target = "followingUsername", expression = "java(follow.getFollowing().getUsername())")
    @Mapping(target = "followerUsername", expression = "java(follow.getUser().getUsername())")
    FollowResponse followToResponse(Follow follow);

    @Mapping(source = "userId",target = "user.id")
    @Mapping(source = "followingId",target = "following.id")
    Follow addRequestToFollow(FollowRequest followRequest);

    List<FollowResponse> followsToResponses(List<Follow> follows);

}
