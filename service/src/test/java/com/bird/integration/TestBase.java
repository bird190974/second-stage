package com.bird.integration;

import com.bird.config.CarBookConfiguration;
import com.bird.testUtils.TestDataCreate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

public abstract class TestBase {

    protected static AnnotationConfigApplicationContext context;
    protected static Session session;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext((CarBookConfiguration.class));
        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        session = (Session) context.getBean(EntityManager.class);
        TestDataCreate.createData(sessionFactory);
    }

    @AfterAll
    static void close() {
        context.close();
    }

    @BeforeEach
    void getSession() {
        session.beginTransaction();
    }

    @AfterEach
    void setSessionRollback() {
        session.getTransaction().rollback();
    }
}
