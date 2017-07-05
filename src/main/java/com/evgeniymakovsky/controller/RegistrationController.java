package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.Role;
import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.RoleService;
import com.evgeniymakovsky.service.UserService;
import com.evgeniymakovsky.service.SendMailSSLServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Class RegistrationController is the main controller for signup.xhtml.
 */
@ManagedBean(eager = true)
@ViewScoped
@Component
public class RegistrationController {

    final static Logger LOGGER = Logger.getLogger(RegistrationController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    @Autowired
    @ManagedProperty("#{RoleService}")
    private RoleService roleService;

    @Autowired
    @ManagedProperty("#{SendMailSSLService}")
    private SendMailSSLServiceImpl sendMailSSLServiceImpl;

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
        LOGGER.info("Start registerUser()");
        User user = new User();
        List<User> users = userService.userList();
        if (username.equals("") || username == null) return "signup.xhtml?status=nameError&faces-redirect=true";
        if (!checkIfUsernameExists(users, username)) {
            user.setName(username);
            LOGGER.info("Set name " + username);
        } else {
            LOGGER.warn("User with username " + username + " exists!");
            return "signup.xhtml?status=userExists&faces-redirect=true";
        }

        user.setEmail(email);

        if (rawPassword.equals("") || rawPassword == null) return "signup.xhtml?status=verifyError&faces-redirect=true";

        if (rawPassword.equals(verifyPassword)) {
            LOGGER.info("Password successfully verified!");
            encryptedPassword = new BCryptPasswordEncoder().encode(rawPassword);
            user.setPassword(encryptedPassword);
        } else {
            LOGGER.warn("Failed verifying password!");
            return "signup.xhtml?status=verifyError&faces-redirect=true";
        }

        user.setEnabled(true);
        userService.saveUser(user);

        Role role = new Role();
        role.setRole("ROLE_USER");
        role.setUser(user);
        roleService.saveRole(role);
        LOGGER.info("User " + username + " successfully registered!");

        sendMailSSLServiceImpl.setRecipientMail(email);
        String subject = "Welcome, " + username;
        String text = "Hello, dear " + username
                + "\nYour password is: " + rawPassword
                + "\nYour email is: " + email;
        sendMailSSLServiceImpl.setSubjectMail(subject);
        sendMailSSLServiceImpl.setTextMail(text);
        sendMailSSLServiceImpl.sendEmail();
        return "signup.xhtml?status=success&faces-redirect=true";
    }

    public static boolean checkIfUsernameExists(List<User> users, String username) {
        LOGGER.info("Start checkIfUsernameExists(List<User> users, String username)");
        for (User user : users) {
            if (user.getName().equals(username)) return true;
        }
        return false;
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
