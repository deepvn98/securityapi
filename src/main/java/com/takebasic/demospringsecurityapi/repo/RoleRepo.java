package com.takebasic.demospringsecurityapi.repo;

import com.takebasic.demospringsecurityapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    boolean existsByRolename(String rolename);
    Role findByRolename(String rolename);

}
