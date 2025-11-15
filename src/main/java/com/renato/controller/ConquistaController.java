package com.renato.controller;

import com.renato.model.Conquista;
import com.renato.model.ConquistaUsuario;
import com.renato.service.ConquistaVerificadorService;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Lista conquistas do usuário logado com dados completos.
     */
    public void listarMinhasConquistas(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        List<ConquistaUsuario> conquistasDoUsuario = conquistaService.obtemConquistasUsuario(usuarioId);

        // Criar lista com dados completos
        List<Map<String, Object>> conquistasCompletas = new ArrayList<>();
        for (ConquistaUsuario conqU : conquistasDoUsuario) {
            Conquista conquista = conquistaService.obtemConquista(conqU.getConquistaId());
            if (conquista != null) {
                Map<String, Object> conquistaMap = new HashMap<>();
                conquistaMap.put("id", conqU.getId());
                conquistaMap.put("usuarioId", conqU.getUsuarioId());
                conquistaMap.put("conquistaId", conqU.getConquistaId());
                conquistaMap.put("dataDesbloqueio", conqU.getDataDesbloqueio() != null 
                    ? conqU.getDataDesbloqueio().toString() 
                    : null);
                conquistaMap.put("nome", conquista.getNome());
                conquistaMap.put("descricao", conquista.getDescricao());
                conquistaMap.put("desbloqueada", true);
                conquistasCompletas.add(conquistaMap);
            }
        }

        ctx.json(conquistasCompletas);
    }
}
