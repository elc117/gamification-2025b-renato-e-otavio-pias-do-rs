package com.renato.controller.dto;

import com.renato.model.ConquistaUsuario;
import com.renato.model.ProgressoCategoria;
import com.renato.model.Usuario;
import java.util.List;

/**
 * DTO para o perfil completo de um usuário.
 */
public class PerfilUsuarioDTO {
    private Usuario usuario;
    private List<ProgressoCategoria> progressos;
    private List<ConquistaUsuario> conquistas;
    private int totalRespostas;
    private int totalAcertos;
    private double taxaAcerto;
    private int pontosAtuais;
    private int pontosFaltam;
    private int pontosProximoNivel;

    public PerfilUsuarioDTO() {
    }

    public PerfilUsuarioDTO(Usuario usuario, List<ProgressoCategoria> progressos, 
                           List<ConquistaUsuario> conquistas, int totalRespostas,
                           int totalAcertos, double taxaAcerto, int pontosAtuais,
                           int pontosFaltam, int pontosProximoNivel) {
        this.usuario = usuario;
        this.progressos = progressos;
        this.conquistas = conquistas;
        this.totalRespostas = totalRespostas;
        this.totalAcertos = totalAcertos;
        this.taxaAcerto = taxaAcerto;
        this.pontosAtuais = pontosAtuais;
        this.pontosFaltam = pontosFaltam;
        this.pontosProximoNivel = pontosProximoNivel;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ProgressoCategoria> getProgressos() {
        return progressos;
    }

    public void setProgressos(List<ProgressoCategoria> progressos) {
        this.progressos = progressos;
    }

    public List<ConquistaUsuario> getConquistas() {
        return conquistas;
    }

    public void setConquistas(List<ConquistaUsuario> conquistas) {
        this.conquistas = conquistas;
    }

    public int getTotalRespostas() {
        return totalRespostas;
    }

    public void setTotalRespostas(int totalRespostas) {
        this.totalRespostas = totalRespostas;
    }

    public int getTotalAcertos() {
        return totalAcertos;
    }

    public void setTotalAcertos(int totalAcertos) {
        this.totalAcertos = totalAcertos;
    }

    public double getTaxaAcerto() {
        return taxaAcerto;
    }

    public void setTaxaAcerto(double taxaAcerto) {
        this.taxaAcerto = taxaAcerto;
    }

    public int getPontosAtuais() {
        return pontosAtuais;
    }

    public void setPontosAtuais(int pontosAtuais) {
        this.pontosAtuais = pontosAtuais;
    }

    public int getPontosFaltam() {
        return pontosFaltam;
    }

    public void setPontosFaltam(int pontosFaltam) {
        this.pontosFaltam = pontosFaltam;
    }

    public int getPontosProximoNivel() {
        return pontosProximoNivel;
    }

    public void setPontosProximoNivel(int pontosProximoNivel) {
        this.pontosProximoNivel = pontosProximoNivel;
    }
}
