package com.texnoera.socialmedia.controller;


import com.texnoera.socialmedia.model.request.FollowRequest;
import com.texnoera.socialmedia.model.response.follow.FollowResponse;
import com.texnoera.socialmedia.service.abstracts.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/add")
    public ResponseEntity<FollowResponse> addFollow(@Valid @RequestBody FollowRequest followRequest) {
        log.info("Received request to add follow: userId={} followingId={}",
                followRequest.getUserId(), followRequest.getFollowingId());
        FollowResponse followResponse = followService.add(followRequest);
        log.info("Successfully added follow: userId={} followingId={}",
                followRequest.getUserId(), followRequest.getFollowingId());
        return new ResponseEntity<>(followResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFollow(@Valid @RequestBody FollowRequest followRequest) {
        log.info("Received request to delete follow: userId={} followingId={}",
                followRequest.getUserId(), followRequest.getFollowingId());
        followService.delete(followRequest);
        log.info("Successfully deleted follow: userId={} followingId={}",
                followRequest.getUserId(), followRequest.getFollowingId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
