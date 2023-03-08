package com.wiiisdom.hr.wp.providers.factories;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.hk2.api.Factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerInjectableFactory implements Factory<EntityManager> {

    public static final String JDBC_PASSWORD = "javax.persistence.jdbc.password";
    public static final String JDBC_URL = "javax.persistence.jdbc.url";
    public static final String JDBC_USER = "javax.persistence.jdbc.user";
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String JDBC_DRIVER = "javax.persistence.jdbc.driver";
    public static final String JDBC_WINDOWS_AUTH = "javax.persistence.jdbc.windows.auth";
    private final EntityManagerFactory managerFactory;

    public EntityManagerInjectableFactory() {
        Map<String, String> sessionProperties = new HashMap<>();
        sessionProperties.put(JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
        sessionProperties.put(HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        sessionProperties.put(JDBC_URL, String.format("jdbc:mysql://%s:%s/%s?seUnicode=true"
                        + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                System.getenv("dbhost"), System.getenv().getOrDefault("dbport", "3306"),
                System.getenv("database")));
        sessionProperties.put(JDBC_USER, System.getenv("dbuser"));
        sessionProperties.put(JDBC_PASSWORD, System.getenv("dbpass"));
        sessionProperties.put("hibernate.connection.provider_class",
                "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
        sessionProperties.put("hibernate.ejb.entitymanager_factory_name", "360DB");
        sessionProperties.put("hibernate.connection.shutdown", "true");
        sessionProperties.put("hibernate.c3p0.idle_test_period", "300");
        sessionProperties.put("hibernate.c3p0.validate", "true");
        sessionProperties.put("hibernate.enable_lazy_load_no_trans", "true");
        sessionProperties.put("hibernate.jdbc.batch_size", "1000");
        this.managerFactory = Persistence
                .createEntityManagerFactory("360DB", sessionProperties);
        ;
    }

    @Override
    public EntityManager provide() {

        return managerFactory.createEntityManager();
    }

    @Override
    public void dispose(EntityManager entityManager) {
        entityManager.clear();
        entityManager.close();
    }
}
