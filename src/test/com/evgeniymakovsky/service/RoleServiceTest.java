package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Role;
import com.evgeniymakovsky.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RoleServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void saveRoleTest_call_nullRole_expect_InvalidDataAccessApiUsageException() {
        roleService.saveRole(null);
    }

    @Test
    public void saveRoleTest_expect_ok() {
        User user = new User();
        user.setName("UserName");
        user.setPassword("Password");
        user.setEnabled(true);
        user.setEmail("user@user.com");
        userService.saveUser(user);

        Role role = new Role();
        role.setRole("ROLE_USER");
        role.setUser(user);
        roleService.saveRole(role);

        User checkUser = userService.findByUserName("UserName");
        assertTrue(!checkUser.getRoles().isEmpty());
        userService.removeUser(checkUser);
    }
}
