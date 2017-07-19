package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.LinkService;
import com.evgeniymakovsky.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.evgeniymakovsky.utils.RandomStringGenerator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class WelcomeController is the main controller for views/welcome.xhtml.
 */
@ManagedBean(eager = true)
@ViewScoped
@Component
public class WelcomeController {

    final static Logger LOGGER = Logger.getLogger(WelcomeController.class);

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
        LOGGER.info("Start generateReducedURL()");
        List<Link> links = linkService.findAll();

        if (!checkIfLinkProtocolExists(originalURL)) {
            originalURL = "http://" + originalURL;
            LOGGER.info("Add HTTP protocol to link " + originalURL);
        }

        String existedShortedLink = checkIfOriginalLinkExists(links, originalURL);

        if (existedShortedLink != null) {
            LOGGER.info("Link " + existedShortedLink + " exists in database!");
            reducedURL = "localhost:8080/" + existedShortedLink;
            return reducedURL;
        }

        String uri = RandomStringGenerator.getRandomString(6);

        while (checkIfShortedLinkExists(links, uri)) {
            LOGGER.warn("Shorted link " + uri + " has already exists!");
            uri = RandomStringGenerator.getRandomString(6);
        }

        reducedURL = "localhost:8080/" + uri;
        Link link = new Link();
        link.setOriginal(originalURL);
        link.setShorted(uri);
        link.setInvocations(0);
        link.setUser(user);
        linkService.saveLink(link);
        LOGGER.info("Reduced link " + reducedURL + " has been saved!");
        return reducedURL;
    }

    private static boolean checkIfShortedLinkExists(List<Link> links, String shortedLink) {
        LOGGER.info("Start checkIfShortedLinkExists(List<Link> links, String shortedLink)");
        for (Link link : links) {
            if (link.getShorted().equals(shortedLink)) return true;
        }
        return false;
    }

    private static String checkIfOriginalLinkExists(List<Link> links, String originalLink) {
        LOGGER.info("Start checkIfOriginalLinkExists(List<Link> links, String originalLink)");
        for (Link link : links) {
            if (link.getOriginal().equals(originalLink)) return link.getShorted();
        }
        return null;
    }

    private static boolean checkIfLinkProtocolExists(String originalLink) {
        LOGGER.info("Start checkIfLinkProtocolExists(String originalLink)");
        Pattern pattern = Pattern.compile("^(http|https)://.*$");
        Matcher matcher = pattern.matcher(originalLink);
        return matcher.matches();
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
        LOGGER.info("User " + username);
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