package com.renato.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private int nivel;

    @Column(name = "pontuacao_total")
    private int pontuacaoTotal;

    @Column(name = "titulo_atual")
    private String tituloAtual;

    @Column(name = "total_tentativas", nullable = false)
    private int totalTentativas = 0;

    @Column(name = "acertos_totais", nullable = false)
    private int acertosTotais = 0;

    @Column(name = "taxa_acerto", nullable = false)
    private double taxaAcerto = 0.0;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String senha, int nivel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public String getTituloAtual() {
        return tituloAtual;
    }

    public void setTituloAtual(String tituloAtual) {
        this.tituloAtual = tituloAtual;
    }

    public int getTotalTentativas() {
        return totalTentativas;
    }

    public void setTotalTentativas(int totalTentativas) {
        this.totalTentativas = totalTentativas;
    }

    public int getAcertosTotais() {
        return acertosTotais;
    }

    public void setAcertosTotais(int acertosTotais) {
        this.acertosTotais = acertosTotais;
    }

    public double getTaxaAcerto() {
        return taxaAcerto;
    }

    public void setTaxaAcerto(double taxaAcerto) {
        this.taxaAcerto = taxaAcerto;
    }

}
