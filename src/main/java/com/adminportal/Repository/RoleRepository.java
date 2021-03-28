package com.adminportal.Repository;

import com.adminportal.Security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByname(String name);
}
