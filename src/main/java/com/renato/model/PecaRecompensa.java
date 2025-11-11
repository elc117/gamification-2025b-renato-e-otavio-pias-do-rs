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

    @Column(name = "imagem_path", length = 255)
    private String imagemPath; // caminho da peça visual

    @Column(name = "posicao_x", nullable = false)
    private int posicaoX; // posição no quebra-cabeça

    @Column(name = "posicao_y", nullable = false)
    private int posicaoY;

    public PecaRecompensa() {
    }

    public PecaRecompensa(Long categoriaId, int nivelRequerido, String imagemPath, int posicaoX, int posicaoY) {
        this.categoriaId = categoriaId;
        this.nivelRequerido = nivelRequerido;
        this.imagemPath = imagemPath;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
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

    public String getImagemPath() {
        return imagemPath;
    }

    public void setImagemPath(String imagemPath) {
        this.imagemPath = imagemPath;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    public void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;
    }
}
