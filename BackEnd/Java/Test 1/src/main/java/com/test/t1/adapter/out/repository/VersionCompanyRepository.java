package com.test.t1.adapter.out.repository;
import org.springframework.stereotype.Repository;

import com.test.t1.domain.model.entity.VersionCompany;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VersionCompanyRepository extends JpaRepository<VersionCompany, Long> { 
    List<VersionCompany> findByCompanyId(int companyId);
}
