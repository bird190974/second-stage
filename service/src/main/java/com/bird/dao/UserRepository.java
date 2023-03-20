package com.bird.dao;

import com.bird.dto.UserFilter;
import com.bird.entity.Car;
import com.bird.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bird.entity.QUser.user;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findByFilter(UserFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .add(filter.getEmail(), user.email::eq)
                .add(filter.getPassword(), user.password::eq)
                .add(filter.getRole(), user.role::eq)
                .buildAnd();
        return new JPAQuery<Car>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
