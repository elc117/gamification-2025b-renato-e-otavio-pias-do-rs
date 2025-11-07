package com.renato.model;

public class Resposta {
    private Long id;
    private Long usuarioId;
    private Long noticiaId;
    private Usuario usuario;
    private Noticia noticia;
    private boolean respostaUsuario;
    private boolean estaCorreta;
    private int pontosGanhos;

    public Resposta(Long id) {
        this.id = id;
    }

    public Resposta(Long id, Long usuarioId, Long noticiaId, boolean respostaUsuario, boolean estaCorreta, int pontosGanhos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.noticiaId = noticiaId;
        this.respostaUsuario = respostaUsuario;
        this.estaCorreta = estaCorreta;
        this.pontosGanhos = pontosGanhos;
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

    public Long getNoticiaId() {
        return noticiaId;
    }

    public void setNoticiaId(Long noticiaId) {
        this.noticiaId = noticiaId;
    }

    public boolean isRespostaUsuario() {
        return respostaUsuario;
    }

    public void setRespostaUsuario(boolean respostaUsuario) {
        this.respostaUsuario = respostaUsuario;
    }

    public boolean isEstaCorreta() {
        return estaCorreta;
    }

    public void setEstaCorreta(boolean estaCorreta) {
        this.estaCorreta = estaCorreta;
    }

    public int getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(int pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }
}
