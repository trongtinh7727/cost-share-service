package com.iiex.cost_share_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

}
