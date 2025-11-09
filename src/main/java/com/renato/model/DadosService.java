// Métodos para controlar lógica.

package com.renato.model;

import java.util.*;
import com.renato.repository.UsuarioRepository;
import com.renato.repository.CategoriaRepository;

public class DadosService {
    private UsuarioRepository usuarioRepository;
    private CategoriaRepository categoriaRepository;
    private List<Noticia> noticias;
    private List<Resposta> respostas;
    private List<ProgressoCategoria> progressos;
    private List<ConquistaUsurario> conquistasUsuario;

    public DadosService(List<Noticia> noticias, List<Resposta> respostas, 
            List<ProgressoCategoria> progressos, List<ConquistaUsurario> conquistasUsuario) {
        this.usuarioRepository = new UsuarioRepository();
        this.categoriaRepository = new CategoriaRepository();
        this.noticias = noticias;
        this.respostas = respostas;
        this.progressos = progressos;
        this.conquistasUsuario = conquistasUsuario;
    }

    public int obtemTotalRespostasUsuario(Long usuarioId) {
        int total = 0;
        for (int i = 0; i < respostas.size(); i++) {
            Resposta r = respostas.get(i);
            if (r.getUsuarioId() == usuarioId) {
                total++;
            }
        }
        return total;
    }

    public int obtemTotalAcertosUsuario(Long usuarioId) {
        int total = 0;
        for (int i = 0; i < respostas.size(); i++) {
            Resposta r = respostas.get(i);
            if (r.getUsuarioId() == usuarioId && r.isEstaCorreta()) {
                total++;
            }
        }
        return total;
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
    
    public List<Noticia> obtemNoticiasNaoRespondidas(Long usuarioId) {
        List<Long> noticiasRespondidas = new ArrayList<>();
        for (int i = 0; i < respostas.size(); i++) {
            Resposta r = respostas.get(i);
            if (r.getUsuarioId() == usuarioId) {
                noticiasRespondidas.add(r.getNoticiaId());
            }
        }

        List<Noticia> noticiasDisponiveis = new ArrayList<>();
        for (int i = 0; i < noticias.size(); i++) {
            Noticia n = noticias.get(i);
            boolean jaRespondeu = false;

            for (int j = 0; j < noticiasRespondidas.size(); j++) {
                if (n.getId() == noticiasRespondidas.get(j)) {
                    jaRespondeu = true;
                    break;
                }
            }

            if (!jaRespondeu) {
                noticiasDisponiveis.add(n);
            }
        }

        return noticiasDisponiveis;
    }

    public Noticia encontrarNoticia(Long id) {
        Noticia noticia = null;
        for (int i = 0; i < noticias.size(); i++) {
            Noticia n = noticias.get(i);
            if (n.getId() == id) {
                noticia = n;
                break;
            }
        }
        return noticia;
    }

    public boolean jaRespondeuNoticia(Long usuarioId, Long noticiaId) {
        for (int i = 0; i < respostas.size(); i++) {
            Resposta r = respostas.get(i);
            if (r.getUsuarioId() == usuarioId && r.getNoticiaId() == noticiaId) {
                return true;
            }
        }
        return false;
    }

    public List<Resposta> obtemRespostasUsuario(Long usuarioId) {
        List<Resposta> respostasUsuario = new ArrayList<>();
        for (int i = 0; i < respostas.size(); i++) {
            Resposta r = respostas.get(i);
            if (r.getUsuarioId() == usuarioId) {
                respostasUsuario.add(r);
            }
        }
        return respostasUsuario;
    }

    public List<ProgressoCategoria> obtemProgressosUsuario(Long usuarioId) {
        List<ProgressoCategoria> progressosUsuario = new ArrayList<>();
        for (int i = 0; i < progressos.size(); i++) {
            ProgressoCategoria p = progressos.get(i);
            if (p.getUsuarioId() == usuarioId) {
                progressosUsuario.add(p);
            }
        }
        return progressosUsuario;
    }

    public List<ConquistaUsurario> obtemConquistasUsuario(Long usuarioId) {
        List<ConquistaUsurario> conquistasUsuarioLista = new ArrayList<>();
        for (int i = 0; i < conquistasUsuario.size(); i++) {
            ConquistaUsurario conqU = conquistasUsuario.get(i);
            if (conqU.getUsuarioId() == usuarioId) {
                conquistasUsuarioLista.add(conqU);
            }
        }
        return conquistasUsuarioLista;
    }

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
        // o banco de dados ignora o id informado e gera ele mesmo
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
        }
        else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }

    /*public ProgressoCategoria obtemProgressoCategoria(Long categoriaId, Long usuarioId) {
       return categoriaRepository.obtemProgressoCategoria(usuarioId, categoriaId);
    }*/
    
    public List<Noticia> obtemNoticiasDaCategoria(Long categoriaId) {
        List<Noticia> noticiasDaCategoria = new ArrayList<>();
        for (int i = 0; i < noticias.size(); i++) {
            Noticia n = noticias.get(i);
            if (n.getCategoriaId().equals(categoriaId)) {
                noticiasDaCategoria.add(n);
            }
        }
        return noticiasDaCategoria;
    }

    public int calcularNivel(int pontos) {
        if (pontos >= 1000) return 6;
        if (pontos >= 700) return 5;
        if (pontos >= 450) return 4;
        if (pontos >= 250) return 3;
        if (pontos >= 100) return 2;
        if (pontos >= 50) return 1;
        return 0;
    }
}