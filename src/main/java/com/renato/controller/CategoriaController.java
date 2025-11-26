package com.renato.controller;

import com.renato.controller.dto.RespostaCategoriaDTO;
import com.renato.controller.dto.EstatisticasCategoriaDTO;
import com.renato.model.Categoria;
import com.renato.model.ProgressoCategoria;
import com.renato.service.CategoriaService;
import com.renato.service.ProgressoCategoriaService;
import io.javalin.http.Context;

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
        EstatisticasCategoriaDTO estatisticas = progressoService.obtemEstatisticasCategoria(usuarioId, categoriaId);
        Categoria categoria = categoriaService.encontrarCategoria(categoriaId);

        RespostaCategoriaDTO resposta = new RespostaCategoriaDTO(
            categoria,
            progresso,
            estatisticas
        );

        ctx.json(resposta);
    }
}
