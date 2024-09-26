package com.iiex.cost_share_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iiex.cost_share_service.entities.Contribution;

public interface ContributionRepository extends JpaRepository<Contribution,Long> {
    
}
