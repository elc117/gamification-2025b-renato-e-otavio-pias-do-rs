package com.renato.service;

import com.renato.controller.dto.ResultadoJogadaDTO;
import com.renato.controller.dto.ResultadoRespostaDTO;
import com.renato.controller.dto.ProgressoCategoriaDTO;
import com.renato.controller.dto.ConquistaInfoDTO;
import com.renato.model.Conquista;
import com.renato.model.Noticia;
import com.renato.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service orquestrador principal do jogo.
 * Coordena os diferentes services especializados para processar uma jogada completa.
 */
public class JogoOrquestradorService {
    private final NoticiaSelecaoService noticiaSelecaoService;
    private final RespostaProcessorService respostaProcessorService;
    private final ProgressoCategoriaService progressoCategoriaService;
    private final ConquistaVerificadorService conquistaVerificadorService;
    private final UsuarioService usuarioService;

    // construtor padrão
    public JogoOrquestradorService() {
        this(new NoticiaSelecaoService(),
             new RespostaProcessorService(),
             new ProgressoCategoriaService(),
             new ConquistaVerificadorService(),
             new UsuarioService());
    }

    // Construtor com Injeção de Dependências
    public JogoOrquestradorService(NoticiaSelecaoService noticiaSelecaoService,
                                   RespostaProcessorService respostaProcessorService,
                                   ProgressoCategoriaService progressoCategoriaService,
                                   ConquistaVerificadorService conquistaVerificadorService,
                                   UsuarioService usuarioService) {
        this.noticiaSelecaoService = noticiaSelecaoService;
        this.respostaProcessorService = respostaProcessorService;
        this.progressoCategoriaService = progressoCategoriaService;
        this.conquistaVerificadorService = conquistaVerificadorService;
        this.usuarioService = usuarioService;
    }

    /**
     * Processa uma resposta completa do usuário, coordenando todos os services.
     * 
     * @param usuarioId ID do usuário
     * @param noticiaId ID da notícia
     * @param respostaUsuario Resposta do usuário (true/false)
     * @return ResultadoJogadaDTO completo com todas as informações da jogada
     */
    public ResultadoJogadaDTO processarResposta(Long usuarioId, Long noticiaId, boolean respostaUsuario) {
        // 1. Buscar notícia
        Noticia noticia = noticiaSelecaoService.buscarPorId(noticiaId);
        if (noticia == null) {
            throw new IllegalArgumentException("Notícia não encontrada");
        }

        // 2. Processar resposta e atualizar pontuação
        ResultadoRespostaDTO resultadoResposta = respostaProcessorService.processarResposta(
            usuarioId, noticia, respostaUsuario
        );

        // 3. Atualizar progresso na categoria
        ProgressoCategoriaDTO infoProgresso = progressoCategoriaService.atualizarProgresso(
            usuarioId, noticia.getCategoriaId()
        );

        // 4. Verificar conquistas
        List<Conquista> conquistasDesbloqueadas = conquistaVerificadorService.verificarConquistas(usuarioId);

        // 5. Montar resultado final
        return montarResultadoCompleto(resultadoResposta, infoProgresso, conquistasDesbloqueadas);
    }

    /**
     * Obtém uma notícia aleatória de uma categoria.
     */
    public Noticia obterNoticiaAleatoriaDaCategoria(Long usuarioId, Long categoriaId) {
        return noticiaSelecaoService.obterNoticiaAleatoria(usuarioId, categoriaId);
    }

    /**
     * Monta o resultado completo combinando informações de todos os services.
     */
    private ResultadoJogadaDTO montarResultadoCompleto(ResultadoRespostaDTO resultadoResposta,
                                                        ProgressoCategoriaDTO infoProgresso,
                                                        List<Conquista> conquistasDesbloqueadas) {
        Usuario usuario = resultadoResposta.getUsuario();
        
        List<ConquistaInfoDTO> conquistasInfo = conquistasDesbloqueadas != null && !conquistasDesbloqueadas.isEmpty()
            ? conquistasDesbloqueadas.stream()
                .map(c -> new ConquistaInfoDTO(c.getNome(), c.getDescricao()))
                .collect(Collectors.toList())
            : new ArrayList<>();

        return new ResultadoJogadaDTO(
            resultadoResposta.isAcertou(),
            resultadoResposta.getPontosGanhos(),
            resultadoResposta.getExplicacao(),
            infoProgresso.isSubiuNivel(),
            infoProgresso.getNivelAtual(),
            infoProgresso.getPecasDesbloqueadas(),
            infoProgresso.getPercentualProgresso(),
            infoProgresso.getAcertosUnicos(),
            infoProgresso.getTotalNoticias(),
            infoProgresso.getNoticiasFaltantes(),
            usuario.getNivel(),
            usuario.getTituloAtual(),
            usuario.getPontuacaoTotal(),
            resultadoResposta.isSubiuNivelGlobal(),
            resultadoResposta.isMudouTitulo(),
            usuarioService.calcularPontosParaProximoNivel(usuario),
            usuario.getTotalTentativas(),
            usuario.getAcertosTotais(),
            usuario.getTaxaAcerto(),
            conquistasInfo
        );
    }
}
