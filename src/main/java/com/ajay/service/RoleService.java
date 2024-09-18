package com.ajay.service;

import com.ajay.exception.RoleAlreadyExistsException;
import com.ajay.exception.UserAlreadyExistsException;
import com.ajay.model.Role;
import com.ajay.model.User;
import com.ajay.repository.RoleRepository;
import com.ajay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;
    private final IUserService userService;
    private final UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
       String roleName = "ROLE_"+theRole.getName().toUpperCase();
       Role role = new Role(roleName);
       if(roleRepository.existsByName(role)){
           throw new RoleAlreadyExistsException(theRole.getName() +" roel already exists");
       }
       return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) throws RoleNotFoundException {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw  new UsernameNotFoundException("User NOt Found");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(user.get().getFirstName() + "is already assigned to the :"+role.get().getName()+" role.");
        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

//    @Override
//    public Role removeAllUsersFromRole(Long roleId) {
////        Optional<Role> role = roleRepository.findById(roleId);
////        role.get().removeAllUsersFromRole();
////        return roleRepository.save(role.get());
//
//    }
@Override
public Role removeAllUsersFromRole(Long roleId) throws RoleNotFoundException {
    // Fetch role by ID and handle the case where it might not be found
    Optional<Role> role = roleRepository.findById(roleId);

    if (role.isPresent()) {
        // Call the updated removeAllUsersFromRole method (without a parameter)
        role.get().removeAllUsersFromRole();

        // Save the updated role to the repository
        return roleRepository.save(role.get());
    } else {
        // Handle role not found case
        throw new RoleNotFoundException("Role with ID " + roleId + " not found.");
    }
}

}
