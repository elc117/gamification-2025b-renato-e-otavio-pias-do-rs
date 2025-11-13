package com.renato.service;

import com.renato.model.*;
import com.renato.repository.*;
import java.util.*;

/**
 * serviço responsável pela lógica principal do jogo.
 * processa respostas, calcula pontos, atualiza progresso e verifica conquistas.
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

        // 2. verificar se o usuário já acertou esta notícia
        if (jaAcertouNoticia(usuarioId, noticiaId)) {
            throw new IllegalStateException("Usuário já acertou esta notícia");
        }

        // 3. verificar se a resposta está correta
        boolean acertou = (respostaUsuario == noticia.isEhVerdadeira());

        // 4. calcular pontos ganhos
        int pontosGanhos = pontuacaoService.calcularPontos(acertou);

        // 5. atualizar pontuação global e nível do usuário
        Usuario usuario = usuarioRepository.findById(usuarioId);
        int nivelAnterior = usuario.getNivel();
        String tituloAnterior = usuario.getTituloAtual();

        // incrementar tentativas sempre (certo ou errado)
        usuario.incrementarTentativas();

        if (acertou && usuario != null) {
            usuario.adicionarPontos(pontosGanhos);
        }
        
        usuarioRepository.update(usuario);

        boolean subiuNivelGlobal = usuario.getNivel() > nivelAnterior;
        boolean mudouTitulo = !Objects.equals(tituloAnterior, usuario.getTituloAtual());

        // 6. salvar a resposta no banco de dados apenas se acertou
        if (acertou) {
            Resposta resposta = new Resposta(null, usuarioId, noticiaId, respostaUsuario, acertou, pontosGanhos);
            respostaRepository.save(resposta);
        }

        // 7. atualizar progresso na categoria
        Map<String, Object> infoProgresso = atualizarProgressoCategoria(usuarioId, noticia.getCategoriaId(), pontosGanhos);

        // 8. verificar e atribuir conquistas (se houver)
        verificarConquistas(usuarioId);

        // 9. montar e retornar resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("acertou", acertou);
        resultado.put("pontosGanhos", pontosGanhos);
        resultado.put("explicacao", noticia.getExplicacao());

        // Informações de progresso na categoria
        resultado.put("subiuNivelCategoria", infoProgresso.get("subiuNivel"));
        resultado.put("nivelCategoria", infoProgresso.get("nivelAtual"));
        resultado.put("pontosCategoria", infoProgresso.get("pontosMaestria"));
        resultado.put("pecasDesbloqueadas", infoProgresso.get("pecasDesbloqueadas"));

        // Informações de progresso global do usuário
        resultado.put("nivelGlobal", usuario.getNivel());
        resultado.put("tituloAtual", usuario.getTituloAtual());
        resultado.put("pontuacaoTotal", usuario.getPontuacaoTotal());
        resultado.put("subiuNivelGlobal", subiuNivelGlobal);
        resultado.put("mudouTitulo", mudouTitulo);
        resultado.put("pontosParaProximoNivel", usuario.pontosParaProximoNivel());

        return resultado;
    }

    /**
     * verifica se o usuário já acertou uma notícia específica.
     * as notícias erradas podem ser tentadas novamente.
     */
    private boolean jaAcertouNoticia(Long usuarioId, Long noticiaId) {
        return respostaRepository.existsByUsuarioAndNoticia(usuarioId, noticiaId);
    }

    /**
     * atualiza o progresso do usuário em uma categoria específica.
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @param pontosGanhos pontos a adicionar
     * @return Map com informações sobre o progresso atualizado
     */
    private Map<String, Object> atualizarProgressoCategoria(Long usuarioId, Long categoriaId, int pontosGanhos) {
        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);

        // se não existe progresso, criar um novo
        if (progresso == null) {
            progresso = new ProgressoCategoria(null, usuarioId, categoriaId, 0, 0, new ArrayList<>());
            progressoRepository.save(progresso);
        }

        // salvar nível anterior para verificar se subiu de nível
        int nivelAnterior = progresso.getNivelAtual();

        // atualizar pontos (eles não podem ficar negativos)
        int novosPontos = progresso.getPontosMaestria() + pontosGanhos;
        if (novosPontos < 0) novosPontos = 0;
        progresso.setPontosMaestria(novosPontos);

        // calcular novo nível baseado nos pontos
        int novoNivel = pontuacaoService.calcularNivel(novosPontos);
        progresso.setNivelAtual(novoNivel);

        // verificar se subiu de nível e desbloquear peça
        // o nível 0 não desbloqueia peça (começa com 0%)
        // nível 1 = peça 1, nível 2 = peça 2, nível 3 = peça 3, nível 4 = peça 4
        boolean subiuNivel = novoNivel > nivelAnterior;
        if (subiuNivel && novoNivel > 0) {
            int numeroPeca = novoNivel; // nível 1 = peça 1, nível 2 = peça 2, etc
            if (!progresso.getPecasDesbloqueadas().contains(numeroPeca)) {
                progresso.getPecasDesbloqueadas().add(numeroPeca);
            }
        }

        // salvar o progresso atualizado
        progressoRepository.update(progresso);

        // retornar informações do progresso
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("subiuNivel", subiuNivel);
        resultado.put("nivelAtual", novoNivel);
        resultado.put("pontosMaestria", novosPontos);
        resultado.put("pecasDesbloqueadas", progresso.getPecasDesbloqueadas());

        return resultado;
    }

    /**
     * verifica e atribui conquistas ao usuário baseado em seu progresso.
     * analisa todas as conquistas disponíveis e verifica se o usuário atende aos critérios.
     */
    private void verificarConquistas(Long usuarioId) {
        // 1. buscar todas as conquistas disponíveis no sistema
        List<Conquista> todasConquistas = conquistaRepository.findAll();
        if (todasConquistas == null || todasConquistas.isEmpty()) {
            return; // não há conquistas cadastradas
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
                System.out.println("Conquista desbloqueada: " + conquista.getNome() + " para usuário " + usuarioId);
            }
        }
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
     * notícias erradas podem aparecer novamente.
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria escolhida
     * @return notícia aleatória da categoria não acertada, ou null se já acertou todas
     */
    public Noticia obterNoticiaAleatoriaDaCategoria(Long usuarioId, Long categoriaId) {
        List<Noticia> noticiasDaCategoria = noticiaRepository.findByCategoria(categoriaId);
        List<Resposta> respostasUsuario = respostaRepository.findByUsuario(usuarioId);

        // filtrar notícias já acertadas (apenas respostas corretas estão salvas)
        Set<Long> noticiasAcertadas = new HashSet<>();
        for (Resposta resposta : respostasUsuario) {
            noticiasAcertadas.add(resposta.getNoticiaId());
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