package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.UserService;
import com.evgeniymakovsky.utils.RandomStringGenerator;
import com.evgeniymakovsky.service.SendMailSSLServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Class ForgetPasswordController is managed bean for forget.xhtml view.
 */
@ManagedBean(eager = true)
@ViewScoped
@Component
public class ForgetPasswordController {

    final static Logger LOGGER = Logger.getLogger(ForgetPasswordController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    @Autowired
    @ManagedProperty("#{SendMailSSLService}")
    private SendMailSSLServiceImpl sendMailSSLServiceImpl;

    private String username;
    private String email;
    private User user;

    /**
     * Method sendPassword() sends password to registered users email if user forget password.
     *
     * @return status=usernameError if user don't enter your name, status=noUser if user not exists
     * in database, status=noEmail if entered email don't match with users email, status=emailError
     * if user don't enter your email and status=success if email successfully sent.
     */
    public String sendPassword() {
        if (username != null) {
            user = userService.findByUserName(username);
        } else {
            LOGGER.error("User is null");
            return "forget.xhtml?status=usernameError&faces-redirect=true";
        }

        if (user == null) {
            LOGGER.error("No user");
            return "forget.xhtml?status=noUser&faces-redirect=true";
        }

        String newPassword = RandomStringGenerator.getRandomString(8);
        if (email != null) {
            if (email.equals(user.getEmail())) {
                sendMailSSLServiceImpl.setRecipientMail(email);
                sendMailSSLServiceImpl.setSubjectMail("Your new password from Link Reducer!");
                sendMailSSLServiceImpl.setTextMail("Hello, dear " + username + "!" +
                        "\nYour new password is: " + newPassword);
                sendMailSSLServiceImpl.sendEmail();
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encryptedPassword = encoder.encode(newPassword);
                userService.changePassword(username, encryptedPassword);
                LOGGER.info("Email sent to " + email);
                return "forget.xhtml?status=success&faces-redirect=true";
            } else {
                LOGGER.error("Not match email");
                return "forget.xhtml?status=noEmail&faces-redirect=true";
            }
        } else {
            LOGGER.error("Email is null");
            return "forget.xhtml?status=emailError&faces-redirect=true";
        }
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
}
