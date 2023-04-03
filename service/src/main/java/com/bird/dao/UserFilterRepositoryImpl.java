package com.bird.dao;

import com.bird.dto.UserFilter;
import com.bird.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bird.entity.QUser.user;

@AllArgsConstructor
public class UserFilterRepositoryImpl implements UserFilterRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findByFilter(UserFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .add(filter.getEmail(), user.email::eq)
                .add(filter.getRole(), user.role::eq)
                .buildAnd();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
