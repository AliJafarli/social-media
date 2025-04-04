package com.texnoera.socialmedia.model.response.someResponses;

import com.texnoera.socialmedia.exception.constants.ApiMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IamResponse<P> {
    private String message;
    private P payload;
    private boolean success;

    public static <P> IamResponse<P> createSuccessful(P payload) {
        return new IamResponse<>("", payload, true);
    }

    public static <P> IamResponse<P> createSuccessfulWithNewToken(P payload) {
        return new IamResponse<>(ApiMessage.TOKEN_CREATED_OR_UPDATED.getMessage(), payload,true);
    }
}
