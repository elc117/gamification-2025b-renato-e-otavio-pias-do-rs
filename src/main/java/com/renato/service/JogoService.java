package com.renato.service;

import com.renato.model.*;
import com.renato.repository.*;
import java.util.*;

/**
 * serviço responsável pela lógica principal do jogo.
 * processa respostas, calcula pontos, atualiza progresso e verifica conquistas.
 */
public class JogoService {
    // Constantes de pontuação
    private static final int PONTOS_ACERTO = 10;  // cada acerto vale 10 pontos

    private final NoticiaRepository noticiaRepository;
    private final RespostaRepository respostaRepository;
    private final ProgressoCategoriaRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConquistaRepository conquistaRepository;
    private final ConquistaUsuarioRepository conquistaUsuarioRepository;

    public JogoService() {
        this.noticiaRepository = new NoticiaRepository();
        this.respostaRepository = new RespostaRepository();
        this.progressoRepository = new ProgressoCategoriaRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.conquistaRepository = new ConquistaRepository();
        this.conquistaUsuarioRepository = new ConquistaUsuarioRepository();
    }

    /**
     * processa a resposta do usuário para uma notícia.
     *
     * @param usuarioId ID do usuário
     * @param noticiaId ID da notícia
     * @param respostaUsuario resposta do usuário (true = verdadeira, false = falsa)
     * @return Map com informações sobre o resultado da resposta
     */
    public Map<String, Object> processarResposta(Long usuarioId, Long noticiaId, boolean respostaUsuario) {
        // 1. buscar notícia e verificar se existe
        Noticia noticia = noticiaRepository.findById(noticiaId);
        if (noticia == null) {
            throw new IllegalArgumentException("Notícia não encontrada");
        }

        // 2. verificar se a resposta está correta
        boolean acertou = (respostaUsuario == noticia.isEhVerdadeira());

        // 3. verificar se já existe uma resposta para esta notícia
        Resposta respostaExistente = respostaRepository.findByUsuarioAndNoticia(usuarioId, noticiaId);
        boolean jaAcertouAntes = (respostaExistente != null && respostaExistente.isEstaCorreta());

        // 4. calcular pontos ganhos (só ganha pontos se acertar pela primeira vez)
        int pontosGanhos = 0;
        if (acertou && !jaAcertouAntes) {
            pontosGanhos = PONTOS_ACERTO;
        }

        // 5. atualizar pontuação global e nível do usuário
        Usuario usuario = usuarioRepository.findById(usuarioId);
        int nivelAnterior = usuario.getNivel();
        String tituloAnterior = usuario.getTituloAtual();

        // incrementar tentativas sempre (certo ou errado)
        usuario.incrementarTentativas();

        // incrementar acertos se acertou (mesmo que já tenha acertado antes - conta a tentativa)
        if (acertou) {
            usuario.incrementarAcertos();
        }

        // adicionar pontos apenas se acertar pela primeira vez
        if (pontosGanhos > 0) {
            usuario.adicionarPontos(pontosGanhos);
        }
        
        usuarioRepository.update(usuario);

        boolean subiuNivelGlobal = usuario.getNivel() > nivelAnterior;
        boolean mudouTitulo = !Objects.equals(tituloAnterior, usuario.getTituloAtual());

        // 6. salvar ou atualizar resposta no banco
        if (respostaExistente != null) {
            // UPDATE: atualizar resposta existente (permitindo repetição)
            respostaExistente.setRespostaUsuario(respostaUsuario);
            respostaExistente.setEstaCorreta(acertou);
            respostaExistente.setPontosGanhos(pontosGanhos);
            respostaExistente.incrementarTentativas();
            respostaRepository.update(respostaExistente);
        } else {
            // INSERT: primeira tentativa nesta notícia
            Resposta resposta = new Resposta(null, usuarioId, noticiaId, respostaUsuario, acertou, pontosGanhos);
            respostaRepository.save(resposta);
        }

        // 7. atualizar progresso na categoria
        Map<String, Object> infoProgresso = atualizarProgressoCategoria(usuarioId, noticia.getCategoriaId(), pontosGanhos);

        // 8. verificar e atribuir conquistas (se houver)
        List<Conquista> conquistasDesbloqueadas = verificarConquistas(usuarioId);

        // 9. montar e retornar resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("acertou", acertou);
        resultado.put("pontosGanhos", pontosGanhos);
        resultado.put("explicacao", noticia.getExplicacao());

        // Informações de progresso na categoria (cobertura)
        resultado.put("desbloqueouNovaPeca", infoProgresso.get("subiuNivel"));
        resultado.put("nivelCategoria", infoProgresso.get("nivelAtual"));
        resultado.put("pecasDesbloqueadas", infoProgresso.get("pecasDesbloqueadas"));
        resultado.put("percentualProgresso", infoProgresso.get("percentualProgresso"));
        resultado.put("acertosUnicos", infoProgresso.get("acertosUnicos"));
        resultado.put("totalNoticias", infoProgresso.get("totalNoticias"));
        resultado.put("noticiasFaltantes", infoProgresso.get("noticiasFaltantes"));

        // Informações de progresso global do usuário
        resultado.put("nivelGlobal", usuario.getNivel());
        resultado.put("tituloAtual", usuario.getTituloAtual());
        resultado.put("pontuacaoTotal", usuario.getPontuacaoTotal());
        resultado.put("subiuNivelGlobal", subiuNivelGlobal);
        resultado.put("mudouTitulo", mudouTitulo);
        resultado.put("pontosParaProximoNivel", usuario.pontosParaProximoNivel());
        resultado.put("totalTentativas", usuario.getTotalTentativas());
        resultado.put("acertosTotais", usuario.getAcertosTotais());
        resultado.put("taxaAcerto", usuario.getTaxaAcerto());

        // Informações de conquistas desbloqueadas
        if (conquistasDesbloqueadas != null && !conquistasDesbloqueadas.isEmpty()) {
            List<Map<String, Object>> conquistasInfo = new ArrayList<>();
            for (Conquista c : conquistasDesbloqueadas) {
                Map<String, Object> info = new HashMap<>();
                info.put("nome", c.getNome());
                info.put("descricao", c.getDescricao());
                conquistasInfo.add(info);
            }
            resultado.put("conquistasDesbloqueadas", conquistasInfo);
        }

        return resultado;
    }

