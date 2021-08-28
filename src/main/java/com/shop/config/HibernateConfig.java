package com.shop.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class HibernateConfig {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_NAME = "auto_parts_store";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pass";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    @Bean("sessionFactory")
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperties(createProps());

        return configuration.buildSessionFactory();
    }

    private Properties createProps() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.connection.driver_class", DRIVER_NAME);
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?createDatabaseIfNotExist=true&serverTimezone=UTC");
        properties.setProperty("hibernate.connection.username", USERNAME);
        properties.setProperty("hibernate.connection.password", PASSWORD);
        properties.setProperty("hibernate.dialect", DIALECT);

        return properties;
    }
}
