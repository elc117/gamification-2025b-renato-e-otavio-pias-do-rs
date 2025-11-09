package com.renato.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @Column(name = "total_niveis", nullable = false)
    private int totalNiveis;
    
    @Column(name = "pontos_para_proximo_nivel", nullable = false)
    private int pontosParaProximoNivel;
    
    @Column(name = "caminho_imagem_completa", length = 255)
    private String caminhoImagemCompleta;

    public Categoria() {
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.descricao = "";
        this.totalNiveis = 0;
        this.pontosParaProximoNivel = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getTotalNiveis() {
        return totalNiveis;
    }

    public void setTotalNiveis(int totalNiveis) {
        this.totalNiveis = totalNiveis;
    }

    public int getPontosParaProximoNivel() {
        return pontosParaProximoNivel;
    }

    public void setPontosParaProximoNivel(int pontosParaProximoNivel) {
        this.pontosParaProximoNivel = pontosParaProximoNivel;
    }

    public String getCaminhoImagemCompleta() {
        return caminhoImagemCompleta;
    }

    public void setCaminhoImagemCompleta(String caminhoImagemCompleta) {
        this.caminhoImagemCompleta = caminhoImagemCompleta;
    }
}
