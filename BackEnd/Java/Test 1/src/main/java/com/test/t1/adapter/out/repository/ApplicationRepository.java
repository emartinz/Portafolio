package com.test.t1.adapter.out.repository;
import org.springframework.stereotype.Repository;

import com.test.t1.domain.model.entity.Application;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
     
}
