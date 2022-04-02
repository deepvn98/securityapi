package com.takebasic.demospringsecurityapi.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @NotNull
    private String rolename;

    public Role(Long id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
