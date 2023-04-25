package com.bird.repository;

import com.bird.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserFilterRepository, QuerydslPredicateExecutor<User> {
    Optional<User> findByEmail(String email);
}
