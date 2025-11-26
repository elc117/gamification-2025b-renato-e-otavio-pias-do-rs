package com.renato.service;

import com.renato.controller.dto.ProgressoCategoriaDTO;
import com.renato.controller.dto.EstatisticasCategoriaDTO;
import com.renato.model.ProgressoCategoria;
import com.renato.repository.NoticiaRepository;
import com.renato.repository.ProgressoCategoriaRepository;
import com.renato.repository.RespostaRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsável por gerenciar o progresso do usuário nas categorias.
 * Sistema baseado em COBERTURA: peças desbloqueadas em 25%, 50%, 75%, 100%.
 */
public class ProgressoCategoriaService {
    private final ProgressoCategoriaRepository progressoRepository;
    private final NoticiaRepository noticiaRepository;
    private final RespostaRepository respostaRepository;

    // Construtor padrão
    public ProgressoCategoriaService() {
        this(new ProgressoCategoriaRepository(), 
             new NoticiaRepository(), 
             new RespostaRepository());
    }

    // Construtor com Injeção de Dependências
    public ProgressoCategoriaService(ProgressoCategoriaRepository progressoRepository,
                                    NoticiaRepository noticiaRepository,
                                    RespostaRepository respostaRepository) {
        this.progressoRepository = progressoRepository;
        this.noticiaRepository = noticiaRepository;
        this.respostaRepository = respostaRepository;
    }

    /**
     * Atualiza o progresso do usuário em uma categoria específica.
     * 
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return ProgressoCategoriaDTO com informações sobre o progresso atualizado
     */
    public ProgressoCategoriaDTO atualizarProgresso(Long usuarioId, Long categoriaId) {
        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);

        // Criar progresso se não existe
        if (progresso == null) {
            progresso = new ProgressoCategoria(null, usuarioId, categoriaId, 0, 0, new ArrayList<>());
            progressoRepository.save(progresso);
        }

        // incrementar tentativas na categoria
        progresso.incrementarTentativas();

        // calcular cobertura de notícias
        long totalNoticias = noticiaRepository.countByCategoria(categoriaId);
        long acertosUnicos = respostaRepository.countAcertosByUsuarioAndCategoria(usuarioId, categoriaId);

        double percentualProgresso = totalNoticias > 0
            ? (acertosUnicos * 100.0 / totalNoticias)
            : 0;

        // atualizar progresso usando comportamento da entidade
        boolean desbloqueouNovaPeca = progresso.atualizarProgresso(percentualProgresso);
        progresso.setPontosMaestriaDiretamente((int) acertosUnicos);

        // Salvar
        progressoRepository.update(progresso);

        // Retornar informações
        return montarResultado(desbloqueouNovaPeca, progresso, percentualProgresso, 
                              acertosUnicos, totalNoticias);
    }



    /**
     * Monta o resultado com todas as informações de progresso.
     */
    private ProgressoCategoriaDTO montarResultado(boolean desbloqueouNovaPeca, ProgressoCategoria progresso,
                                               double percentualProgresso, long acertosUnicos, long totalNoticias) {
        return new ProgressoCategoriaDTO(
            desbloqueouNovaPeca,
            progresso.getNivelAtual(),
            progresso.getPontosMaestria(),
            progresso.getPecasDesbloqueadas(),
            Math.round(percentualProgresso * 100.0) / 100.0,
            acertosUnicos,
            totalNoticias,
            totalNoticias - acertosUnicos
        );
    }

    /**
     * Obtém o progresso de um usuário em todas as categorias.
     */
    public List<ProgressoCategoria> obtemProgressosUsuario(Long usuarioId) {
        return progressoRepository.findByUsuario(usuarioId);
    }

    /**
     * Obtém o progresso de um usuário em uma categoria específica.
     */
    public ProgressoCategoria obtemProgressoCategoria(Long usuarioId, Long categoriaId) {
        return progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);
    }

    /**
     * Salva um novo progresso de categoria.
     */
    public ProgressoCategoria salvarProgressoCategoria(ProgressoCategoria progresso) {
        return progressoRepository.save(progresso);
    }

    /**
     * Atualiza um progresso de categoria existente.
     */
    public ProgressoCategoria atualizarProgressoCategoria(ProgressoCategoria progresso) {
        return progressoRepository.update(progresso);
    }

    /**
     * Calcula estatísticas detalhadas do usuário em uma categoria específica.
     */
    public EstatisticasCategoriaDTO obtemEstatisticasCategoria(Long usuarioId, Long categoriaId) {
        long totalNoticias = noticiaRepository.countByCategoria(categoriaId);
        long acertosUnicos = respostaRepository.countAcertosByUsuarioAndCategoria(usuarioId, categoriaId);

        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);
        long tentativasNaCategoria = progresso != null ? progresso.getTotalTentativas() : 0;

        double taxaAcerto = tentativasNaCategoria > 0
            ? (acertosUnicos * 100.0 / tentativasNaCategoria)
            : 0;

        double percentualProgresso = totalNoticias > 0
            ? (acertosUnicos * 100.0 / totalNoticias)
            : 0;

        long erros = tentativasNaCategoria - acertosUnicos;

        return new EstatisticasCategoriaDTO(
            totalNoticias,
            acertosUnicos,
            tentativasNaCategoria,
            erros,
            Math.round(taxaAcerto * 100.0) / 100.0,
            Math.round(percentualProgresso * 100.0) / 100.0,
            totalNoticias - acertosUnicos
        );
    }
}
