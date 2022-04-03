package com.takebasic.demospringsecurityapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "USER",uniqueConstraints =
        {
                @UniqueConstraint(columnNames = {"username", "email"})
        })
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @JsonIgnore
    @Column(name = "password")
    @Size(min = 3,max = 255)
    private String password;
    @Email
    private String email;
    @Lob
    private String avatar;
    @ManyToMany
    @JoinTable(name = "USER_ROLE", joinColumns =
            {
                @JoinColumn(name = "USER_ID")
            },inverseJoinColumns =
            {
                    @JoinColumn(name = "ROLE_ID")
            })
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User(Long id, String name, String username, String password, String email, String avatar, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }

    public User(String name, String username, String password, String email, String avatar, Set<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
