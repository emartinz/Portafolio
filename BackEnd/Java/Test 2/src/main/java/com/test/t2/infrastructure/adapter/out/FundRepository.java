package com.test.t2.infrastructure.adapter.out;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.test.t2.domain.model.entities.Fund;

@Repository
public interface FundRepository extends MongoRepository<Fund, String> {
    boolean existsByName(String name);
}
