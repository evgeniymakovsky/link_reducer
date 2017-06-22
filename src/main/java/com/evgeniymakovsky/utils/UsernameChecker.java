package com.evgeniymakovsky.utils;

import com.evgeniymakovsky.controller.HomeController;
import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.entity.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by mak on 13.6.17.
 */
public class UsernameChecker {

    final static Logger logger = Logger.getLogger(UsernameChecker.class);

    public static boolean checkIfUsernameExists(List<User> users, String username) {
        logger.info("Start checkIfUsernameExists(List<User> users, String username)");
        for (User user : users) {
            if (user.getName().equals(username)) return true;
        }
        return false;
    }
}
