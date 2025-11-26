package com.renato.service;

import com.renato.model.Noticia;
import com.renato.model.Resposta;
import com.renato.repository.NoticiaRepository;
import com.renato.repository.RespostaRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Service responsável por selecionar notícias para o jogo.
 */
public class NoticiaSelecaoService {
    private final NoticiaRepository noticiaRepository;
    private final RespostaRepository respostaRepository;
    private final Random random;

    // construtor padrão
    public NoticiaSelecaoService() {
        this(new NoticiaRepository(), new RespostaRepository(), new Random());
    }

    // construtor com injeção de dependências
    public NoticiaSelecaoService(NoticiaRepository noticiaRepository,
                                RespostaRepository respostaRepository,
                                Random random) {
        this.noticiaRepository = noticiaRepository;
        this.respostaRepository = respostaRepository;
        this.random = random;
    }

    /**
     * Obtém uma notícia aleatória de uma categoria que o usuário ainda não acertou.
     * Notícias erradas podem aparecer novamente.
     * 
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return notícia aleatória ou null se já acertou todas
     */
    public Noticia obterNoticiaAleatoria(Long usuarioId, Long categoriaId) {
        List<Noticia> noticiasDaCategoria = noticiaRepository.findByCategoria(categoriaId);
        Set<Long> noticiasAcertadas = obterNoticiasAcertadas(usuarioId);

        List<Noticia> noticiasDisponiveis = filtrarNoticiasDisponiveis(noticiasDaCategoria, noticiasAcertadas);

        if (noticiasDisponiveis.isEmpty()) {
            return null;
        }

        return selecionarNoticiaAleatoria(noticiasDisponiveis);
    }

    /**
     * Obtém IDs das notícias já acertadas pelo usuário.
     */
    private Set<Long> obterNoticiasAcertadas(Long usuarioId) {
        List<Resposta> respostasUsuario = respostaRepository.findByUsuario(usuarioId);
        Set<Long> noticiasAcertadas = new HashSet<>();
        
        for (Resposta resposta : respostasUsuario) {
            if (resposta.isEstaCorreta()) {
                noticiasAcertadas.add(resposta.getNoticiaId());
            }
        }
        
        return noticiasAcertadas;
    }

    /**
     * Filtra notícias disponíveis (não acertadas).
     */
    private List<Noticia> filtrarNoticiasDisponiveis(List<Noticia> noticias, Set<Long> noticiasAcertadas) {
        List<Noticia> disponiveis = new ArrayList<>();
        
        for (Noticia noticia : noticias) {
            if (!noticiasAcertadas.contains(noticia.getId())) {
                disponiveis.add(noticia);
            }
        }
        
        return disponiveis;
    }

    /**
     * Seleciona uma notícia aleatória da lista.
     */
    private Noticia selecionarNoticiaAleatoria(List<Noticia> noticias) {
        return noticias.get(random.nextInt(noticias.size()));
    }

    /**
     * Busca uma notícia por ID.
     */
    public Noticia buscarPorId(Long noticiaId) {
        return noticiaRepository.findById(noticiaId);
    }
}
