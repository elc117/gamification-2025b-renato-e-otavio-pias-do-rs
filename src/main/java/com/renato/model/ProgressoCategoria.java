package com.renato.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "progresso_categoria")
public class ProgressoCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    private Categoria categoria;

    @Column(name = "nivel_atual", nullable = false)
    private int nivelAtual;

    @Column(name = "pontos_maestria", nullable = false)
    private int pontosMaestria;

    @Column(name = "total_tentativas", nullable = false)
    private int totalTentativas = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pecas_desbloqueadas", joinColumns = @JoinColumn(name = "progresso_id"))
    @Column(name = "peca")
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

    public int getTotalTentativas() {
        return totalTentativas;
    }

    public void setTotalTentativas(int totalTentativas) {
        this.totalTentativas = totalTentativas;
    }

    public void incrementarTentativas() {
        this.totalTentativas++;
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

    public void adicionarPontos(int pontos) {
        this.pontosMaestria += pontos;
        if (this.pontosMaestria < 0) {
            this.pontosMaestria = 0;
        }
        atualizarNivel();
    }

    private void atualizarNivel() {
    
    }
}


