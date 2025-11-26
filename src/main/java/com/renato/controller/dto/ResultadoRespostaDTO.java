package com.renato.controller.dto;

import com.renato.model.Usuario;

/**
 * DTO para o resultado do processamento de uma resposta.
 */
public class ResultadoRespostaDTO {
    private boolean acertou;
    private int pontosGanhos;
    private String explicacao;
    private Usuario usuario;
    private boolean subiuNivelGlobal;
    private boolean mudouTitulo;

    public ResultadoRespostaDTO() {
    }

    public ResultadoRespostaDTO(boolean acertou, int pontosGanhos, String explicacao, Usuario usuario,
                               boolean subiuNivelGlobal, boolean mudouTitulo) {
        this.acertou = acertou;
        this.pontosGanhos = pontosGanhos;
        this.explicacao = explicacao;
        this.usuario = usuario;
        this.subiuNivelGlobal = subiuNivelGlobal;
        this.mudouTitulo = mudouTitulo;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public int getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(int pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isSubiuNivelGlobal() {
        return subiuNivelGlobal;
    }

    public void setSubiuNivelGlobal(boolean subiuNivelGlobal) {
        this.subiuNivelGlobal = subiuNivelGlobal;
    }

    public boolean isMudouTitulo() {
        return mudouTitulo;
    }

    public void setMudouTitulo(boolean mudouTitulo) {
        this.mudouTitulo = mudouTitulo;
    }
}
