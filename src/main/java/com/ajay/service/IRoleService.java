package com.ajay.service;

import com.ajay.model.Role;
import com.ajay.model.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role createRole(Role theRole);
    void deleteRole(Long id) throws RoleNotFoundException;
    Role findByName(String name);
    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId) throws RoleNotFoundException;
}
