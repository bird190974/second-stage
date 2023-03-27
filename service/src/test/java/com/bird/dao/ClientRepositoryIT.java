package com.bird.dao;

import com.bird.dto.ClientFilter;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class ClientRepositoryIT extends TestBase {

    private final ClientRepository clientRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        var client = TestUtil.getClient(TestUtil.getUser());

        var actualClient = clientRepository.save(client);

        assertThat(actualClient.getId()).isNotNull();
    }

    @Test
    void update() {
        var expectedClient = clientRepository.findById(1).get();
        expectedClient.setClientRating(0);
        clientRepository.update(expectedClient);
        entityManager.clear();

        var maybeClient = clientRepository.findById(1);

        assertThat(maybeClient).isNotNull();
        assertThat(maybeClient.get().getClientRating()).isEqualTo(0);
    }

    @Test
    void delete() {
        var client = TestUtil.getClient(TestUtil.getUser());
        clientRepository.save(client);
        clientRepository.delete(client);
        entityManager.clear();

        var maybeClient = clientRepository.findById(client.getId());

        assertThat(maybeClient).isEmpty();
    }

    @Test
    void findById() {

        var maybeClient = clientRepository.findById(1);

        assertThat(maybeClient).isPresent();
        assertThat(maybeClient.get().getPassportNo()).isEqualTo("123I456");
    }

    @Test
    void findAll() {
        var actualClient = clientRepository.findAll();
        entityManager.clear();

        assertNotNull(actualClient);
        assertThat(actualClient).hasSize(3);
    }

    @Test
    void findByFilterWithAllParams() {
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
        var clientFilter = ClientFilter.builder()
                .build();

        var clients = clientRepository.findByFilter(clientFilter);

        assertThat(clients).hasSize(clientRepository.findAll().size());
    }
}