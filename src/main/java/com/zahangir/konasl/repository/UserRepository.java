package com.zahangir.konasl.repository;

import com.zahangir.konasl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndIsActiveTrue(String username);
    int countByUsername(String username);
}
