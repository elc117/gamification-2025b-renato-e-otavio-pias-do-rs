package com.renato.controller;

import com.renato.controller.dto.LoginRequest;
import com.renato.model.Usuario;
import com.renato.service.UsuarioService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsável por autenticação e gerenciamento de sessão.
 */
public class AuthController {
    private final UsuarioService usuarioService;
    private static final String SESSION_USER_ID = "usuarioId";

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Realiza login do usuário (sem senha, apenas email).
     */
    public void login(Context ctx) {
        LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);
        String email = loginRequest.getEmail();
        
        if (email == null || email.trim().isEmpty()) {
            ctx.status(400).result("Email é obrigatório");
            return;
        }

        Usuario usuario = usuarioService.encontrarUsuarioPorEmail(email);
        
        if (usuario == null) {
            ctx.status(404).result("Usuário não encontrado com este email");
            return;
        }

        // Criar sessão (para funcionar no Render)
        ctx.sessionAttribute(SESSION_USER_ID, usuario.getId());
        
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Login realizado com sucesso");
        resposta.put("usuario", usuario);
        resposta.put("token", usuario.getId().toString()); // Token simples para itch.io
        ctx.json(resposta);
    }

    /**
     * Realiza logout do usuário.
     */
    public void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.result("Logout realizado com sucesso");
    }

    /**
     * Verifica usuário autenticado na sessão.
     */
    public void getUsuarioSessao(Context ctx) {
        Long usuarioId = ctx.sessionAttribute(SESSION_USER_ID);
        if (usuarioId == null) {
            ctx.status(401).result("Nenhum usuário autenticado");
            return;
        }
        
        Usuario usuario = usuarioService.encontrarUsuario(usuarioId);
        if (usuario != null) {
            ctx.json(usuario);
        } else {
            ctx.status(404).result("Usuário da sessão não encontrado");
        }
    }

    /**
     * Método auxiliar para obter ID do usuário autenticado.
     * Retorna null se não autenticado (e já define status 401).
     * Suporta tanto sessão (Render) quanto token no header (itch.io).
     */
    public static Long getUsuarioAutenticado(Context ctx) {
        // Primeiro tenta sessão (para Render)
        Long usuarioId = ctx.sessionAttribute(SESSION_USER_ID);
        
        // Se não tem sessão, tenta header X-User-Token (para itch.io)
        if (usuarioId == null) {
            String token = ctx.header("X-User-Token");
            if (token != null && !token.isEmpty()) {
                try {
                    usuarioId = Long.parseLong(token);
                } catch (NumberFormatException e) {
                    // Token inválido, ignora
                }
            }
        }
        
        if (usuarioId == null) {
            ctx.status(401).result("Usuário não autenticado. Faça login primeiro.");
            return null;
        }
        return usuarioId;
    }
}
