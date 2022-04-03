package com.takebasic.demospringsecurityapi.service.iplm;

import com.takebasic.demospringsecurityapi.model.Role;
import com.takebasic.demospringsecurityapi.model.User;
import com.takebasic.demospringsecurityapi.repo.RoleRepo;
import com.takebasic.demospringsecurityapi.repo.UserRepo;
import com.takebasic.demospringsecurityapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceIplm implements UserService, UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceIplm.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("not found user in the database!");
        }
        return user;
    }

    @Override
    public List<User> users() {
        List<User> users = userRepo.findAll();
        if (users==null){
            throw new RuntimeException("not found user list in the database!");
        }
        return users;
    }

    @Override
    public void saveUser(User user) {
            userRepo.save(user);
    }

    @Override
    public void removeUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsByRolename(String rolename) {
        return roleRepo.existsByRolename(rolename);
    }

    @Override
    public Role findByRolename(String rolename) {
        Role role = roleRepo.findByRolename(rolename);
        if (role == null){
            log.error("not found role in the database!");
            throw new UsernameNotFoundException("Role not found in the database");
        }
        return role;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null){
            log.error("not found user in the database!");
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<Role> roles = roleRepo.findAll();
        for (Role role:roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
