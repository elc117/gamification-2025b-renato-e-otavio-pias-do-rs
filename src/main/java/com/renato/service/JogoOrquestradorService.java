package com.renato.service;

import com.renato.model.Conquista;
import com.renato.model.Noticia;
import com.renato.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public JogoOrquestradorService() {
        this.noticiaSelecaoService = new NoticiaSelecaoService();
        this.respostaProcessorService = new RespostaProcessorService();
        this.progressoCategoriaService = new ProgressoCategoriaService();
        this.conquistaVerificadorService = new ConquistaVerificadorService();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Processa uma resposta completa do usuário, coordenando todos os services.
     * 
     * @param usuarioId ID do usuário
     * @param noticiaId ID da notícia
     * @param respostaUsuario Resposta do usuário (true/false)
     * @return Map completo com todas as informações da jogada
     */
    public Map<String, Object> processarResposta(Long usuarioId, Long noticiaId, boolean respostaUsuario) {
        // 1. Buscar notícia
        Noticia noticia = noticiaSelecaoService.buscarPorId(noticiaId);
        if (noticia == null) {
            throw new IllegalArgumentException("Notícia não encontrada");
        }

        // 2. Processar resposta e atualizar pontuação
        Map<String, Object> resultadoResposta = respostaProcessorService.processarResposta(
            usuarioId, noticia, respostaUsuario
        );

        // 3. Atualizar progresso na categoria
        Map<String, Object> infoProgresso = progressoCategoriaService.atualizarProgresso(
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
    private Map<String, Object> montarResultadoCompleto(Map<String, Object> resultadoResposta,
                                                        Map<String, Object> infoProgresso,
                                                        List<Conquista> conquistasDesbloqueadas) {
        Map<String, Object> resultado = new HashMap<>();
        
        // Informações da resposta
        resultado.put("acertou", resultadoResposta.get("acertou"));
        resultado.put("pontosGanhos", resultadoResposta.get("pontosGanhos"));
        resultado.put("explicacao", resultadoResposta.get("explicacao"));

        // Informações de progresso na categoria
        resultado.put("desbloqueouNovaPeca", infoProgresso.get("subiuNivel"));
        resultado.put("nivelCategoria", infoProgresso.get("nivelAtual"));
        resultado.put("pecasDesbloqueadas", infoProgresso.get("pecasDesbloqueadas"));
        resultado.put("percentualProgresso", infoProgresso.get("percentualProgresso"));
        resultado.put("acertosUnicos", infoProgresso.get("acertosUnicos"));
        resultado.put("totalNoticias", infoProgresso.get("totalNoticias"));
        resultado.put("noticiasFaltantes", infoProgresso.get("noticiasFaltantes"));

        // Informações de progresso global
        Usuario usuario = (Usuario) resultadoResposta.get("usuario");
        resultado.put("nivelGlobal", usuario.getNivel());
        resultado.put("tituloAtual", usuario.getTituloAtual());
        resultado.put("pontuacaoTotal", usuario.getPontuacaoTotal());
        resultado.put("subiuNivelGlobal", resultadoResposta.get("subiuNivelGlobal"));
        resultado.put("mudouTitulo", resultadoResposta.get("mudouTitulo"));
        resultado.put("pontosParaProximoNivel", usuarioService.calcularPontosParaProximoNivel(usuario));
        resultado.put("totalTentativas", usuario.getTotalTentativas());
        resultado.put("acertosTotais", usuario.getAcertosTotais());
        resultado.put("taxaAcerto", usuario.getTaxaAcerto());

        // Informações de conquistas
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
}
