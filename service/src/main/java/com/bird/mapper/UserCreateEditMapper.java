package com.bird.mapper;

import com.bird.dto.UserCreateEditDto;
import com.bird.entity.Client;
import com.bird.entity.User;
import com.bird.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final ClientRepository clientRepository;

    @Override
    public User map(UserCreateEditDto object) {
        var user = new User();
        copy(object, user);
        return user;
    }

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setFirstName(object.getFirstName());
        user.setLastName(object.getLastName());
        user.setEmail(object.getEmail());
        user.setPassword(object.getPassword());
        user.setRole(object.getRole());
        user.setClient(getClient(object.getClientId()));
    }

    private Client getClient(Integer clientId) {
        return Optional.ofNullable(clientId)
                .flatMap(clientRepository::findById)
                .orElse(null);
    }
}
