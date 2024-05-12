package com.dissanayake.financeManagement.mapper;

import com.dissanayake.financeManagement.dto.UserRegistrationDto;
import com.dissanayake.financeManagement.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserInfoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserInfo convertToEntity(UserRegistrationDto userRegistrationDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userRegistrationDto.userName());
        userInfo.setEmailId(userRegistrationDto.userEmail());
        userInfo.setMobileNumber(userRegistrationDto.userMobileNo());
        userInfo.setRoles(userRegistrationDto.userRole());
        userInfo.setPassword(passwordEncoder.encode(userRegistrationDto.userPassword()));
        return userInfo;
    }
}

