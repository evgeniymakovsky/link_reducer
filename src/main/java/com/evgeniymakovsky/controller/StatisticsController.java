package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class StatisticsController {

    final static Logger logger = Logger.getLogger(StatisticsController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    private User user;
    private List<Link> links;

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

    public List<Link> getLinks() {
        user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        links = user.getLinks();
        logger.info("User " + user.getName() + " obtains links");
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
