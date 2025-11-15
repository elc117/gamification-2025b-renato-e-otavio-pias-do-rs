package com.renato.controller;

import com.renato.controller.dto.RespostaRequest;
import com.renato.model.Noticia;
import com.renato.model.Resposta;
import com.renato.service.JogoOrquestradorService;
import com.renato.service.RespostaService;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;

/**
 * Controller responsável pela lógica principal do jogo.
 */
public class JogoController {
    private final JogoOrquestradorService jogoService;
    private final RespostaService respostaService;

    public JogoController(JogoOrquestradorService jogoService, RespostaService respostaService) {
        this.jogoService = jogoService;
        this.respostaService = respostaService;
    }

    /**
     * Obtém notícia aleatória de uma categoria para o usuário logado.
     */
    public void obterNoticiaAleatoria(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));
        Noticia noticia = jogoService.obterNoticiaAleatoriaDaCategoria(usuarioId, categoriaId);

        if (noticia == null) {
            ctx.status(404).result("Todas as notícias desta categoria já foram respondidas");
            return;
        }

        ctx.json(noticia);
    }

    /**
     * Processa resposta do usuário para uma notícia.
     */
    public void responderNoticia(Context ctx) {
        try {
            Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
            if (usuarioId == null) return;
            
            Long noticiaId = Long.parseLong(ctx.pathParam("id"));
            RespostaRequest respostaRequest = ctx.bodyAsClass(RespostaRequest.class);
            boolean respostaUsuario = respostaRequest.isResposta();

            Map<String, Object> resultado = jogoService.processarResposta(usuarioId, noticiaId, respostaUsuario);

            ctx.status(201).json(resultado);
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (IllegalStateException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao processar resposta: " + e.getMessage());
        }
    }

    /**
     * Lista respostas do usuário logado.
     */
    public void listarMinhasRespostas(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        List<Resposta> respostasUsuario = respostaService.obtemRespostasUsuario(usuarioId);
        ctx.json(respostasUsuario);
    }
}
