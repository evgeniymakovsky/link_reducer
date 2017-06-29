package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.LinkService;
import com.evgeniymakovsky.service.UserService;
import com.evgeniymakovsky.utils.LinkChecker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.evgeniymakovsky.utils.RandomStringGenerator;

import java.util.List;

/**
 * Class WelcomeController is the main controller for views/welcome.xhtml.
 */
@ManagedBean
@SessionScoped
@Component
public class WelcomeController {

    final static Logger logger = Logger.getLogger(WelcomeController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    @Autowired
    @ManagedProperty("#{LinkService}")
    private LinkService linkService;

    private User user;
    private String username;
    private String originalURL;
    private String reducedURL;

    /**
     * Method generateReducedURL generate reduced link and save it in database
     *
     * @return URL with reduced link
     */
    public String generateReducedURL() {
        logger.info("Start generateReducedURL()");
        List<Link> links = linkService.findAll();

        if (!LinkChecker.checkIfLinkProtocolExists(originalURL)) {
            originalURL = "http://" + originalURL;
            logger.info("Add HTTP protocol to link " + originalURL);
        }

        String existedShortedLink = LinkChecker.checkIfOriginalLinkExists(links, originalURL);

        if (existedShortedLink != null) {
            logger.info("Link " + existedShortedLink + " exists in database!");
            reducedURL = "localhost:8085/" + existedShortedLink;
            return reducedURL;
        }

        String uri = RandomStringGenerator.getRandomString(6);

        while (LinkChecker.checkIfShortedLinkExists(links, uri)) {
            logger.warn("Shorted link " + uri + " has already exists!");
            uri = RandomStringGenerator.getRandomString(6);
        }

        reducedURL = "localhost:8085/" + uri;
        Link link = new Link();
        link.setOriginal(originalURL);
        link.setShorted(uri);
        link.setInvocations(0);
        link.setUser(user);
        linkService.saveLink(link);
        logger.info("Reduced link " + reducedURL + " has been saved!");
        return reducedURL;
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
        username = SecurityContextHolder.getContext().getAuthentication().getName();
        user = userService.findByUserName(username);
        logger.info("User " + username);
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LinkService getLinkService() {
        return linkService;
    }

    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getReducedURL() {
        return reducedURL;
    }

    public void setReducedURL(String reducedURL) {
        this.reducedURL = reducedURL;
    }
}