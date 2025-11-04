package com.renato.model;

public class Categoria {
    private Long id;
    private String nome;
    private String descricao;
    private int totalNiveis;
    private int pontosParaProximoNivel;
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

    public  void setDescricao(String descricao) {
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
