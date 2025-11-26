package com.renato.controller;

import com.renato.controller.dto.ConquistaComStatus;
import com.renato.model.Conquista;
import com.renato.model.ConquistaUsuario;
import com.renato.service.ConquistaVerificadorService;
import io.javalin.http.Context;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável por operações relacionadas a conquistas.
 */
public class ConquistaController {
    private final ConquistaVerificadorService conquistaService;

    public ConquistaController(ConquistaVerificadorService conquistaService) {
        this.conquistaService = conquistaService;
    }

    /**
     * Lista todas as conquistas disponíveis.
     */
    public void listarTodas(Context ctx) {
        ctx.json(conquistaService.listarConquistas());
    }

    /**
     * Lista conquistas do usuário logado usando herança OO.
     * Retorna ConquistaComStatus que ESTENDE Conquista.
     */
    public void listarMinhasConquistas(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        List<ConquistaUsuario> conquistasDoUsuario = conquistaService.obtemConquistasUsuario(usuarioId);

        List<ConquistaComStatus> conquistasCompletas = conquistasDoUsuario.stream()
            .map(conqU -> {
                Conquista conquista = conquistaService.obtemConquista(conqU.getConquistaId());
                return conquista != null 
                    ? new ConquistaComStatus(conquista, conqU.getDataDesbloqueio(), true)
                    : null;
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());

        ctx.json(conquistasCompletas);
    }
}
