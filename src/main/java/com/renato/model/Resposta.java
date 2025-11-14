package com.renato.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "respostas")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "noticia_id", nullable = false)
    private Long noticiaId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticia_id", insertable = false, updatable = false)
    private Noticia noticia;

    @Column(name = "resposta_usuario", nullable = false)
    private boolean respostaUsuario;

    @Column(name = "esta_correta", nullable = false)
    private boolean estaCorreta;

    @Column(name = "pontos_ganhos", nullable = false)
    private int pontosGanhos;

    @Column(name = "tentativas", nullable = false)
    private int tentativas = 1;

    // Construtor vazio necessário para o Hibernate
    public Resposta() {
    }

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

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }

    public void incrementarTentativas() {
        this.tentativas++;
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
