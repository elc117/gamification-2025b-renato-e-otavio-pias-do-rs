package com.renato.controller;

import com.renato.service.*;
import io.javalin.Javalin;

/**
 * Classe responsável por registrar todas as rotas da aplicação.
 * Centraliza o mapeamento de URLs para controllers.
 */
public class Routes {
    
    /**
     * Registra todas as rotas da aplicação.
     * 
     * @param app Instância do Javalin
     */
    public static void configure(Javalin app) {
        // Inicializar services
        UsuarioService usuarioService = new UsuarioService();
        CategoriaService categoriaService = new CategoriaService();
        NoticiaService noticiaService = new NoticiaService();
        RespostaService respostaService = new RespostaService();
        ConquistaVerificadorService conquistaService = new ConquistaVerificadorService();
        ProgressoCategoriaService progressoService = new ProgressoCategoriaService();
        JogoOrquestradorService jogoService = new JogoOrquestradorService();

        // Inicializar controllers
        AuthController authController = new AuthController(usuarioService);
        UsuarioController usuarioController = new UsuarioController(usuarioService, progressoService, conquistaService);
        CategoriaController categoriaController = new CategoriaController(categoriaService, progressoService);
        NoticiaController noticiaController = new NoticiaController(noticiaService);
        JogoController jogoController = new JogoController(jogoService, respostaService);
        ConquistaController conquistaController = new ConquistaController(conquistaService);
        RecompensaController recompensaController = new RecompensaController();

        // ========== ROTAS DE AUTENTICAÇÃO ==========
        app.post("/login", authController::login);
        app.post("/logout", authController::logout);
        app.get("/session/usuario", authController::getUsuarioSessao);

        // ========== ROTAS DE USUÁRIOS ==========
        app.get("/usuarios", usuarioController::listarTodos);
        app.get("/usuarios/{id}", usuarioController::buscarPorId);
        app.post("/usuarios", usuarioController::criar);
        app.put("/usuarios/{id}", usuarioController::atualizar);
        app.delete("/usuarios/{id}", usuarioController::deletar);
        app.get("/meu-perfil", usuarioController::buscarMeuPerfil);

        // ========== ROTAS DE CATEGORIAS ==========
        app.get("/categorias", categoriaController::listarTodas);
        app.get("/categorias/{id}", categoriaController::buscarPorId);
        app.post("/categorias", categoriaController::criar);
        app.put("/categorias/{id}", categoriaController::atualizar);
        app.delete("/categorias/{id}", categoriaController::deletar);
        app.get("/categorias/{id}/meu-progresso", categoriaController::buscarMeuProgresso);

        // ========== ROTAS DE NOTÍCIAS ==========
        app.get("/noticias", noticiaController::listarTodas);
        app.get("/noticias/{id}", noticiaController::buscarPorId);
        app.get("/noticias/categoria/{categoriaId}", noticiaController::listarPorCategoria);
        app.post("/noticias", noticiaController::criar);
        app.put("/noticias/{id}", noticiaController::atualizar);
        app.delete("/noticias/{id}", noticiaController::deletar);

        // ========== ROTAS DO JOGO ==========
        app.get("/noticias/random/categoria/{categoriaId}", jogoController::obterNoticiaAleatoria);
        app.post("/noticias/{id}/responder", jogoController::responderNoticia);
        app.get("/minhas-respostas", jogoController::listarMinhasRespostas);

        // ========== ROTAS DE CONQUISTAS ==========
        app.get("/conquistas", conquistaController::listarTodas);
        app.get("/minhas-conquistas", conquistaController::listarMinhasConquistas);

        // ========== ROTAS DE RECOMPENSAS ==========
        app.get("/minhas-recompensas", recompensaController::listarMinhasRecompensas);
        app.get("/categorias/{categoriaId}/progresso-imagem", recompensaController::obterProgressoImagem);
    }
}
