// Métodos para controlar lógica.

package com.renato.model;

import java.util.*;

public class DadosService {
    private List<Usuario> usuarios;
    private List<Categoria> categorias;
    private List<Noticia> noticias;
    private List<Resposta> respostas;
    private List<ProgressoCategoria> progressos;
    private List<ConquistaUsurario> conquistasUsuario;

    public DadosService(List<Usuario> usuarios, List<Categoria> categorias, List<Noticia> noticias, List<Resposta> respostas, 
            List<ProgressoCategoria> progressos, List<ConquistaUsurario> conquistasUsuario) {
        this.usuarios = usuarios;
        this.categorias = categorias;
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
        Usuario encontrado = null;
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getId() == id) {
                encontrado = u;
                break;
            }
        }
        return encontrado;
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

    public Categoria encontrarCategoria(Long categoriaId) {
        for (int i = 0; i < categorias.size(); i++) {
            Categoria c = categorias.get(i);
            if (c.getId() == categoriaId) {
                return c;
            }
        }
        return null;
    }

    public ProgressoCategoria obtemProgressoCategoria(Long categoriaId, Long usuarioId) {
       for (int i = 0; i < progressos.size(); i++) {
           ProgressoCategoria p = progressos.get(i);
           if (p.getCategoriaId() == categoriaId && p.getUsuarioId() == usuarioId) {
               return p;
           }
       }
       return null;
    }
    
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