    /**
     * atualiza o progresso do usuário em uma categoria específica.
     * sistema baseado em COBERTURA: (acertos_únicos / total_notícias) * 100
     * peças desbloqueadas em: 25%, 50%, 75%, 100%
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @param pontosGanhos pontos a adicionar (usado apenas para pontuação global)
     * @return Map com informações sobre o progresso atualizado
     */
    private Map<String, Object> atualizarProgressoCategoria(Long usuarioId, Long categoriaId, int pontosGanhos) {
        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);

        // se não existe progresso, criar um novo
        if (progresso == null) {
            progresso = new ProgressoCategoria(null, usuarioId, categoriaId, 0, 0, new ArrayList<>());
            progressoRepository.save(progresso);
        }

        // incrementar tentativas na categoria (a cada resposta, certa ou errada)
        progresso.incrementarTentativas();

        // calcular progresso baseado em cobertura de notícias
        long totalNoticias = noticiaRepository.countByCategoria(categoriaId);
        long acertosUnicos = respostaRepository.countAcertosByUsuarioAndCategoria(usuarioId, categoriaId);

        double percentualProgresso = totalNoticias > 0
            ? (acertosUnicos * 100.0 / totalNoticias)
            : 0;

        // salvar peças anteriores para verificar se desbloqueou nova peça
        List<Integer> pecasAnteriores = new ArrayList<>(progresso.getPecasDesbloqueadas());

        // determinar quais peças devem estar desbloqueadas baseado no percentual
        List<Integer> pecasDesbloqueadas = new ArrayList<>();
        if (percentualProgresso >= 25.0) pecasDesbloqueadas.add(1);
        if (percentualProgresso >= 50.0) pecasDesbloqueadas.add(2);
        if (percentualProgresso >= 75.0) pecasDesbloqueadas.add(3);
        if (percentualProgresso >= 100.0) pecasDesbloqueadas.add(4);

        // atualizar progresso
        progresso.setPecasDesbloqueadas(pecasDesbloqueadas);
        progresso.setPontosMaestria((int) acertosUnicos); // salva quantidade de acertos

        // calcular "nível" baseado em quantas peças tem (para compatibilidade)
        int nivelAtual = pecasDesbloqueadas.size();
        progresso.setNivelAtual(nivelAtual);

        // verificar se desbloqueou nova peça
        boolean desbloqueouNovaPeca = pecasDesbloqueadas.size() > pecasAnteriores.size();

        // salvar o progresso atualizado
        progressoRepository.update(progresso);

