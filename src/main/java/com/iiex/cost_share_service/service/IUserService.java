package com.iiex.cost_share_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.entity.User;

@Service
public interface IUserService {
    List<User> getAllUsers();

    public User createUser(User user);

    public User getUserById(Long userId);

    public User getUserByUsername(String username);

    public Optional<User> getUserByEmail(String email);

    public void deleteUser(Long userId);
}
