package com.bird.mapper;

import com.bird.dto.ClientReadDto;
import com.bird.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientReadMapper implements Mapper<Client, ClientReadDto> {

    @Override
    public ClientReadDto map(Client object) {
        return new ClientReadDto(
                object.getId(),
                object.getPassportNo(),
                object.getDriverLicenceNo(),
                object.getDriverLicenceExpiration(),
                object.getCreditAmount(),
                object.getClientRating()
        );
    }
}
