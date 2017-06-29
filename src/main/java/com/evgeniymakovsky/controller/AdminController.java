package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.User;
import com.evgeniymakovsky.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Class AdminController is managed bean for /admin/admin.xhtml view.
 */
@ManagedBean
@SessionScoped
@Component
public class AdminController {

    final static Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    @ManagedProperty("#{UserService}")
    private UserService userService;

    private List<User> users;

    /**
     * Method deleteUser invokes when admin delete user from database
     * @param responsibleUser - user to delete
     */
    public void deleteUser(User responsibleUser) {
        userService.removeUser(responsibleUser);
        users = userService.userList();
        logger.info("User " + responsibleUser.getName() + " deleted!");
    }

    public List<User> getUsers() {
        users = userService.userList();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
