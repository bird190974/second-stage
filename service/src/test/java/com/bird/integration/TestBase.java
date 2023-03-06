package com.bird.integration;

import com.bird.testUtils.TestDataCreate;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestBase {
    private static SessionFactory sessionFactory;
    protected Session session;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtils.buildSessionFactory();
        TestDataCreate.createData(sessionFactory);
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void sessionInit() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void setSessionRollbackAndClose() {
        session.getTransaction().rollback();
        session.close();
    }
}
