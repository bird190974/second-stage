package com.bird.dao;

import com.bird.dto.ClientFilter;
import com.bird.entity.Car;
import com.bird.entity.Client;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bird.entity.QClient.client;

@AllArgsConstructor
public class ClientFilterRepositoryImpl implements ClientFilterRepository {

    private final EntityManager entityManager;

    @Override
    public List<Client> findByFilter(ClientFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getPassportNo(), client.passportNo::eq)
                .add(filter.getDriverLicenceNo(), client.driverLicenceNo::eq)
                .add(filter.getDriverLicenceExpiration(), client.driverLicenceExpiration::eq)
                .add(filter.getCreditAmount(), client.creditAmount::eq)
                .add(filter.getClientRating(), client.clientRating::eq)
                .buildAnd();
        return new JPAQuery<Car>(entityManager)
                .select(client)
                .from(client)
                .where(predicate)
                .fetch();
    }
}
