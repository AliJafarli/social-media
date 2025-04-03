package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.Follow;
import com.texnoera.socialmedia.model.entity.Role;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.role.RoleResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowerResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserProfileResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    UserFollowerResponse followToFollowerResponse(Follow follow);

    @Mapping(source = "following.id", target = "userId")
    @Mapping(source = "following.username", target = "username")
    UserFollowingResponse followToFollowingResponse(Follow follow);

    @Mapping(source = "followers", target = "followers")
    @Mapping(source = "following", target = "followings")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse userToResponse(User user);

    User requestToUser(UserAddRequest userAddRequest);

    List<UserResponse> usersToResponses(List<User> users);

    List<UserFollowingResponse> followsToFollowingResponses(List<Follow> follows);

    void update(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "token", source = "token")
    UserProfileResponse toUserProfileResponse(User user, String token);


    default List<RoleResponse> mapRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .toList();
    }


}
