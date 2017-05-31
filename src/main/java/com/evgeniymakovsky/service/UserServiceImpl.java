package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.entity.User;
import java.util.List;

import com.evgeniymakovsky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    public void saveUser(User user) {
        if(user==null){
            throw new IllegalStateException("User should not be null!");
        }
        repository.saveAndFlush(user);
    }

    public void removeUser(User user) {
        repository.delete(user);
    }

    public User findByUserName(String username) {
        return repository.findByName(username);
    }

    public List<User> userList() {
        return repository.findAll();
    }

    public List<Link> retrieveUserLinks(String name) {
        User user = repository.findByName(name);
        return user.getLinks();
    }

    public void changePassword(String name, String newPassword) {
        repository.changePassword(name, newPassword);
    }
}
