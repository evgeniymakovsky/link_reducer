package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.entity.User;

import java.util.List;

public interface UserService {

    public void saveUser(User user);

    public void removeUser(User user);

    public User findByUserName(String username);

    public List<User> userList();

    public List<Link> retrieveUserLinks(String name);

    public void changePassword(String name, String newPassword);
}