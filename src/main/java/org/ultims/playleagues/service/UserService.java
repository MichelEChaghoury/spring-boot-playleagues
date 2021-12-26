package org.ultims.playleagues.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ultims.playleagues.model.Role;
import org.ultims.playleagues.model.User;
import org.ultims.playleagues.repository.v1.RoleRepository;
import org.ultims.playleagues.repository.v1.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setName("Admin");
        adminRole.setDescription("Admin Role - can create, read, update and delete");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("User");
        userRole.setDescription("Default Role - can read only");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("michel.e.chaghoury@playleagues.com");
        adminUser.setPassword(getEncodedPassword("admin@pass"));
        adminUser.setFirstName("michel");
        adminUser.setLastName("chaghoury");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

        User defaultUser = new User();
        defaultUser.setUserName("user@playleagues.com");
        defaultUser.setPassword(getEncodedPassword("user@pass"));
        defaultUser.setFirstName("jhon");
        defaultUser.setLastName("doe");
        Set<Role> defaultRole = new HashSet<>();
        defaultRole.add(userRole);
        defaultUser.setRole(defaultRole);
        userRepository.save(defaultUser);

    }

    public User registerNewUser(User user) {
        Role role = roleRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
