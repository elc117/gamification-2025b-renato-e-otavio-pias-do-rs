package com.renato.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                // Log detalhado e rethrow para não mascarar erro
                System.err.println("[HibernateConfig] Falha ao inicializar SessionFactory. Verifique hibernate.cfg.xml, driver e credenciais.");
                e.printStackTrace();
                throw new IllegalStateException("Não foi possível inicializar o Hibernate SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}