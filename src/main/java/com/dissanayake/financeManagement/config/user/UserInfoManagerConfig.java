package com.dissanayake.financeManagement.config.user;

import com.dissanayake.financeManagement.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserInfoManagerConfig implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) {
        System.out.println(emailId);

        boolean isEmail = userInfoRepository.findByEmailId(emailId).isPresent();
        System.out.println(isEmail);

        return userInfoRepository
                .findByEmailId(emailId)
                .map(UserInfoConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("UserEmail: "+ emailId +" does not exist"));
    }
}
