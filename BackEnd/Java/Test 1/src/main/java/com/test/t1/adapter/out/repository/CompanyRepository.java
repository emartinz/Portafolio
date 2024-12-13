package com.test.t1.adapter.out.repository;
import org.springframework.stereotype.Repository;

import com.test.t1.domain.model.entity.Company;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> { 
    Optional<Company> findByCode(String code);
}
