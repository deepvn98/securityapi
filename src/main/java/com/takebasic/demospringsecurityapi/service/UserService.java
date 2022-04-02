package com.takebasic.demospringsecurityapi.service;

import com.takebasic.demospringsecurityapi.model.User;

import java.util.List;

public interface UserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
    List<User> users();
    void saveUser(User user);
    void removeUser(Long id);
    boolean existsByRolename(String rolename);
}
