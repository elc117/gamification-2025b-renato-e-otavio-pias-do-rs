package com.renato.service;

import com.renato.model.Noticia;
import com.renato.model.Resposta;
import com.renato.model.Usuario;
import com.renato.repository.RespostaRepository;
import com.renato.repository.UsuarioRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Service responsável por processar respostas e calcular pontuações.
 */
public class RespostaProcessorService {
    private static final int PONTOS_ACERTO = 10;

    private final RespostaRepository respostaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public RespostaProcessorService() {
        this.respostaRepository = new RespostaRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Processa uma resposta do usuário para uma notícia.
     * 
     * @param usuarioId ID do usuário
     * @param noticia Notícia respondida
     * @param respostaUsuario Resposta do usuário (true/false)
     * @return Map com informações sobre o processamento
     */
    public Map<String, Object> processarResposta(Long usuarioId, Noticia noticia, boolean respostaUsuario) {
        // Verificar se a resposta está correta
        boolean acertou = (respostaUsuario == noticia.isEhVerdadeira());

        // Verificar se já existe resposta anterior
        Resposta respostaExistente = respostaRepository.findByUsuarioAndNoticia(usuarioId, noticia.getId());
        boolean jaAcertouAntes = (respostaExistente != null && respostaExistente.isEstaCorreta());

        // Calcular pontos (só ganha se acertar pela primeira vez)
        int pontosGanhos = 0;
        if (acertou && !jaAcertouAntes) {
            pontosGanhos = PONTOS_ACERTO;
        }

        // Atualizar estatísticas do usuário
        Usuario usuario = usuarioRepository.findById(usuarioId);
        int nivelAnterior = usuario.getNivel();
        String tituloAnterior = usuario.getTituloAtual();

        // Incrementar tentativas sempre
        usuarioService.incrementarTentativas(usuario);

        // Incrementar acertos se acertou
        if (acertou) {
            usuarioService.incrementarAcertos(usuario);
        }

        // Adicionar pontos se ganhou
        if (pontosGanhos > 0) {
            usuarioService.adicionarPontos(usuario, pontosGanhos);
        }

        usuarioRepository.update(usuario);

        boolean subiuNivelGlobal = usuario.getNivel() > nivelAnterior;
        boolean mudouTitulo = !Objects.equals(tituloAnterior, usuario.getTituloAtual());

        // Salvar ou atualizar resposta
        salvarResposta(respostaExistente, usuarioId, noticia.getId(), respostaUsuario, acertou, pontosGanhos);

        // Montar resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("acertou", acertou);
        resultado.put("pontosGanhos", pontosGanhos);
        resultado.put("explicacao", noticia.getExplicacao());
        resultado.put("usuario", usuario);
        resultado.put("subiuNivelGlobal", subiuNivelGlobal);
        resultado.put("mudouTitulo", mudouTitulo);

        return resultado;
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
