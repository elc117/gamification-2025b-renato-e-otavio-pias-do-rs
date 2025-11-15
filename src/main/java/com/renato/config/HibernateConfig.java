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
                
                // configurações com as variáveis de ambiente (para o Render)
                String dbUrl = System.getenv("DATABASE_URL");
                if (dbUrl != null && !dbUrl.isEmpty()) {
                    configuration.setProperty("hibernate.connection.url", dbUrl);
                    System.out.println("[HibernateConfig] Usando DATABASE_URL do ambiente");
                }
                
                String dbUser = System.getenv("DB_USER");
                if (dbUser != null && !dbUser.isEmpty()) {
                    configuration.setProperty("hibernate.connection.username", dbUser);
                }
                
                String dbPassword = System.getenv("DB_PASSWORD");
                if (dbPassword != null && !dbPassword.isEmpty()) {
                    configuration.setProperty("hibernate.connection.password", dbPassword);
                }
                
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