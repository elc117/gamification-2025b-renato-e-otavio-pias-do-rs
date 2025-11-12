package com.renato.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pecas_recompensa")
public class PecaRecompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;

    @Column(name = "nivel_requerido", nullable = false)
    private int nivelRequerido;

    public PecaRecompensa() {
    }

    public PecaRecompensa(Long categoriaId, int nivelRequerido) {
        this.categoriaId = categoriaId;
        this.nivelRequerido = nivelRequerido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getNivelRequerido() {
        return nivelRequerido;
    }

    public void setNivelRequerido(int nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }
}
