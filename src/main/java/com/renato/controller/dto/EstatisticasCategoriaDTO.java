package com.renato.controller.dto;

/**
 * DTO para estatísticas de uma categoria.
 */
public class EstatisticasCategoriaDTO {
    private long totalNoticias;
    private long acertosUnicos;
    private long tentativasNaCategoria;
    private long errosNaCategoria;
    private double taxaAcertoCategoria;
    private double percentualProgresso;
    private long noticiasFaltantes;

    public EstatisticasCategoriaDTO() {
    }

    public EstatisticasCategoriaDTO(long totalNoticias, long acertosUnicos, long tentativasNaCategoria,
                                   long errosNaCategoria, double taxaAcertoCategoria,
                                   double percentualProgresso, long noticiasFaltantes) {
        this.totalNoticias = totalNoticias;
        this.acertosUnicos = acertosUnicos;
        this.tentativasNaCategoria = tentativasNaCategoria;
        this.errosNaCategoria = errosNaCategoria;
        this.taxaAcertoCategoria = taxaAcertoCategoria;
        this.percentualProgresso = percentualProgresso;
        this.noticiasFaltantes = noticiasFaltantes;
    }

    public long getTotalNoticias() {
        return totalNoticias;
    }

    public void setTotalNoticias(long totalNoticias) {
        this.totalNoticias = totalNoticias;
    }

    public long getAcertosUnicos() {
        return acertosUnicos;
    }

    public void setAcertosUnicos(long acertosUnicos) {
        this.acertosUnicos = acertosUnicos;
    }

    public long getTentativasNaCategoria() {
        return tentativasNaCategoria;
    }

    public void setTentativasNaCategoria(long tentativasNaCategoria) {
        this.tentativasNaCategoria = tentativasNaCategoria;
    }

    public long getErrosNaCategoria() {
        return errosNaCategoria;
    }

    public void setErrosNaCategoria(long errosNaCategoria) {
        this.errosNaCategoria = errosNaCategoria;
    }

    public double getTaxaAcertoCategoria() {
        return taxaAcertoCategoria;
    }

    public void setTaxaAcertoCategoria(double taxaAcertoCategoria) {
        this.taxaAcertoCategoria = taxaAcertoCategoria;
    }

    public double getPercentualProgresso() {
        return percentualProgresso;
    }

    public void setPercentualProgresso(double percentualProgresso) {
        this.percentualProgresso = percentualProgresso;
    }

    public long getNoticiasFaltantes() {
        return noticiasFaltantes;
    }

    public void setNoticiasFaltantes(long noticiasFaltantes) {
        this.noticiasFaltantes = noticiasFaltantes;
    }
}
