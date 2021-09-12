package com.adminportal.Service.Impl;

import com.adminportal.Domain.User;
import com.adminportal.Repository.UserRepository;
import com.adminportal.Security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("Username not found");
        }
        for (UserRole role : user.getUserRoles()) {
            if (role.getRole().getRoleId() != 0) {
                throw new UsernameNotFoundException("You have no authorization");
            }
        }
        return user;
    }
}
