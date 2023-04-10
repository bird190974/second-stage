package com.bird.service;

import com.bird.dto.ClientReadDto;
import com.bird.mapper.ClientReadMapper;
import com.bird.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientReadMapper::map)
                .toList();
    }

    public Optional<ClientReadDto> findById(Integer id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::map);
    }
}
