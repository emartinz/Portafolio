package com.test.t1.adapter.out.repository;
import org.springframework.stereotype.Repository;

import com.test.t1.domain.model.entity.Version;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> { 
    
}
