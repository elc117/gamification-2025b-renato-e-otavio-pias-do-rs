package com.renato.service;

import com.renato.model.Noticia;
import com.renato.repository.NoticiaRepository;
import java.util.List;

/**
 * Service responsável por operações relacionadas a notícias.
 */
public class NoticiaService {
    private final NoticiaRepository noticiaRepository;

    public NoticiaService() {
        this.noticiaRepository = new NoticiaRepository();
    }

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

    public void atualizarNoticia(Noticia noticia) {
        noticiaRepository.update(noticia);
    }

    public void deletarNoticia(Noticia noticia) {
        noticiaRepository.delete(noticia);
    }
}
