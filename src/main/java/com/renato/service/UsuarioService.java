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