        // retornar informações do progresso
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("subiuNivel", desbloqueouNovaPeca);
        resultado.put("nivelAtual", nivelAtual);
        resultado.put("pontosMaestria", (int) acertosUnicos);
        resultado.put("pecasDesbloqueadas", pecasDesbloqueadas);
        resultado.put("percentualProgresso", Math.round(percentualProgresso * 100.0) / 100.0); // 2 casas decimais
        resultado.put("acertosUnicos", acertosUnicos);
        resultado.put("totalNoticias", totalNoticias);
        resultado.put("noticiasFaltantes", totalNoticias - acertosUnicos);

        return resultado;
    }

    /**
     * verifica e atribui conquistas ao usuário baseado em seu progresso.
     * analisa todas as conquistas disponíveis e verifica se o usuário atende aos critérios.
     * @return lista de conquistas recém-desbloqueadas nesta chamada
     */
    private List<Conquista> verificarConquistas(Long usuarioId) {
        // Lista para armazenar conquistas recém-desbloqueadas
        List<Conquista> conquistasRecemDesbloqueadas = new ArrayList<>();

        // 1. buscar todas as conquistas disponíveis no sistema
        List<Conquista> todasConquistas = conquistaRepository.findAll();
        if (todasConquistas == null || todasConquistas.isEmpty()) {
            return conquistasRecemDesbloqueadas; // não há conquistas cadastradas
        }

        // 2. buscar conquistas já desbloqueadas pelo usuário
        List<ConquistaUsuario> conquistasUsuario = conquistaUsuarioRepository.findByUsuario(usuarioId);
        Set<Long> conquistasJaDesbloqueadas = new HashSet<>();
        for (ConquistaUsuario cu : conquistasUsuario) {
            conquistasJaDesbloqueadas.add(cu.getConquistaId());
        }

        // 3. calcular pontos totais do usuário (soma dos pontos de todas as respostas corretas)
        int pontosTotais = calcularPontosTotais(usuarioId);

        // 4. verificar cada conquista para ver se deve ser desbloqueada
        for (Conquista conquista : todasConquistas) {
            // se já desbloqueou esta conquista, pular
            if (conquistasJaDesbloqueadas.contains(conquista.getId())) {
                continue;
            }

            boolean desbloqueou = false;

            if ("PONTOS_TOTAIS".equalsIgnoreCase(conquista.getTipo())) {
                desbloqueou = pontosTotais >= conquista.getValorRequerido();
            }

            // se desbloqueou a conquista, salvar no banco
            if (desbloqueou) {
                ConquistaUsuario novaConquista = new ConquistaUsuario(
                    null,
                    usuarioId,
                    conquista.getId(),
                    java.time.LocalDateTime.now()
                );
                conquistaUsuarioRepository.save(novaConquista);
                conquistasRecemDesbloqueadas.add(conquista);
                System.out.println("Conquista desbloqueada: " + conquista.getNome() + " para usuário " + usuarioId);
            }
        }

        return conquistasRecemDesbloqueadas;
    }

    /**
     * calcula o total de pontos acumulados pelo usuário.
     * soma os pontos de todas as respostas corretas.
     */
    private int calcularPontosTotais(Long usuarioId) {
        List<Resposta> respostas = respostaRepository.findByUsuario(usuarioId);
        int total = 0;
        for (Resposta resposta : respostas) {
            total += resposta.getPontosGanhos();
        }
        return total;
    }

    /**
     * obtém uma notícia aleatória de uma categoria específica que o usuário ainda não acertou.
     * agora salvamos todas as tentativas, mas notícias erradas podem aparecer novamente.
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria escolhida
     * @return notícia aleatória da categoria não acertada, ou null se já acertou todas
     */
    public Noticia obterNoticiaAleatoriaDaCategoria(Long usuarioId, Long categoriaId) {
        List<Noticia> noticiasDaCategoria = noticiaRepository.findByCategoria(categoriaId);
        List<Resposta> respostasUsuario = respostaRepository.findByUsuario(usuarioId);

        // filtrar apenas notícias já acertadas (erradas podem aparecer de novo)
        Set<Long> noticiasAcertadas = new HashSet<>();
        for (Resposta resposta : respostasUsuario) {
            if (resposta.isEstaCorreta()) { // só bloqueia se acertou
                noticiasAcertadas.add(resposta.getNoticiaId());
            }
        }

        List<Noticia> noticiasDisponiveis = new ArrayList<>();
        for (Noticia noticia : noticiasDaCategoria) {
            if (!noticiasAcertadas.contains(noticia.getId())) {
                noticiasDisponiveis.add(noticia);
            }
        }

        if (noticiasDisponiveis.isEmpty()) {
            return null;
        }

        // retornar notícia aleatória
        Random random = new Random();
        return noticiasDisponiveis.get(random.nextInt(noticiasDisponiveis.size()));
    }
}