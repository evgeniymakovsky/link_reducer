package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test(expected = IllegalStateException.class)
    public void saveUserTest_call_nullUser_expect_IllegalStateException() {
        userService.saveUser(null);
    }

    @Test
    public void saveUserTest_expect_ok() {
        User user = new User();
        user.setName("UserName");
        user.setPassword("Password");
        user.setEnabled(true);
        user.setEmail("user@user.com");
        userService.saveUser(user);
        User userChecked = userService.findByUserName("UserName");
        assertTrue(userChecked != null);
    }

    @Test
    public void changePasswordTest_expect_ok() {
        User user = new User();
        user.setName("UserName");
        user.setPassword("Password");
        user.setEnabled(true);
        user.setEmail("user@user.com");
        userService.saveUser(user);
        userService.changePassword("UserName", "New Password");
        user = userService.findByUserName("UserName");
        String changedPassword = user.getPassword();

        assertTrue("New Password".equals(changedPassword));
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void removeUserTest_expect_InvalidDataAccessApiUsageException() {
        userService.removeUser(null);
    }

    @Test
    public void removeUserTest_expect_ok() {
        User user = new User();
        user.setName("UserName");
        user.setPassword("Password");
        user.setEnabled(true);
        user.setEmail("user@user.com");
        userService.saveUser(user);
        userService.removeUser(user);
        assertTrue(userService.findByUserName("UserName") == null);
    }
}
