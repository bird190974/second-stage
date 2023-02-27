package com.bird.integration.entity;

import com.bird.entity.Client;
import com.bird.entity.User;
import com.bird.testUtils.TestUtil;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {
    @Test
    void saveAndGetClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            session.save(client);
            session.flush();
            session.evict(user);
            var result = session
                    .createQuery("select c from Client c where c.passportId = :passportId", Client.class)
                    .setParameter("passportId", "12N34")
                    .list();

            assertThat(result.get(0).getPassportId()).isEqualTo(client.getPassportId());

            session.getTransaction().commit();
        }
    }

    @Test
    void updateClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);
        String updatedPassportId = "56N789";

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            session.save(client);
            client.setPassportId(updatedPassportId);
            session.update(client);
            session.flush();
            session.evict(user);

            Client updatedClient = session.get(Client.class, client.getId());

            assertThat(updatedClient.getPassportId()).isEqualTo(updatedPassportId);

            session.getTransaction().commit();
        }
    }


    @Test
    void deleteClient() {
        User user = TestUtil.getUser();
        Client client = TestUtil.getClient(user);
        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            session.save(client);
            session.flush();
            session.delete(client);
            session.flush();

            Client deletedClient = session.get(Client.class, client.getId());

            assertThat(deletedClient).isNull();

            session.getTransaction().commit();
        }
    }

}
