package com.takebasic.demospringsecurityapi.controller;

import com.takebasic.demospringsecurityapi.dto.request.UserDto;
import com.takebasic.demospringsecurityapi.dto.restspone.Message;
import com.takebasic.demospringsecurityapi.model.Role;
import com.takebasic.demospringsecurityapi.model.User;
import com.takebasic.demospringsecurityapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        boolean username = userService.existsByUsername(userDto.getUsername());
        if (username) {
            return new ResponseEntity<>(new Message("usernamedaplicate"), HttpStatus.OK);
        }
        boolean email = userService.existsByEmail(userDto.getEmail());
        if (email) {
            return new ResponseEntity<>(new Message("emaildaplicate"), HttpStatus.OK);
        }
        Set<String> strRoles = userDto.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = userService.findByRolename("ROLE_ADMIN");
                    if (adminRole == null) {
                        throw new RuntimeException("Role not found");
                    }
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = userService.findByRolename("ROLE_PM");
                    if (pmRole == null) {
                        throw new RuntimeException("Role not found");
                    }
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = userService.findByRolename("ROLE_USER");
                    if (userRole == null) {
                        throw new RuntimeException("Role not found");
                    }
                    roles.add(userRole);
            }
        });
        User user = new User(userDto.getUsername(),passwordEncoder.encode(userDto.getPassword()),userDto.getEmail(),roles);
        userService.saveUser(user);
        return new ResponseEntity<>(new Message("done"),HttpStatus.ACCEPTED);
    }
}

