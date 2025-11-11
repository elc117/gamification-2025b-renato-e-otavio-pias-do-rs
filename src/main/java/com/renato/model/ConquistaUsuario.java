package com.renato.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conquistas_usuario")
public class ConquistaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "conquista_id", nullable = false)
    private Long conquistaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conquista_id", insertable = false, updatable = false)
    private Conquista conquista;

    @Column(name = "data_desbloqueio", nullable = false)
    private LocalDateTime dataDesbloqueio;

    @Column(nullable = false)
    private boolean visualizada;

    public ConquistaUsuario() {
    }

    public ConquistaUsuario(Long id, Long usuarioId, Long conquistaId, LocalDateTime dataDesbloqueio, boolean visualizada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.conquistaId = conquistaId;
        this.dataDesbloqueio = dataDesbloqueio;
        this.visualizada = visualizada;
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

    public Long getConquistaId() {
        return conquistaId;
    }

    public void setConquistaId(Long conquistaId) {
        this.conquistaId = conquistaId;
    }

    public LocalDateTime getDataDesbloqueio() {
        return dataDesbloqueio;
    }

    public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }

    public boolean isVisualizada() {
        return visualizada;
    }

    public void setVisualizada(boolean visualizada) {
        this.visualizada = visualizada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Conquista getConquista() {
        return conquista;
    }

    public void setConquista(Conquista conquista) {
        this.conquista = conquista;
    }
}

