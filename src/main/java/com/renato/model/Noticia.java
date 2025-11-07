package com.renato.model;

public class Noticia {
    private Long id;
    private Long categoriaId;
    private Categoria categoria;
    private String titulo;
    private String conteudo;
    private boolean ehVerdadeira;
    private String explicacao;
    private int dificuldade;
    private String fontes;

    public Noticia() {
    }

    public Noticia(Long id, Long categoriaId, String titulo, String conteudo, boolean ehVerdadeira, String explicacao, int dificuldade) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.ehVerdadeira = ehVerdadeira;
        this.explicacao = explicacao;
        this.dificuldade = dificuldade;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public boolean isEhVerdadeira() {
        return ehVerdadeira;
    }

    public void setEhVerdadeira(boolean ehVerdadeira) {
        this.ehVerdadeira = ehVerdadeira;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
