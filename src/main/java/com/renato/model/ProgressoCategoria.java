package com.renato.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
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

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "pecas_desbloqueadas", columnDefinition = "integer[]")
    private List<Integer> pecasDesbloqueadas = new ArrayList<>();

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
    }

    // ========== COMPORTAMENTOS (Métodos de Negócio) ==========

    /**
     * Atualiza o progresso com base no percentual de cobertura.
     * Desbloqueia peças automaticamente conforme o percentual.
     * @param percentualProgresso percentual de notícias acertadas (0-100)
     * @return true se desbloqueou uma nova peça
     */
    public boolean atualizarProgresso(double percentualProgresso) {
        int pecasAnteriores = this.pecasDesbloqueadas.size();
        
        // recalcular peças baseado no percentual
        List<Integer> novasPecas = calcularPecasDesbloqueadas(percentualProgresso);
        this.pecasDesbloqueadas = novasPecas;
        this.nivelAtual = novasPecas.size();
        
        return novasPecas.size() > pecasAnteriores;
    }

    /**
     * Calcula quais peças devem estar desbloqueadas baseado no percentual.
     * Sistema de recompensas: 25%, 50%, 75%, 100%
     */
    private List<Integer> calcularPecasDesbloqueadas(double percentualProgresso) {
        List<Integer> pecas = new ArrayList<>();
        if (percentualProgresso >= 25.0) pecas.add(1);
        if (percentualProgresso >= 50.0) pecas.add(2);
        if (percentualProgresso >= 75.0) pecas.add(3);
        if (percentualProgresso >= 100.0) pecas.add(4);
        return pecas;
    }

    /**
     * Verifica se possui todas as peças desbloqueadas (imagem completa).
     */
    public boolean possuiImagemCompleta() {
        return pecasDesbloqueadas.size() >= 4;
    }

    /**
     * Verifica se uma peça específica está desbloqueada.
     */
    public boolean possuiPeca(int numeroPeca) {
        return pecasDesbloqueadas.contains(numeroPeca);
    }

    /**
     * Registra pontos de maestria (equivale a acertos únicos na categoria).
     */
    public void setPontosMaestriaDiretamente(int pontos) {
        this.pontosMaestria = pontos;
    }
}