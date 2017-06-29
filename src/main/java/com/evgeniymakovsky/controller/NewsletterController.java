package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.UserService;
import com.evgeniymakovsky.utils.SendMailSSL;
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
@ManagedBean
@ViewScoped
@Component
public class NewsletterController {

    final static Logger logger = Logger.getLogger(NewsletterController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    private User user;
    private String username;
    private String userEmail;
    private String enteredEmail;
    private SendMailSSL sendMailSSL;

    /**
     * Method sendNewsletter sends newsletter for user if he enter yours correct email in bottom
     * of page.
     */
    public void sendNewsletter() {
        logger.info("Start sendNewsletter()");
        user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            userEmail = user.getEmail();
            username = user.getName();
            logger.info("username: " + username);
        } else {
            logger.error("user is null");
            return;
        }

        if (userEmail.equals(enteredEmail)) {
            sendMailSSL = new SendMailSSL();
            sendMailSSL.setRecipientMail(enteredEmail);
            sendMailSSL.setSubjectMail("Newsletter from Link Reducer!");
            sendMailSSL.setTextMail("Hi, dear " + username +
                    "\nThis is newsletter!");
            sendMailSSL.sendEmail();
            logger.info("Email has been sent to " + enteredEmail);
        } else logger.warn("Email has been sent, because userEmail don't match to enteredEmail!");
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
