package com.renato.service;

import com.renato.model.ProgressoCategoria;
import com.renato.repository.NoticiaRepository;
import com.renato.repository.ProgressoCategoriaRepository;
import com.renato.repository.RespostaRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service responsável por gerenciar o progresso do usuário nas categorias.
 * Sistema baseado em COBERTURA: peças desbloqueadas em 25%, 50%, 75%, 100%.
 */
public class ProgressoCategoriaService {
    private final ProgressoCategoriaRepository progressoRepository;
    private final NoticiaRepository noticiaRepository;
    private final RespostaRepository respostaRepository;

    public ProgressoCategoriaService() {
        this.progressoRepository = new ProgressoCategoriaRepository();
        this.noticiaRepository = new NoticiaRepository();
        this.respostaRepository = new RespostaRepository();
    }

    /**
     * Atualiza o progresso do usuário em uma categoria específica.
     * 
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return Map com informações sobre o progresso atualizado
     */
    public Map<String, Object> atualizarProgresso(Long usuarioId, Long categoriaId) {
        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);

        // Criar progresso se não existe
        if (progresso == null) {
            progresso = new ProgressoCategoria(null, usuarioId, categoriaId, 0, 0, new ArrayList<>());
            progressoRepository.save(progresso);
        }

        // Incrementar tentativas na categoria
        progresso.incrementarTentativas();

        // Calcular cobertura de notícias
        long totalNoticias = noticiaRepository.countByCategoria(categoriaId);
        long acertosUnicos = respostaRepository.countAcertosByUsuarioAndCategoria(usuarioId, categoriaId);

        double percentualProgresso = totalNoticias > 0
            ? (acertosUnicos * 100.0 / totalNoticias)
            : 0;

        // Peças anteriores (para detectar desbloqueio)
        List<Integer> pecasAnteriores = new ArrayList<>(progresso.getPecasDesbloqueadas());

        // Determinar peças desbloqueadas
        List<Integer> pecasDesbloqueadas = calcularPecasDesbloqueadas(percentualProgresso);

        // Atualizar progresso
        progresso.setPecasDesbloqueadas(pecasDesbloqueadas);
        progresso.setPontosMaestria((int) acertosUnicos);
        progresso.setNivelAtual(pecasDesbloqueadas.size());

        boolean desbloqueouNovaPeca = pecasDesbloqueadas.size() > pecasAnteriores.size();

        // Salvar
        progressoRepository.update(progresso);

        // Retornar informações
        return montarResultado(desbloqueouNovaPeca, progresso, percentualProgresso, 
                              acertosUnicos, totalNoticias);
    }

    /**
     * Calcula quais peças devem estar desbloqueadas baseado no percentual.
     */
    private List<Integer> calcularPecasDesbloqueadas(double percentualProgresso) {
        List<Integer> pecas = new ArrayList<>();
        if (percentualProgresso >= 25.0) pecas.add(1);
        if (percentualProgresso >= 50.0) pecas.add(2);
        if (percentualProgresso >= 75.0) pecas.add(3);
        if (percentualProgresso >= 100.0) pecas.add(4);
        return pecas;
    }

    /**
     * Monta o resultado com todas as informações de progresso.
     */
    private Map<String, Object> montarResultado(boolean desbloqueouNovaPeca, ProgressoCategoria progresso,
                                               double percentualProgresso, long acertosUnicos, long totalNoticias) {
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("subiuNivel", desbloqueouNovaPeca);
        resultado.put("nivelAtual", progresso.getNivelAtual());
        resultado.put("pontosMaestria", progresso.getPontosMaestria());
        resultado.put("pecasDesbloqueadas", progresso.getPecasDesbloqueadas());
        resultado.put("percentualProgresso", Math.round(percentualProgresso * 100.0) / 100.0);
        resultado.put("acertosUnicos", acertosUnicos);
        resultado.put("totalNoticias", totalNoticias);
        resultado.put("noticiasFaltantes", totalNoticias - acertosUnicos);
        return resultado;
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
    public Map<String, Object> obtemEstatisticasCategoria(Long usuarioId, Long categoriaId) {
        Map<String, Object> estatisticas = new HashMap<>();

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

        estatisticas.put("totalNoticias", totalNoticias);
        estatisticas.put("acertosUnicos", acertosUnicos);
        estatisticas.put("tentativasNaCategoria", tentativasNaCategoria);
        estatisticas.put("errosNaCategoria", erros);
        estatisticas.put("taxaAcertoCategoria", Math.round(taxaAcerto * 100.0) / 100.0);
        estatisticas.put("percentualProgresso", Math.round(percentualProgresso * 100.0) / 100.0);
        estatisticas.put("noticiasFaltantes", totalNoticias - acertosUnicos);

        return estatisticas;
    }
}
