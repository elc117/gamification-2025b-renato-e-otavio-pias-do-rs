package com.renato.service;

import com.renato.model.*;
import com.renato.repository.*;
import java.util.*;

/**
 * serviço responsável pela lógica de recompensas visuais (sistema de peças/quebra-cabeça).
 * gerencia o desbloqueio de partes da imagem conforme o usuário progride nas categorias.
 */
public class RecompensaService {
    private final ProgressoCategoriaRepository progressoRepository;
    private final CategoriaRepository categoriaRepository;

    public RecompensaService() {
        this.progressoRepository = new ProgressoCategoriaRepository();
        this.categoriaRepository = new CategoriaRepository();
    }

    /**
     * calcula o progresso percentual da imagem desbloqueada em uma categoria.
     * Sistema de peças por quadrante:
     * - 25% de progresso: desbloqueia peça 1 (quadrante superior esquerdo)
     * - 50% de progresso: desbloqueia peça 2 (quadrante superior direito)
     * - 75% de progresso: desbloqueia peça 3 (quadrante inferior esquerdo)
     * - 100% de progresso: desbloqueia peça 4 (quadrante inferior direito) - imagem completa!
     *
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return Map com informações do progresso visual (percentual, peças desbloqueadas, etc)
     */
    public Map<String, Object> calcularProgressoImagem(Long usuarioId, Long categoriaId) {
        ProgressoCategoria progresso = progressoRepository.findByUsuarioAndCategoria(usuarioId, categoriaId);
        Categoria categoria = categoriaRepository.findById(categoriaId);

        Map<String, Object> resultado = new HashMap<>();

        if (progresso == null || categoria == null) {
            resultado.put("percentualDesbloqueado", 0);
            resultado.put("pecasDesbloqueadas", new ArrayList<>());
            resultado.put("totalPecas", 4);
            resultado.put("imagemCompleta", false);
            resultado.put("descricaoPecas", obterDescricaoPecas(new ArrayList<>()));
            return resultado;
        }

        List<Integer> pecasDesbloqueadas = progresso.getPecasDesbloqueadas();
        int numPecasDesbloqueadas = pecasDesbloqueadas != null ? pecasDesbloqueadas.size() : 0;

        // calcula percentual baseado em 4 peças (os níveis vão de 0 a 4, mas só níveis 1-4 desbloqueiam peças)
        int totalPecas = 4;
        double percentual = totalPecas > 0 ? (numPecasDesbloqueadas * 100.0 / totalPecas) : 0;
        boolean imagemCompleta = numPecasDesbloqueadas >= totalPecas;

        resultado.put("percentualDesbloqueado", Math.round(percentual * 100.0) / 100.0); // 2 casas decimais
        resultado.put("pecasDesbloqueadas", pecasDesbloqueadas != null ? pecasDesbloqueadas : new ArrayList<>());
        resultado.put("totalPecas", totalPecas);
        resultado.put("imagemCompleta", imagemCompleta);
        resultado.put("caminhoImagemCompleta", categoria.getCaminhoImagemCompleta());
        resultado.put("descricaoPecas", obterDescricaoPecas(pecasDesbloqueadas));

        return resultado;
    }

    /**
     * Retorna descrição detalhada de cada peça por quadrante.
     *
     * @param pecasDesbloqueadas lista de peças já desbloqueadas
     * @return Map com descrição de cada quadrante
     */
    private Map<Integer, String> obterDescricaoPecas(List<Integer> pecasDesbloqueadas) {
        Map<Integer, String> descricoes = new HashMap<>();
        descricoes.put(1, pecasDesbloqueadas != null && pecasDesbloqueadas.contains(1)
            ? "Quadrante Superior Esquerdo - Desbloqueado (25%)"
            : "Quadrante Superior Esquerdo - Bloqueado (Alcance 25%)");
        descricoes.put(2, pecasDesbloqueadas != null && pecasDesbloqueadas.contains(2)
            ? "Quadrante Superior Direito - Desbloqueado (50%)"
            : "Quadrante Superior Direito - Bloqueado (Alcance 50%)");
        descricoes.put(3, pecasDesbloqueadas != null && pecasDesbloqueadas.contains(3)
            ? "Quadrante Inferior Esquerdo - Desbloqueado (75%)"
            : "Quadrante Inferior Esquerdo - Bloqueado (Alcance 75%)");
        descricoes.put(4, pecasDesbloqueadas != null && pecasDesbloqueadas.contains(4)
            ? "Quadrante Inferior Direito - COMPLETO! (100%)"
            : "Quadrante Inferior Direito - Bloqueado (Alcance 100%)");
        return descricoes;
    }

    /**
     * verifica se uma nova peça deve ser desbloqueada com base no nível atingido.
     * 
     * @param nivelAtual nível atual do usuário na categoria
     * @param pecasDesbloqueadas lista de peças já desbloqueadas
     * @return true se uma nova peça foi desbloqueada
     */
    public boolean verificarNovaPeca(int nivelAtual, List<Integer> pecasDesbloqueadas) {
        if (pecasDesbloqueadas == null) {
            return false;
        }
        
        // verifica se o nível atual já está na lista de peças desbloqueadas
        return pecasDesbloqueadas.contains(nivelAtual);
    }

    /**
     * retorna informações sobre todas as recompensas visuais de um usuário.
     * 
     * @param usuarioId ID do usuário
     * @return lista de mapas com progresso visual de cada categoria
     */
    public List<Map<String, Object>> obterTodasRecompensasUsuario(Long usuarioId) {
        List<Categoria> todasCategorias = categoriaRepository.findAll();
        List<Map<String, Object>> recompensas = new ArrayList<>();

        for (Categoria categoria : todasCategorias) {
            Map<String, Object> progressoImagem = calcularProgressoImagem(usuarioId, categoria.getId());
            progressoImagem.put("categoriaNome", categoria.getNome());
            progressoImagem.put("categoriaId", categoria.getId());
            recompensas.add(progressoImagem);
        }

        return recompensas;
    }

    /**
     * obtém detalhes de uma peça específica desbloqueada.
     * 
     * @param categoriaId ID da categoria
     * @param nivel nível da peça
     * @return Map com informações da peça
     */
    public Map<String, Object> obterDetalhesPeca(Long categoriaId, int nivel) {
        Categoria categoria = categoriaRepository.findById(categoriaId);
        
        Map<String, Object> detalhes = new HashMap<>();
        detalhes.put("nivel", nivel);
        detalhes.put("categoriaId", categoriaId);
        detalhes.put("categoriaNome", categoria != null ? categoria.getNome() : "Desconhecida");
        
        // o frontend usa essas informações para renderizar a peça da forma correta
        return detalhes;
    }
}
