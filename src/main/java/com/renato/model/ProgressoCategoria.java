package com.renato.model;

import java.util.List;

public class ProgressoCategoria {
    private Long id;
    private Long usuarioId;
    private Long categoriaId;
    private Usuario usuario;
    private Categoria categoria;
    private int nivelAtual;
    private int pontosMaestria;
    private List<Integer> pecasDesbloqueadas;

    public ProgressoCategoria() {
    }

    public ProgressoCategoria(Long id, Long usuarioId, Long categoriaId, int nivelAtual, int pontosMaestria, List<Integer> pecasDesbloqueadas) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
        this.nivelAtual = nivelAtual;
        this.pontosMaestria = pontosMaestria;
        this.pecasDesbloqueadas = pecasDesbloqueadas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(int nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public int getPontosMaestria() {
        return pontosMaestria;
    }

    public void setPontosMaestria(int pontosMaestria) {
        this.pontosMaestria = pontosMaestria;
    }

    public List<Integer> getPecasDesbloqueadas() {
        return pecasDesbloqueadas;
    }

    public void setPecasDesbloqueadas(List<Integer> pecasDesbloqueadas) {
        this.pecasDesbloqueadas = pecasDesbloqueadas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}


