package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.entity.RefreshToken;
import com.texnoera.socialmedia.model.entity.User;

public interface RefreshTokenService {

    RefreshToken generateOrUpdateRefreshToken(User user);

}
