package com.bird.mapper;

import com.bird.dto.UserReadDto;
import com.bird.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final ClientReadMapper clientReadMapper;

    @Override
    public UserReadDto map(User object) {
        var client = Optional.ofNullable(object.getClient())
                .map(clientReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getPassword(),
                object.getRole(),
                client
        );
    }
}
