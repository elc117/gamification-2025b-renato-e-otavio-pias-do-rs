package com.renato.controller;

import com.renato.model.Categoria;
import com.renato.model.ProgressoCategoria;
import com.renato.service.CategoriaService;
import com.renato.service.ProgressoCategoriaService;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsável por operações relacionadas a categorias.
 */
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final ProgressoCategoriaService progressoService;

    public CategoriaController(CategoriaService categoriaService, 
                              ProgressoCategoriaService progressoService) {
        this.categoriaService = categoriaService;
        this.progressoService = progressoService;
    }

    /**
     * Lista todas as categorias.
     */
    public void listarTodas(Context ctx) {
        ctx.json(categoriaService.listarCategorias());
    }

    /**
     * Busca categoria por ID.
     */
    public void buscarPorId(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Categoria categoria = categoriaService.encontrarCategoria(id);
        
        if (categoria != null) {
            ctx.json(categoria);
        } else {
            ctx.status(404).result("Categoria não encontrada");
        }
    }

    /**
     * Cria nova categoria.
     */
    public void criar(Context ctx) {
        Categoria novaCategoria = ctx.bodyAsClass(Categoria.class);
        Categoria categoriaSalva = categoriaService.salvarCategoria(novaCategoria);
        ctx.status(201).json(categoriaSalva);
    }

    /**
     * Atualiza categoria existente.
     */
    public void atualizar(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Categoria categoria = ctx.bodyAsClass(Categoria.class);
        categoria.setId(id);
        categoriaService.atualizarCategoria(categoria);
        ctx.json(categoria);
    }

    /**
     * Deleta categoria.
     */
    public void deletar(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        categoriaService.deletarCategoria(id);
        ctx.status(204);
    }

    /**
     * Busca progresso do usuário logado em uma categoria.
     */
    public void buscarMeuProgresso(Context ctx) {
        Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
        if (usuarioId == null) return;
        
        Long categoriaId = Long.parseLong(ctx.pathParam("id"));
        ProgressoCategoria progresso = progressoService.obtemProgressoCategoria(usuarioId, categoriaId);
        Map<String, Object> estatisticas = progressoService.obtemEstatisticasCategoria(usuarioId, categoriaId);

        if (progresso != null) {
            // Montar resposta completa
            Map<String, Object> respostaCompleta = new HashMap<>();
            respostaCompleta.put("id", progresso.getId());
            respostaCompleta.put("usuarioId", progresso.getUsuarioId());
            respostaCompleta.put("categoriaId", progresso.getCategoriaId());
            respostaCompleta.put("nivelAtual", progresso.getNivelAtual());
            respostaCompleta.put("pontosMaestria", progresso.getPontosMaestria());
            respostaCompleta.put("pecasDesbloqueadas", progresso.getPecasDesbloqueadas());
            respostaCompleta.put("estatisticas", estatisticas);

            ctx.json(respostaCompleta);
        } else {
            // Progresso vazio
            Map<String, Object> respostaVazia = new HashMap<>();
            respostaVazia.putAll(estatisticas);
            respostaVazia.put("pecasDesbloqueadas", new ArrayList<>());
            respostaVazia.put("nivelAtual", 0);
            respostaVazia.put("pontosMaestria", 0);
            ctx.json(respostaVazia);
        }
    }
}
