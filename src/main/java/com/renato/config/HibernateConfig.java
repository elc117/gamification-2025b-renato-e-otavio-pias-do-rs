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

                // --- Leitura das VARS de ambiente ---
                String dbUrl = System.getenv("DATABASE_URL");
                String dbUser = System.getenv("DB_USER");
                String dbPassword = System.getenv("DB_PASSWORD");
                
                // --- Detectar ambiente DevContainer/Codespaces ---
                String remoteContainers = System.getenv("REMOTE_CONTAINERS");
                String codespaces = System.getenv("CODESPACES");
                boolean isDevContainer = "true".equals(remoteContainers) || "true".equals(codespaces);
                
                if (isDevContainer) {
                    System.out.println("[HibernateConfig] Ambiente DevContainer/Codespaces detectado!");
                    System.out.println("[HibernateConfig] Usando configurações do DevContainer");
                    
                    // Configurações para DevContainer (docker-compose)
                    configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/fact_or_fake");
                    configuration.setProperty("hibernate.connection.username", "postgres");
                    configuration.setProperty("hibernate.connection.password", "postgres");
                    configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                }

                // --- Tratamento especial para Render ---
                else if (dbUrl != null && !dbUrl.isEmpty()) {
                    System.out.println("[HibernateConfig] DATABASE_URL detectada: " + dbUrl);

                    /*
                     * FORMATO DA RENDER:
                     * postgresql://USUARIO:SENHA@HOST:PORTA/BANCO
                     *
                     * Precisamos converter para:
                     * jdbc:postgresql://HOST:PORTA/BANCO
                     * e extrair USUARIO e SENHA separadamente
                     */

                    try {
                        // Remove prefixo "postgresql://"
                        String cleanUrl = dbUrl.replace("postgresql://", "");

                        // Divide em "usuario:senha" e "host:porta/banco"
                        String[] parts = cleanUrl.split("@");
                        String cred = parts[0];       // usuario:senha
                        String hostPart = parts[1];   // host:porta/banco

                        // Divide credenciais
                        String[] credParts = cred.split(":");
                        String user = credParts[0];
                        String password = credParts[1];

                        // Monta URL JDBC correta
                        String jdbcUrl = "jdbc:postgresql://" + hostPart;

                        configuration.setProperty("hibernate.connection.url", jdbcUrl);
                        configuration.setProperty("hibernate.connection.username", user);
                        configuration.setProperty("hibernate.connection.password", password);

                        System.out.println("[HibernateConfig] URL JDBC convertida: " + jdbcUrl);
                        System.out.println("[HibernateConfig] Usuário detectado: " + user);

                    } catch (Exception ex) {
                        System.err.println("[HibernateConfig] ERRO ao converter DATABASE_URL do Render!");
                        ex.printStackTrace();
                    }
                }

                // Caso DB_USER e DB_PASSWORD também estejam setados, eles sobrescrevem
                if (dbUser != null && !dbUser.isEmpty()) {
                    configuration.setProperty("hibernate.connection.username", dbUser);
                }

                if (dbPassword != null && !dbPassword.isEmpty()) {
                    configuration.setProperty("hibernate.connection.password", dbPassword);
                }

                // --- Criação final da SessionFactory ---
                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                System.err.println("[HibernateConfig] Falha ao inicializar SessionFactory.");
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
