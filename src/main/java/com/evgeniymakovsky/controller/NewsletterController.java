package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.UserService;
import com.evgeniymakovsky.service.SendMailSSLServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Class NewsletterController is managed bean for newsletter bar in bottom of pages
 */
@ManagedBean(eager = true)
@ViewScoped
@Component
public class NewsletterController {

    final static Logger LOGGER = Logger.getLogger(NewsletterController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    @Autowired
    @ManagedProperty("#{SendMailSSLService}")
    private SendMailSSLServiceImpl sendMailSSLServiceImpl;

    private User user;
    private String username;
    private String userEmail;
    private String enteredEmail;

    /**
     * Method sendNewsletter sends newsletter for user if he enter yours correct email in bottom
     * of page. If user isn't authenticate or user email don't match to entered email it throws
     * IllegalStateException.
     */
    public void sendNewsletter() {
        LOGGER.info("Start sendNewsletter()");
        user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            userEmail = user.getEmail();
            username = user.getName();
            LOGGER.info("username: " + username);
        } else {
            LOGGER.error("user is null");
            throw new IllegalStateException("User not authenticate");
        }

        if (userEmail.equals(enteredEmail)) {
            sendMailSSLServiceImpl = new SendMailSSLServiceImpl();
            sendMailSSLServiceImpl.setRecipientMail(enteredEmail);
            sendMailSSLServiceImpl.setSubjectMail("Newsletter from Link Reducer!");
            sendMailSSLServiceImpl.setTextMail("Hi, dear " + username +
                    "\nThis is newsletter!");
            sendMailSSLServiceImpl.sendEmail();
            LOGGER.info("Email has been sent to " + enteredEmail);
        } else {
            LOGGER.warn("Email hasn't been sent, because userEmail don't match to enteredEmail!");
            throw new IllegalStateException("Email hasn't been sent, because user email don't match to entered email!");
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEnteredEmail() {
        return enteredEmail;
    }

    public void setEnteredEmail(String enteredEmail) {
        this.enteredEmail = enteredEmail;
    }
}