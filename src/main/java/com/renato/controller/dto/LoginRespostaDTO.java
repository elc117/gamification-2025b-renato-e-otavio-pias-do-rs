package com.renato.controller.dto;

import com.renato.model.Usuario;

/**
 * DTO para resposta de login.
 */
public class LoginRespostaDTO {
    private String mensagem;
    private Usuario usuario;
    private String token;

    public LoginRespostaDTO() {
    }

    public LoginRespostaDTO(String mensagem, Usuario usuario, String token) {
        this.mensagem = mensagem;
        this.usuario = usuario;
        this.token = token;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
