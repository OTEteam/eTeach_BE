package com.main.eteach.core.service;

import com.main.eteach.core.entity.User;
import com.main.eteach.core.repository.UserRepository;
import com.main.eteach.core.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail)
        );
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: "+ id)
        );
        return UserPrincipal.create(user);
    }
}
