package com.renato.service;

import com.renato.model.Usuario;
import com.renato.repository.UsuarioRepository;
import java.util.List;

/**
 * Service responsável por operações relacionadas a usuários.
 * Contém toda a lógica de negócio relacionada a usuários.
 */
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

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

    /**
     * Incrementa o contador de tentativas do usuário e atualiza a taxa de acerto.
     * @param usuario usuário a atualizar
     */
    public void incrementarTentativas(Usuario usuario) {
        usuario.setTotalTentativas(usuario.getTotalTentativas() + 1);
        atualizarTaxaAcerto(usuario);
    }

    /**
     * Incrementa o contador de acertos do usuário e atualiza a taxa de acerto.
     * @param usuario usuário a atualizar
     */
    public void incrementarAcertos(Usuario usuario) {
        usuario.setAcertosTotais(usuario.getAcertosTotais() + 1);
        atualizarTaxaAcerto(usuario);
    }

    /**
     * Atualiza a taxa de acerto com base nos acertos totais e tentativas totais.
     * Taxa de acerto = (acertos_totais / total_tentativas) * 100
     * @param usuario usuário a atualizar
     */
    private void atualizarTaxaAcerto(Usuario usuario) {
        if (usuario.getTotalTentativas() > 0) {
            double taxa = Math.round((usuario.getAcertosTotais() * 100.0 / usuario.getTotalTentativas()) * 100.0) / 100.0;
            usuario.setTaxaAcerto(taxa);
        } else {
            usuario.setTaxaAcerto(0.0);
        }
    }

    /**
     * Adiciona pontos à pontuação total e atualiza o nível automaticamente.
     * @param usuario usuário a atualizar
     * @param pontos pontos a adicionar (pode ser negativo para penalidades)
     */
    public void adicionarPontos(Usuario usuario, int pontos) {
        int novaPontuacao = usuario.getPontuacaoTotal() + pontos;
        // garantir que a pontuação não fique negativa
        if (novaPontuacao < 0) {
            novaPontuacao = 0;
        }
        usuario.setPontuacaoTotal(novaPontuacao);
        atualizarNivel(usuario);
    }

    /**
     * Atualiza o nível e título do usuário baseado na pontuação total.
     * Sistema de progressão: XP para subir = 100 × nível_atual
     * Exemplo: nível 1→2 = 100 pontos, nível 2→3 = 200 pontos, nível 10→11 = 1000 pontos
     * Nível máximo: 30
     * @param usuario usuário a atualizar
     */
    private void atualizarNivel(Usuario usuario) {
        int novoNivel = calcularNivelPorPontos(usuario.getPontuacaoTotal());
        usuario.setNivel(novoNivel);
        usuario.setTituloAtual(obterTituloPorNivel(novoNivel));
    }

    /**
     * Calcula o nível baseado nos pontos totais acumulados.
     * Usa progressão aritmética: cada nível requer 100 × nível_atual pontos.
     * @param pontos pontos totais acumulados
     * @return nível correspondente
     */
    private int calcularNivelPorPontos(int pontos) {
        int nivel = 1;
        int pontosAcumulados = 0;

        // tabela pré-calculada para performance (níveis 1-30)
        while (nivel <= 30) {
            int pontosParaProximoNivel = 100 * nivel;
            if (pontosAcumulados + pontosParaProximoNivel > pontos) {
                break; // ainda não tem pontos suficientes para o próximo nível
            }
            pontosAcumulados += pontosParaProximoNivel;
            nivel++;
        }

        return nivel;
    }

    /**
     * Retorna o título baseado no nível do usuário.
     * Progressão: Reporter → Analista → Investigador → Investigador Sênior → Detetive → Detetive Master → Caçador Supremo
     * @param nivel nível do usuário
     * @return título correspondente ao nível
     */
    private String obterTituloPorNivel(int nivel) {
        if (nivel >= 30) return "Caçador Supremo";      // 46500+ pontos (4650+ acertos)
        if (nivel >= 25) return "Detetive Master";      // 32500+ pontos (3250+ acertos)
        if (nivel >= 20) return "Detetive";             // 21000+ pontos (2100+ acertos)
        if (nivel >= 15) return "Investigador Sênior";  // 12000+ pontos (1200+ acertos)
        if (nivel >= 10) return "Investigador";         // 4500+ pontos (450+ acertos)
        if (nivel >= 5) return "Analista";              // 1000+ pontos (100+ acertos)
        if (nivel >= 1) return "Repórter";              // 0+ pontos
        return "Repórter";                              // nível inicial
    }

    /**
     * Calcula quantos pontos faltam para o próximo nível.
     * @param usuario usuário a verificar
     * @return pontos necessários para o próximo nível (0 se já está no máximo)
     */
    public int calcularPontosParaProximoNivel(Usuario usuario) {
        if (usuario.getNivel() >= 30) {
            return 0; // já está no nível máximo
        }

        // calcular quantos pontos já foram gastos até o nível atual
        int pontosAcumuladosAteNivelAtual = 0;
        for (int n = 1; n < usuario.getNivel(); n++) {
            pontosAcumuladosAteNivelAtual += 100 * n;
        }

        // calcular pontos necessários para o próximo nível
        int pontosParaProximoNivel = 100 * usuario.getNivel();

        // calcular quantos pontos faltam
        int pontosGastos = usuario.getPontuacaoTotal() - pontosAcumuladosAteNivelAtual;
        return pontosParaProximoNivel - pontosGastos;
    }
}
