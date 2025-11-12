package com.renato.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "noticias")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    private Categoria categoria;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "eh_verdadeira", nullable = false)
    private boolean ehVerdadeira;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String explicacao;

    public Noticia() {
    }

    public Noticia(Long id, Long categoriaId, String titulo, String conteudo, boolean ehVerdadeira, String explicacao) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.ehVerdadeira = ehVerdadeira;
        this.explicacao = explicacao;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
