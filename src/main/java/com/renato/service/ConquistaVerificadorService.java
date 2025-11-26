package com.renato.service;

import com.renato.model.Conquista;
import com.renato.model.ConquistaUsuario;
import com.renato.model.Usuario;
import com.renato.repository.ConquistaRepository;
import com.renato.repository.ConquistaUsuarioRepository;
import com.renato.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service responsável por verificar e atribuir conquistas aos usuários.
 */
public class ConquistaVerificadorService {
    private final ConquistaRepository conquistaRepository;
    private final ConquistaUsuarioRepository conquistaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    // construtor padrão
    public ConquistaVerificadorService() {
        this(new ConquistaRepository(), new ConquistaUsuarioRepository(), new UsuarioRepository());
    }

    // construtor com injeção de dependências
    public ConquistaVerificadorService(ConquistaRepository conquistaRepository,
                                      ConquistaUsuarioRepository conquistaUsuarioRepository,
                                      UsuarioRepository usuarioRepository) {
        this.conquistaRepository = conquistaRepository;
        this.conquistaUsuarioRepository = conquistaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Verifica e atribui conquistas ao usuário baseado em seu progresso.
     * 
     * @param usuarioId ID do usuário
     * @return Lista de conquistas recém-desbloqueadas
     */
    public List<Conquista> verificarConquistas(Long usuarioId) {
        List<Conquista> conquistasRecemDesbloqueadas = new ArrayList<>();

        List<Conquista> todasConquistas = conquistaRepository.findAll(); // buscar todas as conquistas disponíveis
        if (todasConquistas == null || todasConquistas.isEmpty()) {
            return conquistasRecemDesbloqueadas;
        }

        // buscar conquistas já desbloqueadas
        Set<Long> conquistasJaDesbloqueadas = obterConquistasDesbloqueadas(usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId); // buscar usuário e obter pontos totais
        if (usuario == null) {
            return conquistasRecemDesbloqueadas;
        }
        int pontosTotais = usuario.getPontuacaoTotal();

        // Verificar cada conquista
        for (Conquista conquista : todasConquistas) {
            if (conquistasJaDesbloqueadas.contains(conquista.getId())) {
                continue; // Já desbloqueada
            }

            if (verificarCriterio(conquista, pontosTotais)) {
                desbloquearConquista(usuarioId, conquista);
                conquistasRecemDesbloqueadas.add(conquista);
            }
        }

        return conquistasRecemDesbloqueadas;
    }

    /**
     * Obtém IDs das conquistas já desbloqueadas pelo usuário.
     */
    private Set<Long> obterConquistasDesbloqueadas(Long usuarioId) {
        List<ConquistaUsuario> conquistasUsuario = conquistaUsuarioRepository.findByUsuario(usuarioId);
        Set<Long> ids = new HashSet<>();
        for (ConquistaUsuario conqU : conquistasUsuario) {
            ids.add(conqU.getConquistaId());
        }
        return ids;
    }

    /**
     * Verifica se o usuário atende ao critério de uma conquista.
     */
    private boolean verificarCriterio(Conquista conquista, int pontosTotais) {
        if ("PONTOS_TOTAIS".equalsIgnoreCase(conquista.getTipo())) {
            return pontosTotais >= conquista.getValorRequerido();
        }
        return false;
    }

    /**
     * Desbloqueia uma conquista para o usuário.
     */
    private void desbloquearConquista(Long usuarioId, Conquista conquista) {
        ConquistaUsuario novaConquista = new ConquistaUsuario(
            null,
            usuarioId,
            conquista.getId(),
            LocalDateTime.now()
        );
        conquistaUsuarioRepository.save(novaConquista);
        System.out.println("Conquista desbloqueada: " + conquista.getNome() + " para usuário " + usuarioId);
    }

    /**
     * Obtém todas as conquistas de um usuário.
     */
    public List<ConquistaUsuario> obtemConquistasUsuario(Long usuarioId) {
        return conquistaUsuarioRepository.findByUsuario(usuarioId);
    }

    /**
     * Lista todas as conquistas disponíveis no sistema.
     */
    public List<Conquista> listarConquistas() {
        return conquistaRepository.findAll();
    }

    /**
     * Obtém uma conquista por ID.
     */
    public Conquista obtemConquista(Long id) {
        return conquistaRepository.findById(id);
    }

    /**
     * Salva uma nova conquista.
     */
    public Conquista salvarConquista(Conquista conquista) {
        return conquistaRepository.save(conquista);
    }

    /**
     * Salva uma conquista desbloqueada por um usuário.
     */
    public ConquistaUsuario salvarConquistaUsuario(ConquistaUsuario conquistaUsuario) {
        return conquistaUsuarioRepository.save(conquistaUsuario);
    }
}
