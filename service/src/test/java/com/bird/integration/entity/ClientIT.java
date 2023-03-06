package com.bird.integration.entity;

import com.bird.entity.Client;
import com.bird.entity.User;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientIT extends TestBase {
    @Test
    void saveClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);

        session.save(user);
        session.save(client);
        session.clear();

        assertThat(client).isNotNull();
    }

    @Test
    void getClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);

        session.save(user);
        session.save(client);
        session.clear();
        Client actualClient = session.get(Client.class, client.getId());
        assertThat(client.getPassportNo()).isEqualTo(actualClient.getPassportNo());
    }

    @Test
    void updateClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);
        String updatedPassportId = "56N789";

        session.save(user);
        session.save(client);
        session.flush();
        client.setPassportNo(updatedPassportId);
        session.update(client);
        session.flush();
        session.clear();

        Client updatedClient = session.get(Client.class, client.getId());

        assertThat(updatedClient.getPassportNo()).isEqualTo(updatedPassportId);

    }


    @Test
    void deleteClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);
        session.save(user);
        session.save(client);
        session.flush();
        session.delete(client);
        session.flush();

        Client deletedClient = session.get(Client.class, client.getId());

        assertThat(deletedClient).isNull();
    }
}
