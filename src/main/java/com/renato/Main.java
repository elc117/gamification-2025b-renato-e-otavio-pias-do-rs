package com.renato;

/**
 * Classe principal da aplicação.
 * Ponto de entrada, apenas inicializa a aplicação.
 */
public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        App app = new App(port);
        app.start();
    }
}