package com.zahangir.konasl.repository;

import com.zahangir.konasl.model.Role;
import com.zahangir.konasl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleAndIsActiveTrue(String role);
    Role findByRole(String role);
}
