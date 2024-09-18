package com.ajay.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void assignRoleToUser(User user) {
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(User user) {
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

//    public void removeAllUsersFromRole(User user) {
////        if(this.getUsers() != null){
////            List<User> roleUsers = this.getUsers().parallelStream().toList();
////            roleUsers.forEach(this :: removeUserFromRole);
////        }
//        if (this.getUsers() != null) {
//            // Convert to list to avoid ConcurrentModificationException while iterating
//            List<User> roleUsers = this.getUsers().parallelStream().toList();
//
//            // Iterate over each user and remove them from this role
//            roleUsers.forEach(this::removeUserFromRole);
//        }
//    }
public void removeAllUsersFromRole() {
    if (this.getUsers() != null) {
        // Convert to list to avoid ConcurrentModificationException while iterating
        List<User> roleUsers = this.getUsers().parallelStream().toList();

        // Iterate over each user and remove them from this role
        roleUsers.forEach(this::removeUserFromRole);
    }
}

    public String getName(){
        return name!=null?name:"";
    }
}
