package com.ajaysw.service;

import com.ajaysw.model.Role;
import com.ajaysw.model.User;

import java.util.List;

/**
 * @author Ajay Wankhade
 */
public interface IRoleService {
    List<Role> getRoles();
    Role createRole(Role theRole);

    void deleteRole(Long id);
    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
