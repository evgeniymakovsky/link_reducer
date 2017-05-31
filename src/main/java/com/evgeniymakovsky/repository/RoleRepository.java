package com.evgeniymakovsky.repository;

import com.evgeniymakovsky.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
