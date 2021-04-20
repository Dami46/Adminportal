package com.adminportal.Service;

import com.adminportal.Domain.User;
import com.adminportal.Security.UserRole;

import java.util.Set;

public interface UserService {

	User createUser(User user, Set<UserRole> userRoles) throws Exception;

	User save(User user);

	User findById(Long id);
}
