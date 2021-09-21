package com.shop.config;

import com.shop.model.Good;
import com.shop.model.Category;
import com.shop.model.Manufacturer;
import com.shop.model.GoodType;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class HibernateConfig {

    private Logger logger = LoggerFactory.getLogger(HibernateConfig.class);

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_NAME = "auto_parts_store";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pass";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    @Bean("sessionFactory")
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        initMappings(configuration);
        configuration.setProperties(createProps());

        logger.info("Building session factory.");
        return configuration.buildSessionFactory();
    }

    private void initMappings(org.hibernate.cfg.Configuration configuration) {
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(GoodType.class);
        configuration.addAnnotatedClass(Good.class);
        configuration.addAnnotatedClass(Manufacturer.class);
    }

    private Properties createProps() {
        Properties properties = new Properties();

        properties.setProperty(Environment.DRIVER, DRIVER_NAME);
        properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?createDatabaseIfNotExist=true&serverTimezone=UTC");
        properties.setProperty(Environment.USER, USERNAME);
        properties.setProperty(Environment.PASS, PASSWORD);
        properties.setProperty(Environment.DIALECT, DIALECT);
        properties.setProperty(Environment.HBM2DDL_AUTO, "update");

        return properties;
    }
}
