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
    private int nivel;

    @Column(name = "pontuacao_total")
    private int pontuacaoTotal;

    @Column(name = "titulo_atual")
    private String tituloAtual;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, int nivel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacaoTotal += pontos;
        atualizarNivel();
    }

    private void atualizarNivel() {
    }
}
