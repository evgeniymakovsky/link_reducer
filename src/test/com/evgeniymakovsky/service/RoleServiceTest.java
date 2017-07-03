package com.evgeniymakovsky.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RoleServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void saveUserTest_call_nullUser_expect_IllegalStateException() {
        roleService.saveRole(null);
    }
}
