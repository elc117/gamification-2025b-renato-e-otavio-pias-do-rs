package com.renato;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.renato.controller.Routes;

/**
 * Classe responsável por configurar e inicializar a aplicação Javalin.
 */
public class App {
    private final Javalin javalin;
    private final int port;

    /**
     * Construtor que configura a aplicação.
     * 
     * @param port Porta em que o servidor vai rodar
     */
    public App(int port) {
        this.port = port;
        this.javalin = configurarJavalin();
        Routes.configure(this.javalin);
    }

    /**
     * Configura o Javalin com JSON mapper e arquivos estáticos.
     */
    private Javalin configurarJavalin() {
        // Configurar ObjectMapper para suportar LocalDateTime
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Criar e configurar aplicação Javalin
        return Javalin.create(config -> {
            // Configurar JSON mapper
            config.jsonMapper(new JavalinJackson(objectMapper, true));
            
            // Servir arquivos estáticos (frontend)
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "frontend";
                staticFiles.location = io.javalin.http.staticfiles.Location.EXTERNAL;
            });
        });
    }

    /**
     * Inicia o servidor.
     */
    public void start() {
        javalin.start(port);
        System.out.println("Servidor rodando na porta " + port);
    }

    /**
     * Para o servidor.
     */
    public void stop() {
        javalin.stop();
    }

    /**
     * Obtém a instância do Javalin (ela é útil para testes).
     */
    public Javalin getJavalin() {
        return javalin;
    }
}
