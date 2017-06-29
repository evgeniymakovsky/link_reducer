package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.Role;
import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.RoleService;
import com.evgeniymakovsky.service.UserService;
import com.evgeniymakovsky.utils.SendMailSSL;
import com.evgeniymakovsky.utils.UsernameChecker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Class RegistrationController is the main controller for signup.xhtml.
 */
@ManagedBean
@SessionScoped
@Component
public class RegistrationController {

    final static Logger logger = Logger.getLogger(RegistrationController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    @Autowired
    @ManagedProperty("#{RoleService}")
    private RoleService roleService;

    private String username;
    private String email;
    private String rawPassword;
    private String encryptedPassword;
    private String verifyPassword;

    /**
     * Method registerUser invokes when user register himself in Link Reducer
     *
     * @return status=nameError if user don't enter your name, status=userExists if user with same name
     * has already exists, status=verifyError if user don't enter password or failed verification of own
     * password, status=success if user successfully registered.
     */
    public String registerUser() {
        logger.info("Start registerUser()");
        User user = new User();
        List<User> users = userService.userList();
        if (username.equals("") || username == null) return "signup.xhtml?status=nameError&faces-redirect=true";
        if (!UsernameChecker.checkIfUsernameExists(users, username)) {
            user.setName(username);
            logger.info("Set name " + username);
        } else {
            logger.warn("User with username " + username + " exists!");
            return "signup.xhtml?status=userExists&faces-redirect=true";
        }

        user.setEmail(email);

        if (rawPassword.equals("") || rawPassword == null) return "signup.xhtml?status=verifyError&faces-redirect=true";

        if (rawPassword.equals(verifyPassword)) {
            logger.info("Password successfully verified!");
            encryptedPassword = new BCryptPasswordEncoder().encode(rawPassword);
            user.setPassword(encryptedPassword);
        } else {
            logger.warn("Failed verifying password!");
            return "signup.xhtml?status=verifyError&faces-redirect=true";
        }

        user.setEnabled(true);
        userService.saveUser(user);

        Role role = new Role();
        role.setRole("ROLE_USER");
        role.setUser(user);
        roleService.saveRole(role);
        logger.info("User " + username + " successfully registered!");

        SendMailSSL sendMailSSL = new SendMailSSL();
        sendMailSSL.setRecipientMail(email);
        String subject = "Welcome, " + username;
        String text = "Hello, dear " + username
                + "\nYour password is: " + rawPassword
                + "\nYour email is: " + email;
        sendMailSSL.setSubjectMail(subject);
        sendMailSSL.setTextMail(text);
        sendMailSSL.sendEmail();
        return "signup.xhtml?status=success&faces-redirect=true";
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
