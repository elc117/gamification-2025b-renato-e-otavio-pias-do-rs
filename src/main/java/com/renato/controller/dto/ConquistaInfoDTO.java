package com.renato.controller.dto;

/**
 * DTO para informações de conquista desbloqueada.
 */
public class ConquistaInfoDTO {
    private String nome;
    private String descricao;

    public ConquistaInfoDTO() {
    }

    public ConquistaInfoDTO(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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
}
