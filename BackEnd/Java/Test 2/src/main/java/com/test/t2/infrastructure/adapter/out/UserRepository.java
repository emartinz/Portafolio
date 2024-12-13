package com.test.t2.infrastructure.adapter.out;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.test.t2.domain.model.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
