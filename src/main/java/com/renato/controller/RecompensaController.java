package com.renato.controller;

import com.renato.controller.dto.RecompensaCategoriaDTO;
import com.renato.controller.dto.ProgressoImagemDTO;
import com.renato.service.RecompensaService;
import io.javalin.http.Context;
import java.util.List;

/**
 * Controller responsável por operações relacionadas a recompensas.
 */
public class RecompensaController {
    private final RecompensaService recompensaService;

    public RecompensaController() {
        this.recompensaService = new RecompensaService();
    }

    /**
     * Lista todas as recompensas do usuário logado.
     */
    public void listarMinhasRecompensas(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        List<RecompensaCategoriaDTO> recompensas = recompensaService.obterTodasRecompensasUsuario(usuarioId);
        ctx.json(recompensas);
    }

    /**
     * Obtém progresso visual (imagem) de uma categoria para o usuário logado.
     */
    public void obterProgressoImagem(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));
        ProgressoImagemDTO progressoImagem = recompensaService.calcularProgressoImagem(usuarioId, categoriaId);
        ctx.json(progressoImagem);
    }
}
