package com.renato.service;

import com.renato.model.Resposta;
import com.renato.repository.RespostaRepository;
import java.util.List;

/**
 * Service responsável por operações relacionadas a respostas.
 */
public class RespostaService {
    private final RespostaRepository respostaRepository;

    public RespostaService() {
        this.respostaRepository = new RespostaRepository();
    }

    public Resposta salvarResposta(Resposta resposta) {
        return respostaRepository.save(resposta);
    }

    public boolean jaRespondeuNoticia(Long usuarioId, Long noticiaId) {
        return respostaRepository.existsByUsuarioAndNoticia(usuarioId, noticiaId);
    }

    public List<Resposta> obtemRespostasUsuario(Long usuarioId) {
        return respostaRepository.findByUsuario(usuarioId);
    }

    public int obtemTotalRespostasUsuario(Long usuarioId) {
        return (int) respostaRepository.countByUsuario(usuarioId);
    }

    public int obtemTotalAcertosUsuario(Long usuarioId) {
        return (int) respostaRepository.countAcertosByUsuario(usuarioId);
    }
}
