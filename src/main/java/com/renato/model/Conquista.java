package com.renato.model;

public class Conquista {
    private Long id;
    private String nome;
    private String descricao;
    private String icone;
    private String criterio;
    private String tipo;
    private int valorRequerido;

    public Conquista() {
    }

    public Conquista(Long id, String nome, String descricao, String icone, String criterio, String tipo, int valorRequerido) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.icone = icone;
        this.criterio = criterio;
        this.tipo = tipo;
        this.valorRequerido = valorRequerido;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getValorRequerido() {
        return valorRequerido;
    }

    public void setValorRequerido(int valorRequerido) {
        this.valorRequerido = valorRequerido;
    }
}
