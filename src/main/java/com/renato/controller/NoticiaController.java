package com.renato.controller;

import com.renato.model.Noticia;
import com.renato.service.NoticiaService;
import io.javalin.http.Context;
import java.util.List;

/**
 * Controller responsável por operações relacionadas a notícias.
 */
public class NoticiaController {
    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    /**
     * Lista todas as notícias.
     */
    public void listarTodas(Context ctx) {
        ctx.json(noticiaService.listarNoticias());
    }

    /**
     * Busca notícia por ID.
     */
    public void buscarPorId(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Noticia noticia = noticiaService.encontrarNoticia(id);

        if (noticia != null) {
            ctx.json(noticia);
        } else {
            ctx.status(404).result("Notícia não encontrada");
        }
    }

    /**
     * Lista notícias de uma categoria.
     */
    public void listarPorCategoria(Context ctx) {
        Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));
        List<Noticia> noticiasDaCategoria = noticiaService.obtemNoticiasDaCategoria(categoriaId);
        ctx.json(noticiasDaCategoria);
    }

    /**
     * Cria nova notícia.
     */
    public void criar(Context ctx) {
        Noticia novaNoticia = ctx.bodyAsClass(Noticia.class);
        Noticia noticiaSalva = noticiaService.salvarNoticia(novaNoticia);
        ctx.status(201).json(noticiaSalva);
    }

    /**
     * Atualiza notícia existente.
     */
    public void atualizar(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Noticia noticiaExistente = noticiaService.encontrarNoticia(id);

        if (noticiaExistente == null) {
            ctx.status(404).result("Notícia não encontrada");
            return;
        }

        Noticia noticiaAtualizada = ctx.bodyAsClass(Noticia.class);
        noticiaAtualizada.setId(id);
        noticiaService.atualizarNoticia(noticiaAtualizada);
        ctx.json(noticiaAtualizada);
    }

    /**
     * Deleta notícia.
     */
    public void deletar(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Noticia noticia = noticiaService.encontrarNoticia(id);

        if (noticia == null) {
            ctx.status(404).result("Notícia não encontrada");
            return;
        }

        noticiaService.deletarNoticia(noticia);
        ctx.status(204);
    }
}
