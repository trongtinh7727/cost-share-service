package com.iiex.cost_share_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entity.User;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

}
