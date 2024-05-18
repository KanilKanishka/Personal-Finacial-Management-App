package com.budgetapp.mapper;

import com.budgetapp.entity.UserInfo;
import com.budgetapp.dto.UserRegistrationDto;
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

