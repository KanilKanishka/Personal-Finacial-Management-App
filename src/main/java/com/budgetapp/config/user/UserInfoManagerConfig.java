package com.budgetapp.config.user;

import com.budgetapp.repository.UserInfoRepository;
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

        return userInfoRepository
                .findByEmailId(emailId)
                .map(UserInfoConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("UserEmail: "+ emailId +" does not exist"));
    }
}
