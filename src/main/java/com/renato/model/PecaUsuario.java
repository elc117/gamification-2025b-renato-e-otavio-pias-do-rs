package com.renato.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pecas_usuario")
public class PecaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "peca_id", nullable = false)
    private Long pecaId;

    @Column(name = "data_desbloqueio", nullable = false)
    private LocalDateTime dataDesbloqueio;

    public PecaUsuario() {
    }

    public PecaUsuario(Long usuarioId, Long pecaId, LocalDateTime dataDesbloqueio) {
        this.usuarioId = usuarioId;
        this.pecaId = pecaId;
        this.dataDesbloqueio = dataDesbloqueio;
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

    public Long getPecaId() {
        return pecaId;
    }

    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }

    public LocalDateTime getDataDesbloqueio() {
        return dataDesbloqueio;
    }

    public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }
}
