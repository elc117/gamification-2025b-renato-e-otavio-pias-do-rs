package com.renato.model;

import java.util.*;

public class DadosService {
    private List<Usuario> usuarios;
    private List<Noticia> noticias;
    private List<Resposta> respostas;

    public DadosService(List<Usuario> usuarios, List<Noticia> noticias, List<Resposta> respostas) {
        this.usuarios = usuarios;
        this.noticias = noticias;
        this.respostas = respostas;
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
}