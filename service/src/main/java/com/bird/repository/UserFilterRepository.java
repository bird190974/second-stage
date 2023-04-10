package com.bird.repository;

import com.bird.dto.UserFilter;
import com.bird.entity.User;

import java.util.List;

public interface UserFilterRepository {

    List<User> findByFilter(UserFilter filter);
}
