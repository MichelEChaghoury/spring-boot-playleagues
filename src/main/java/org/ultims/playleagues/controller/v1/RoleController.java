package org.ultims.playleagues.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ultims.playleagues.model.Role;
import org.ultims.playleagues.service.RoleService;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("")
    @PreAuthorize("hasRole('Admin')")
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createNewRole(role);
    }
}
