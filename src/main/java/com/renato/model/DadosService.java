// Métodos para controlar lógica com banco de dados.

package com.renato.model;

import java.util.*;
import com.renato.repository.*;

public class DadosService {
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final NoticiaRepository noticiaRepository;
    private final RespostaRepository respostaRepository;
    private final ConquistaRepository conquistaRepository;
    private final ConquistaUsuarioRepository conquistaUsuarioRepository;
    private final ProgressoCategoriaRepository progressoCategoriaRepository;

    public DadosService() {
        this.usuarioRepository = new UsuarioRepository();
        this.categoriaRepository = new CategoriaRepository();
        this.noticiaRepository = new NoticiaRepository();
        this.respostaRepository = new RespostaRepository();
        this.conquistaRepository = new ConquistaRepository();
        this.conquistaUsuarioRepository = new ConquistaUsuarioRepository();
        this.progressoCategoriaRepository = new ProgressoCategoriaRepository();
    }

    // ========== Métodos de Usuário ==========
    public Usuario encontrarUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.update(usuario);
    }

    public void deletarUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario encontrarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // ========== Métodos de Categoria ==========
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria encontrarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId);
    }

    public Categoria encontrarCategoriaPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    public Categoria salvarCategoria(Categoria categoria) {
        if (categoria != null) {
            categoria.setId(null);
        }
        return categoriaRepository.save(categoria);
    }

    public void atualizarCategoria(Categoria categoria) {
        categoriaRepository.update(categoria);
    }

    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria != null) {
            categoriaRepository.delete(categoria);
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }

    // ========== Métodos de Notícia ==========
    public List<Noticia> listarNoticias() {
        return noticiaRepository.findAll();
    }

    public Noticia encontrarNoticia(Long id) {
        return noticiaRepository.findById(id);
    }

    public Noticia salvarNoticia(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

    public List<Noticia> obtemNoticiasDaCategoria(Long categoriaId) {
        return noticiaRepository.findByCategoria(categoriaId);
    }

    public List<Noticia> obtemNoticiasNaoRespondidas(Long usuarioId) {
        return noticiaRepository.findNaoRespondidasPorUsuario(usuarioId);
    }

    // ========== Métodos de Resposta ==========
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

    // ========== Métodos de Conquista ==========
    public List<Conquista> listarConquistas() {
        return conquistaRepository.findAll();
    }

    public Conquista obtemConquista(Long id) {
        return conquistaRepository.findById(id);
    }

    public Conquista salvarConquista(Conquista conquista) {
        return conquistaRepository.save(conquista);
    }

    public List<ConquistaUsuario> obtemConquistasUsuario(Long usuarioId) {
        return conquistaUsuarioRepository.findByUsuario(usuarioId);
    }

    public ConquistaUsuario salvarConquistaUsuario(ConquistaUsuario conquistaUsuario) {
        return conquistaUsuarioRepository.save(conquistaUsuario);
    }

    // ========== Métodos de Progresso ==========
    public List<ProgressoCategoria> obtemProgressosUsuario(Long usuarioId) {
        return progressoCategoriaRepository.findByUsuario(usuarioId);
    }

    public ProgressoCategoria obtemProgressoCategoria(Long usuarioId, Long categoriaId) {
        return progressoCategoriaRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);
    }

    public ProgressoCategoria salvarProgressoCategoria(ProgressoCategoria progresso) {
        return progressoCategoriaRepository.save(progresso);
    }

    public ProgressoCategoria atualizarProgressoCategoria(ProgressoCategoria progresso) {
        return progressoCategoriaRepository.update(progresso);
    }

    /**
     * Calcula estatísticas detalhadas do usuário em uma categoria específica.
     * Agora com taxa de acerto REAL (salvamos acertos E erros).
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return Map com estatísticas da categoria
     */
    public Map<String, Object> obtemEstatisticasCategoria(Long usuarioId, Long categoriaId) {
        Map<String, Object> estatisticas = new HashMap<>();

        // Total de notícias na categoria
        long totalNoticias = noticiaRepository.countByCategoria(categoriaId);

        // Acertos únicos do usuário nesta categoria
        long acertosUnicos = respostaRepository.countAcertosByUsuarioAndCategoria(usuarioId, categoriaId);

        // Buscar progresso para obter total de tentativas
        ProgressoCategoria progresso = progressoCategoriaRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);
        long tentativasNaCategoria = progresso != null ? progresso.getTotalTentativas() : 0;

        // Calcular taxa de acerto real (baseada nas tentativas reais, não apenas acertos salvos)
        double taxaAcerto = tentativasNaCategoria > 0
            ? (acertosUnicos * 100.0 / tentativasNaCategoria)
            : 0;

        // Calcular progresso
        double percentualProgresso = totalNoticias > 0
            ? (acertosUnicos * 100.0 / totalNoticias)
            : 0;

        // Calcular erros
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
