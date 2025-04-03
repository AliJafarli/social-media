package com.texnoera.socialmedia.model.response.someResponses;

import com.texnoera.socialmedia.exception.constants.ApiMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IamResponse<P> {
    private String message;
    private P payload;
    private boolean success;

    public static <P> IamResponse<P> createSuccessfulWithNewToken(P payload) {
        return new IamResponse<>(ApiMessage.TOKEN_CREATED_OR_UPDATED.getMessage(), payload,true);
    }
}
