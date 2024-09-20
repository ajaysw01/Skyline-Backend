package com.ajaysw.controller;

import com.ajaysw.exception.RoleAlreadyExistException;
import com.ajaysw.model.Role;
import com.ajaysw.model.User;
import com.ajaysw.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

/**
 * @author Ajay Wankhade
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "APIs for managing roles")

public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/all-roles")
    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    @ApiResponse(responseCode = "302", description = "Roles found",
            content = @Content(schema = @Schema(implementation = Role.class)))

    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getRoles(), FOUND);
    }

    @PostMapping("/create-new-role")
    @Operation(summary = "Create a new role", description = "Creates a new role in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role created successfully"),
            @ApiResponse(responseCode = "409", description = "Role already exists")
    })
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try{
            roleService.createRole(theRole);
            return ResponseEntity.ok("New role created successfully!");
        }catch(RoleAlreadyExistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());

        }
    }
    @DeleteMapping("/delete/{roleId}")
    @Operation(summary = "Delete a role", description = "Deletes a role by its ID")
    public void deleteRole(@PathVariable("roleId") Long roleId){

        roleService.deleteRole(roleId);
    }


    @PostMapping("/remove-all-users-from-role/{roleId}")
    @Operation(summary = "Remove all users from a role", description = "Removes all users from a specified role")
    @ApiResponse(responseCode = "200", description = "Users removed from role",
            content = @Content(schema = @Schema(implementation = Role.class)))
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        return roleService.removeAllUsersFromRole(roleId);
    }


    @PostMapping("/remove-user-from-role")
    @Operation(summary = "Remove a user from a role", description = "Removes a specific user from a role")
    @ApiResponse(responseCode = "200", description = "User removed from role",
            content = @Content(schema = @Schema(implementation = User.class)))

    public User removeUserFromRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        return roleService.removeUserFromRole(userId, roleId);
    }
    @PostMapping("/assign-user-to-role")
    @Operation(summary = "Assign a user to a role", description = "Assigns a specific user to a role")
    @ApiResponse(responseCode = "200", description = "User assigned to role",
            content = @Content(schema = @Schema(implementation = User.class)))

    public User assignUserToRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        return roleService.assignRoleToUser(userId, roleId);
    }
}
