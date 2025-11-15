package com.renato.controller.dto;

/**
 * DTO para requisição de resposta a uma notícia.
 */
public class RespostaRequest {
    private boolean resposta;

    public RespostaRequest() {
    }

    public RespostaRequest(boolean resposta) {
        this.resposta = resposta;
    }

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }
}
