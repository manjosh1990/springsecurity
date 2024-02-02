package org.manjosh.practice.service;

import lombok.RequiredArgsConstructor;
import org.manjosh.practice.entity.RefreshToken;
import org.manjosh.practice.repository.RefreshTokenRepository;
import org.manjosh.practice.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserInfoRepository userInfoRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder().userInfo(userInfoRepository.findByUsername(username).get())
                .token(UUID.randomUUID().toString())
                .expiryTime(Instant.now().plusMillis(600000))
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken  verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryTime().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "token has expired. Please sign in");
        }
        return refreshToken;
    }
}
