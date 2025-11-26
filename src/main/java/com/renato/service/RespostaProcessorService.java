package com.renato.service;

import com.renato.controller.dto.ResultadoRespostaDTO;
import com.renato.model.Noticia;
import com.renato.model.Resposta;
import com.renato.model.Usuario;
import com.renato.repository.RespostaRepository;
import com.renato.repository.UsuarioRepository;

/**
 * Service responsável por processar respostas e calcular pontuações.
 */
public class RespostaProcessorService {
    private static final int PONTOS_ACERTO = 10;

    private final RespostaRepository respostaRepository;
    private final UsuarioRepository usuarioRepository;

    // Construtor padrão
    public RespostaProcessorService() {
        this(new RespostaRepository(), new UsuarioRepository());
    }

    // Construtor com Injeção de Dependências
    public RespostaProcessorService(RespostaRepository respostaRepository, 
                                   UsuarioRepository usuarioRepository) {
        this.respostaRepository = respostaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Processa uma resposta do usuário para uma notícia.
     * 
     * @param usuarioId ID do usuário
     * @param noticia Notícia respondida
     * @param respostaUsuario Resposta do usuário (true/false)
     * @return DTO com informações sobre o processamento
     */
    public ResultadoRespostaDTO processarResposta(Long usuarioId, Noticia noticia, boolean respostaUsuario) {
        boolean acertou = (respostaUsuario == noticia.isEhVerdadeira()); // verificar se a resposta está correta

        // Verificar se já existe resposta anterior
        Resposta respostaExistente = respostaRepository.findByUsuarioAndNoticia(usuarioId, noticia.getId());
        boolean jaAcertouAntes = (respostaExistente != null && respostaExistente.isEstaCorreta());

        // Calcular pontos (só ganha se acertar pela primeira vez)
        int pontosGanhos = 0;
        if (acertou && !jaAcertouAntes) {
            pontosGanhos = PONTOS_ACERTO;
        }

        // atualizar as estatísticas do usuário usando comportamentos da entidade
        Usuario usuario = usuarioRepository.findById(usuarioId);

        // registrar tentativa (pra isso, encapsula lógica de acertos e taxa)
        usuario.registrarTentativa(acertou);

        // adicionar pontos se ganhou (encapsula lógica de nível)
        boolean subiuNivelGlobal = false;
        if (pontosGanhos > 0) {
            subiuNivelGlobal = usuario.adicionarPontos(pontosGanhos);
        }

        // atualizar título (encapsula lógica de títulos)
        boolean mudouTitulo = usuario.atualizarTitulo();

        usuarioRepository.update(usuario);

        // salvar ou atualizar resposta
        salvarResposta(respostaExistente, usuarioId, noticia.getId(), respostaUsuario, acertou, pontosGanhos);

        // Retornar o DTO tipado
        return new ResultadoRespostaDTO(
            acertou,
            pontosGanhos,
            noticia.getExplicacao(),
            usuario,
            subiuNivelGlobal,
            mudouTitulo
        );
    }

    /**
     * Salva ou atualiza uma resposta no banco de dados.
     */
    private void salvarResposta(Resposta respostaExistente, Long usuarioId, Long noticiaId, 
                                boolean respostaUsuario, boolean acertou, int pontosGanhos) {
        if (respostaExistente != null) {
            // UPDATE: atualizar resposta existente
            respostaExistente.setRespostaUsuario(respostaUsuario);
            respostaExistente.setEstaCorreta(acertou);
            respostaExistente.setPontosGanhos(pontosGanhos);
            respostaExistente.incrementarTentativas();
            respostaRepository.update(respostaExistente);
        } else {
            // INSERT: primeira tentativa
            Resposta resposta = new Resposta(null, usuarioId, noticiaId, respostaUsuario, acertou, pontosGanhos);
            respostaRepository.save(resposta);
        }
    }

    /**
     * Calcula o total de pontos de um usuário.
     */
    public int calcularPontosTotais(Long usuarioId) {
        return respostaRepository.findByUsuario(usuarioId)
            .stream()
            .mapToInt(Resposta::getPontosGanhos)
            .sum();
    }
}
