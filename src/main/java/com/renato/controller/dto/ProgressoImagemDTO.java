package com.renato.controller.dto;

import java.util.List;
import java.util.Map;

/**
 * DTO para progresso visual da imagem de uma categoria.
 */
public class ProgressoImagemDTO {
    private double percentualDesbloqueado;
    private List<Integer> pecasDesbloqueadas;
    private int totalPecas;
    private boolean imagemCompleta;
    private String caminhoImagemCompleta;
    private Map<Integer, String> descricaoPecas;

    public ProgressoImagemDTO() {
    }

    public ProgressoImagemDTO(double percentualDesbloqueado, List<Integer> pecasDesbloqueadas,
                             int totalPecas, boolean imagemCompleta, String caminhoImagemCompleta,
                             Map<Integer, String> descricaoPecas) {
        this.percentualDesbloqueado = percentualDesbloqueado;
        this.pecasDesbloqueadas = pecasDesbloqueadas;
        this.totalPecas = totalPecas;
        this.imagemCompleta = imagemCompleta;
        this.caminhoImagemCompleta = caminhoImagemCompleta;
        this.descricaoPecas = descricaoPecas;
    }

    public double getPercentualDesbloqueado() {
        return percentualDesbloqueado;
    }

    public void setPercentualDesbloqueado(double percentualDesbloqueado) {
        this.percentualDesbloqueado = percentualDesbloqueado;
    }

    public List<Integer> getPecasDesbloqueadas() {
        return pecasDesbloqueadas;
    }

    public void setPecasDesbloqueadas(List<Integer> pecasDesbloqueadas) {
        this.pecasDesbloqueadas = pecasDesbloqueadas;
    }

    public int getTotalPecas() {
        return totalPecas;
    }

    public void setTotalPecas(int totalPecas) {
        this.totalPecas = totalPecas;
    }

    public boolean isImagemCompleta() {
        return imagemCompleta;
    }

    public void setImagemCompleta(boolean imagemCompleta) {
        this.imagemCompleta = imagemCompleta;
    }

    public String getCaminhoImagemCompleta() {
        return caminhoImagemCompleta;
    }

    public void setCaminhoImagemCompleta(String caminhoImagemCompleta) {
        this.caminhoImagemCompleta = caminhoImagemCompleta;
    }

    public Map<Integer, String> getDescricaoPecas() {
        return descricaoPecas;
    }

    public void setDescricaoPecas(Map<Integer, String> descricaoPecas) {
        this.descricaoPecas = descricaoPecas;
    }
}
