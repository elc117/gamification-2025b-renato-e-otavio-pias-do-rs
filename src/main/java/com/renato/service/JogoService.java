package com.renato.service;

import com.renato.model.*;
import com.renato.repository.*;
import java.util.*;

/**
 * Serviço responsável pela lógica principal do jogo.
 * Processa respostas, calcula pontos, atualiza progresso e verifica conquistas.
 */
public class JogoService {
    private final NoticiaRepository noticiaRepository;
    private final RespostaRepository respostaRepository;
    private final ProgressoCategoriaRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConquistaRepository conquistaRepository;
    private final ConquistaUsuarioRepository conquistaUsuarioRepository;
    private final PontuacaoService pontuacaoService;

    public JogoService() {
        this.noticiaRepository = new NoticiaRepository();
        this.respostaRepository = new RespostaRepository();
        this.progressoRepository = new ProgressoCategoriaRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.conquistaRepository = new ConquistaRepository();
        this.conquistaUsuarioRepository = new ConquistaUsuarioRepository();
        this.pontuacaoService = new PontuacaoService();
    }

    /**
     * Processa a resposta do usuário para uma notícia.
     *
     * @param usuarioId ID do usuário
     * @param noticiaId ID da notícia
     * @param respostaUsuario resposta do usuário (true = verdadeira, false = falsa)
     * @return Map com informações sobre o resultado da resposta
     */
    public Map<String, Object> processarResposta(Long usuarioId, Long noticiaId, boolean respostaUsuario) {
        // 1. Buscar notícia e verificar se existe
        Noticia noticia = noticiaRepository.findById(noticiaId);
        if (noticia == null) {
            throw new IllegalArgumentException("Notícia não encontrada");
        }

        // 2. Verificar se o usuário já respondeu esta notícia
        if (jaRespondeuNoticia(usuarioId, noticiaId)) {
            throw new IllegalStateException("Usuário já respondeu esta notícia");
        }

        // 3. Verificar se a resposta está correta
        boolean acertou = (respostaUsuario == noticia.isEhVerdadeira());

        // 4. Calcular pontos ganhos
        int pontosGanhos = pontuacaoService.calcularPontos(acertou);

        // 5. Salvar a resposta no banco de dados
        Resposta resposta = new Resposta(null, usuarioId, noticiaId, respostaUsuario, acertou, pontosGanhos);
        respostaRepository.save(resposta);

        // 6. Atualizar progresso na categoria
        Map<String, Object> infoProgresso = atualizarProgressoCategoria(usuarioId, noticia.getCategoriaId(), pontosGanhos);

        // 7. Verificar e atribuir conquistas (se houver)
        verificarConquistas(usuarioId);

        // 8. Montar e retornar resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("acertou", acertou);
        resultado.put("pontosGanhos", pontosGanhos);
        resultado.put("explicacao", noticia.getExplicacao());
        resultado.put("subiuNivel", infoProgresso.get("subiuNivel"));
        resultado.put("nivelAtual", infoProgresso.get("nivelAtual"));
        resultado.put("pontosCategoria", infoProgresso.get("pontosMaestria"));
        resultado.put("pecasDesbloqueadas", infoProgresso.get("pecasDesbloqueadas"));

        return resultado;
    }

    /**
     * Verifica se o usuário já respondeu uma notícia específica.
     */
    private boolean jaRespondeuNoticia(Long usuarioId, Long noticiaId) {
        return respostaRepository.existsByUsuarioAndNoticia(usuarioId, noticiaId);
    }

    /**
     * Atualiza o progresso do usuário em uma categoria específica.
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @param pontosGanhos pontos a adicionar (pode ser negativo)
     * @return Map com informações sobre o progresso atualizado
     */
    private Map<String, Object> atualizarProgressoCategoria(Long usuarioId, Long categoriaId, int pontosGanhos) {
        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);

        // Se não existe progresso, criar um novo
        if (progresso == null) {
            progresso = new ProgressoCategoria(null, usuarioId, categoriaId, 0, 0, new ArrayList<>());
            progressoRepository.save(progresso);
        }

        // Salvar nível anterior para verificar se subiu de nível
        int nivelAnterior = progresso.getNivelAtual();

        // Atualizar pontos (não pode ficar negativo)
        int novosPontos = progresso.getPontosMaestria() + pontosGanhos;
        if (novosPontos < 0) novosPontos = 0;
        progresso.setPontosMaestria(novosPontos);

        // Calcular novo nível baseado nos pontos
        int novoNivel = pontuacaoService.calcularNivel(novosPontos);
        progresso.setNivelAtual(novoNivel);

        // Verificar se subiu de nível e desbloquear peça
        boolean subiuNivel = novoNivel > nivelAnterior;
        if (subiuNivel && !progresso.getPecasDesbloqueadas().contains(novoNivel)) {
            progresso.getPecasDesbloqueadas().add(novoNivel);
        }

        // Salvar progresso atualizado
        progressoRepository.update(progresso);

        // Retornar informações do progresso
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("subiuNivel", subiuNivel);
        resultado.put("nivelAtual", novoNivel);
        resultado.put("pontosMaestria", novosPontos);
        resultado.put("pecasDesbloqueadas", progresso.getPecasDesbloqueadas());

        return resultado;
    }

    /**
     * Verifica e atribui conquistas ao usuário baseado em seu progresso.
     * (Implementação futura - deixar para próximas iterações)
     */
    private void verificarConquistas(Long usuarioId) {
        // TODO: Implementar lógica de conquistas
        // Exemplos:
        // - Primeira resposta correta
        // - 10 respostas corretas seguidas
        // - Completar todas as notícias de uma categoria
        // - Atingir nível máximo em uma categoria
        // etc.
    }

    /**
     * Obtém uma notícia aleatória de uma categoria específica que o usuário ainda não respondeu.
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria escolhida
     * @return notícia aleatória da categoria não respondida, ou null se já respondeu todas
     */
    public Noticia obterNoticiaAleatoriaDaCategoria(Long usuarioId, Long categoriaId) {
        List<Noticia> noticiasDaCategoria = noticiaRepository.findByCategoria(categoriaId);
        List<Resposta> respostasUsuario = respostaRepository.findByUsuario(usuarioId);

        // Filtrar notícias não respondidas
        Set<Long> noticiasRespondidas = new HashSet<>();
        for (Resposta resposta : respostasUsuario) {
            noticiasRespondidas.add(resposta.getNoticiaId());
        }

        List<Noticia> noticiasDisponiveis = new ArrayList<>();
        for (Noticia noticia : noticiasDaCategoria) {
            if (!noticiasRespondidas.contains(noticia.getId())) {
                noticiasDisponiveis.add(noticia);
            }
        }

        if (noticiasDisponiveis.isEmpty()) {
            return null;
        }

        // Retornar notícia aleatória
        Random random = new Random();
        return noticiasDisponiveis.get(random.nextInt(noticiasDisponiveis.size()));
    }
}

