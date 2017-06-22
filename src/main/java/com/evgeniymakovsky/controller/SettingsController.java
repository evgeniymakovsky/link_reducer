package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
@Component
public class SettingsController {

    final static Logger logger = Logger.getLogger(SettingsController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    private User user;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public String changePassword() {
        logger.info("Start changePassword()");
        if (encoder.matches(oldPassword, user.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                userService.changePassword(user.getName(), encoder.encode(newPassword));
                logger.info("Password successfully changed!");
                return "/views/settings.xhtml?status=changed&faces-redirect=true";
            }
            logger.warn("New password don't match with confirmed password!");
            return "/views/settings.xhtml?status=notMatch&faces-redirect=true";
        }
        logger.warn("Old password wrong!");
        return "/views/settings.xhtml?status=error&faces-redirect=true";
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getOldPassword() {
        user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
