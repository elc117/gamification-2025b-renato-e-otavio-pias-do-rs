package com.renato.controller.dto;

import java.util.List;

/**
 * DTO para informações de progresso em uma categoria.
 */
public class ProgressoCategoriaDTO {
    private boolean subiuNivel;
    private int nivelAtual;
    private int pontosMaestria;
    private List<Integer> pecasDesbloqueadas;
    private double percentualProgresso;
    private long acertosUnicos;
    private long totalNoticias;
    private long noticiasFaltantes;

    public ProgressoCategoriaDTO() {
    }

    public ProgressoCategoriaDTO(boolean subiuNivel, int nivelAtual, int pontosMaestria,
                                List<Integer> pecasDesbloqueadas, double percentualProgresso,
                                long acertosUnicos, long totalNoticias, long noticiasFaltantes) {
        this.subiuNivel = subiuNivel;
        this.nivelAtual = nivelAtual;
        this.pontosMaestria = pontosMaestria;
        this.pecasDesbloqueadas = pecasDesbloqueadas;
        this.percentualProgresso = percentualProgresso;
        this.acertosUnicos = acertosUnicos;
        this.totalNoticias = totalNoticias;
        this.noticiasFaltantes = noticiasFaltantes;
    }

    public boolean isSubiuNivel() {
        return subiuNivel;
    }

    public void setSubiuNivel(boolean subiuNivel) {
        this.subiuNivel = subiuNivel;
    }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(int nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public int getPontosMaestria() {
        return pontosMaestria;
    }

    public void setPontosMaestria(int pontosMaestria) {
        this.pontosMaestria = pontosMaestria;
    }

    public List<Integer> getPecasDesbloqueadas() {
        return pecasDesbloqueadas;
    }

    public void setPecasDesbloqueadas(List<Integer> pecasDesbloqueadas) {
        this.pecasDesbloqueadas = pecasDesbloqueadas;
    }

    public double getPercentualProgresso() {
        return percentualProgresso;
    }

    public void setPercentualProgresso(double percentualProgresso) {
        this.percentualProgresso = percentualProgresso;
    }

    public long getAcertosUnicos() {
        return acertosUnicos;
    }

    public void setAcertosUnicos(long acertosUnicos) {
        this.acertosUnicos = acertosUnicos;
    }

    public long getTotalNoticias() {
        return totalNoticias;
    }

    public void setTotalNoticias(long totalNoticias) {
        this.totalNoticias = totalNoticias;
    }

    public long getNoticiasFaltantes() {
        return noticiasFaltantes;
    }

    public void setNoticiasFaltantes(long noticiasFaltantes) {
        this.noticiasFaltantes = noticiasFaltantes;
    }
}
