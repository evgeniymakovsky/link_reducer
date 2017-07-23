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
import javax.faces.bean.ViewScoped;

/**
 * Class SettingsController is the main controller for views/settings.xhtml.
 */

@ManagedBean(eager = true)
@ViewScoped
@Component
public class SettingsController {

    final static Logger LOGGER = Logger.getLogger(SettingsController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    private User user;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    /**
     * Method changePassword invokes when user change his own password
     *
     * @return status=changed if password changed, status=notMatch if user fails verification,
     * status=error if user entered wrong actual password.
     */
    public String changePassword() {
        LOGGER.info("Start changePassword()");
        if (encoder.matches(oldPassword, user.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                userService.changePassword(user.getName(), encoder.encode(newPassword));
                LOGGER.info("Password successfully changed!");
                return "/views/settings.xhtml?status=changed&faces-redirect=true";
            }
            LOGGER.warn("New password don't match with confirmed password!");
            return "/views/settings.xhtml?status=notMatch&faces-redirect=true";
        }
        LOGGER.warn("Old password wrong!");
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
