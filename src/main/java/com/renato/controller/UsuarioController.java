package com.renato.controller;

import com.renato.controller.dto.PerfilUsuarioDTO;
import com.renato.model.ConquistaUsuario;
import com.renato.model.ProgressoCategoria;
import com.renato.model.Usuario;
import com.renato.service.ConquistaVerificadorService;
import com.renato.service.ProgressoCategoriaService;
import com.renato.service.UsuarioService;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsável por operações relacionadas a usuários.
 */
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ProgressoCategoriaService progressoService;
    private final ConquistaVerificadorService conquistaService;

    public UsuarioController(UsuarioService usuarioService, 
                            ProgressoCategoriaService progressoService,
                            ConquistaVerificadorService conquistaService) {
        this.usuarioService = usuarioService;
        this.progressoService = progressoService;
        this.conquistaService = conquistaService;
    }

    /**
     * Lista todos os usuários.
     */
    public void listarTodos(Context ctx) {
        ctx.json(usuarioService.listarUsuarios());
    }

    /**
     * Busca usuário por ID.
     */
    public void buscarPorId(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Usuario usuario = usuarioService.encontrarUsuario(id);
        
        if (usuario != null) {
            ctx.json(usuario);
        } else {
            ctx.status(404).result("Usuário não encontrado");
        }
    }

    /**
     * Cria novo usuário.
     */
    public void criar(Context ctx) {
        Usuario novoUsuario = ctx.bodyAsClass(Usuario.class);
        
        // Validações
        if (novoUsuario.getNome() == null || novoUsuario.getNome().trim().isEmpty()) {
            ctx.status(400).result("Nome é obrigatório");
            return;
        }
        
        if (novoUsuario.getEmail() == null || novoUsuario.getEmail().trim().isEmpty()) {
            ctx.status(400).result("Email é obrigatório");
            return;
        }
        
        if (novoUsuario.getSenha() == null || novoUsuario.getSenha().trim().isEmpty()) {
            ctx.status(400).result("Senha é obrigatória");
            return;
        }
        
        // Verificar se email já existe
        Usuario usuarioExistente = usuarioService.encontrarUsuarioPorEmail(novoUsuario.getEmail());
        if (usuarioExistente != null) {
            ctx.status(409).result("Email já cadastrado");
            return;
        }
        
        novoUsuario.setNivel(0);
        Usuario usuarioSalvo = usuarioService.salvarUsuario(novoUsuario);
        ctx.status(201).json(usuarioSalvo);
    }

    /**
     * Atualiza usuário existente.
     */
    public void atualizar(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Usuario usuarioExistente = usuarioService.encontrarUsuario(id);

        if (usuarioExistente == null) {
            ctx.status(404).result("Usuário não encontrado");
            return;
        }

        Usuario usuarioAtualizado = ctx.bodyAsClass(Usuario.class);
        usuarioAtualizado.setId(id);
        usuarioService.atualizarUsuario(usuarioAtualizado);
        ctx.json(usuarioAtualizado);
    }

    /**
     * Deleta usuário.
     */
    public void deletar(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Usuario usuario = usuarioService.encontrarUsuario(id);

        if (usuario == null) {
            ctx.status(404).result("Usuário não encontrado");
            return;
        }

        usuarioService.deletarUsuario(usuario);
        ctx.status(204);
    }

    /**
     * Busca perfil completo do usuário logado.
     */
    public void buscarMeuPerfil(Context ctx) {
        try {
            Long usuarioId = AuthController.getUsuarioAutenticado(ctx);
            if (usuarioId == null) return;
            
            Usuario usuario = usuarioService.encontrarUsuario(usuarioId);

            if (usuario == null) {
                ctx.status(404).result("Usuário não encontrado");
                return;
            }

            List<ProgressoCategoria> progressosUsuario = progressoService.obtemProgressosUsuario(usuarioId);
            List<ConquistaUsuario> conquistasUsuarioLista = conquistaService.obtemConquistasUsuario(usuarioId);

            // Estatísticas do usuário
            int totalTentativas = usuario.getTotalTentativas();
            int totalAcertos = usuario.getAcertosTotais();
            double taxaAcerto = usuario.getTaxaAcerto();

            // Informações de progresso de nível
            int pontosAtuais = usuario.getPontuacaoTotal();
            int pontosFaltam = usuarioService.calcularPontosParaProximoNivel(usuario);
            int pontosProximoNivel = 100 * usuario.getNivel();

            PerfilUsuarioDTO perfil = new PerfilUsuarioDTO(
                usuario,
                progressosUsuario != null ? progressosUsuario : new ArrayList<>(),
                conquistasUsuarioLista != null ? conquistasUsuarioLista : new ArrayList<>(),
                totalTentativas,
                totalAcertos,
                taxaAcerto,
                pontosAtuais,
                pontosFaltam,
                pontosProximoNivel
            );

            ctx.json(perfil);
        } catch (Exception e) {
            System.err.println("Erro ao buscar perfil: " + e.getMessage());
            e.printStackTrace();
            ctx.status(500).result("Erro interno: " + e.getMessage());
        }
    }
}
