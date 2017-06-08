package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Role;
import com.evgeniymakovsky.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository repository;

    public void saveRole(Role role) {
        repository.saveAndFlush(role);
    }
}
