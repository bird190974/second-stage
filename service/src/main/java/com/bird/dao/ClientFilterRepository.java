package com.bird.dao;

import com.bird.dto.ClientFilter;
import com.bird.entity.Client;

import java.util.List;

public interface ClientFilterRepository {

    List<Client> findByFilter(ClientFilter clientFilter);
}
