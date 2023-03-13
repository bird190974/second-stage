package com.bird.dao;

import com.bird.dto.ClientFilter;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientRepositoryIT extends TestBase {

    @Test
    void save() {

        var client = TestUtil.getClient(TestUtil.getUser());
        var clientRepository = new ClientRepository(session);

        var actualClient = clientRepository.save(client);

        assertThat(actualClient).isNotNull();
    }

    @Test
    void update() {
        var clientRepository = new ClientRepository(session);

        var expectedClient = clientRepository.findById(1).get();
        expectedClient.setClientRating(0);
        clientRepository.update(expectedClient);

        var actualClient = clientRepository.findById(1);

        assertThat(actualClient).isNotNull();
        assertThat(actualClient.get().getClientRating()).isEqualTo(0);
    }

    @Test
    void deleteExistingClient() {
        var clientRepository = new ClientRepository(session);

        clientRepository.delete(1);

        assertThat(clientRepository.findById(1)).isEmpty();
    }
    @Test
    void deleteNotExistingClient() {
        var clientRepository = new ClientRepository(session);

        assertThrows(IllegalArgumentException.class, () -> clientRepository.delete(60000));
    }
    @Test
    void findById() {
        var clientRepository = new ClientRepository(session);

        var actualClient = clientRepository.findById(1);

        assertThat(actualClient).isPresent();
        assertThat(actualClient.get().getPassportNo()).isEqualTo("123I456");
    }

    @Test
    void findAll() {
        var clientRepository = new ClientRepository(session);

        var actualClient = clientRepository.findAll();

        assertNotNull(actualClient);
        assertThat(actualClient).hasSize(3);
    }

    @Test
    void findByFilterWithAllParams() {
        var clientRepository = new ClientRepository(session);

        var clientFilter = ClientFilter.builder()
                .passportNo("123I456")
                .driverLicenceNo("I123456")
                .driverLicenceExpiration(LocalDate.of(2023, 12, 31))
                .creditAmount(BigDecimal.valueOf(120.45))
                .clientRating(5)
                .build();
        var clients = clientRepository.findByFilter(clientFilter);

        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getPassportNo()).isEqualTo("123I456");
    }

    @Test
    void findByFilterWithOneParams() {
        var clientRepository = new ClientRepository(session);

        var clientFilter = ClientFilter.builder()
                .clientRating(5)
                .build();
        var clients = clientRepository.findByFilter(clientFilter);

        assertThat(clients).hasSize(2);
        assertThat(clients.get(0).getPassportNo()).isEqualTo("123I456");
        assertThat(clients.get(1).getPassportNo()).isEqualTo("123P456");
    }

    @Test
    void findByFilterWithNoParams() {
        var clientRepository = new ClientRepository(session);

        var clientFilter = ClientFilter.builder()
                .build();
        var clients = clientRepository.findByFilter(clientFilter);

        assertThat(clients).hasSize(clientRepository.findAll().size());

    }


}