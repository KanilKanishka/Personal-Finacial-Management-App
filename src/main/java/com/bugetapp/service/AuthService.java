package com.bugetapp.service;


import com.bugetapp.config.jwtAuth.JwtTokenGenerator;
import com.bugetapp.entity.UserInfo;
import com.bugetapp.mapper.UserInfoMapper;
import com.bugetapp.repository.RefreshTokenRepository;
import com.bugetapp.repository.UserInfoRepository;
import com.bugetapp.dto.AuthResponseDto;
import com.bugetapp.dto.TokenType;
import com.bugetapp.dto.UserRegistrationDto;
import com.bugetapp.entity.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserInfoMapper userInfoMapper;


    public Map<String, Object> getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse httpServletResponse) {
        Map<String, Object> response = new HashMap<>();

        try {
            var userInfoEntity = userInfoRepository.findByEmailId(authentication.getName())
                    .orElseThrow(()->{
                        return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND");});

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
            saveUserRefreshToken(userInfoEntity,refreshToken);
            creatRefreshTokenCookie(httpServletResponse,refreshToken);

            response.put("STATUS", "SUCCESS");
            response.put("ACCESS_TOKEN", accessToken);
            response.put("REFRESH_TOKEN", refreshToken);
            response.put("TOKEN_TYPE", TokenType.Bearer);
            response.put("TOKEN_EXPIRY", 15 * 60);
            response.put("USER_NAME", userInfoEntity.getUserName());

            return response;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
        }
    }

    private void saveUserRefreshToken(UserInfo userInfo, String refreshToken) {
        var refreshTokenEntity = RefreshToken.builder()
                .user(userInfo)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60 );
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        if(!authorizationHeader.startsWith(TokenType.Bearer.name())){
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please verify your token type");
        }

        final String refreshToken = authorizationHeader.substring(7);

        var refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
                .filter(tokens-> !tokens.isRevoked())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Refresh token revoked"));

        UserInfo userInfo = refreshTokenEntity.getUser();

        Authentication authentication =  createAuthenticationObject(userInfo);

        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return  AuthResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(userInfo.getUserName())
                .tokenType(TokenType.Bearer)
                .build();
    }

    private static Authentication createAuthenticationObject(UserInfo userInfo) {
        String username = userInfo.getEmailId();
        String password = userInfo.getPassword();
        String roles = userInfo.getRoles();

        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }

    public Map<String, Object> registerUser(UserRegistrationDto userRegistrationDto,
                                        HttpServletResponse httpServletResponse) {

        Map<String, Object> response = new HashMap<>();

        try{
            Optional<UserInfo> user = userInfoRepository.findByEmailId(userRegistrationDto.userEmail());
            if(user.isPresent()){
                response.put("STATUS", "FAILED");
                response.put("MESSAGE", "USER_ALREADY_EXIST");
                return response;
            }

            UserInfo userDetailsEntity = userInfoMapper.convertToEntity(userRegistrationDto);
            Authentication authentication = createAuthenticationObject(userDetailsEntity);

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            UserInfo savedUserDetails = userInfoRepository.save(userDetailsEntity);
            saveUserRefreshToken(userDetailsEntity,refreshToken);

            creatRefreshTokenCookie(httpServletResponse,refreshToken);

            response.put("STATUS", "SUCCESS");
            response.put("ACCESS_TOKEN", accessToken);
            response.put("REFRESH_TOKEN", refreshToken);
            response.put("TOKEN_TYPE", TokenType.Bearer);
            response.put("TOKEN_EXPIRY", 5 * 60);
            response.put("USER_NAME", savedUserDetails.getUserName());

            return response;

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
