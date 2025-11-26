package com.renato.controller.dto;

import java.util.List;

/**
 * DTO para o resultado completo de uma jogada.
 */
public class ResultadoJogadaDTO {
    // Informações da resposta
    private boolean acertou;
    private int pontosGanhos;
    private String explicacao;

    // Progresso na categoria
    private boolean desbloqueouNovaPeca;
    private int nivelCategoria;
    private List<Integer> pecasDesbloqueadas;
    private double percentualProgresso;
    private long acertosUnicos;
    private long totalNoticias;
    private long noticiasFaltantes;

    // Progresso global do usuário
    private int nivelGlobal;
    private String tituloAtual;
    private int pontuacaoTotal;
    private boolean subiuNivelGlobal;
    private boolean mudouTitulo;
    private int pontosParaProximoNivel;
    private int totalTentativas;
    private int acertosTotais;
    private double taxaAcerto;

    // Conquistas desbloqueadas
    private List<ConquistaInfoDTO> conquistasDesbloqueadas;

    public ResultadoJogadaDTO() {
    }

    public ResultadoJogadaDTO(boolean acertou, int pontosGanhos, String explicacao,
                              boolean desbloqueouNovaPeca, int nivelCategoria, List<Integer> pecasDesbloqueadas,
                              double percentualProgresso, long acertosUnicos, long totalNoticias, long noticiasFaltantes,
                              int nivelGlobal, String tituloAtual, int pontuacaoTotal,
                              boolean subiuNivelGlobal, boolean mudouTitulo, int pontosParaProximoNivel,
                              int totalTentativas, int acertosTotais, double taxaAcerto,
                              List<ConquistaInfoDTO> conquistasDesbloqueadas) {
        this.acertou = acertou;
        this.pontosGanhos = pontosGanhos;
        this.explicacao = explicacao;
        this.desbloqueouNovaPeca = desbloqueouNovaPeca;
        this.nivelCategoria = nivelCategoria;
        this.pecasDesbloqueadas = pecasDesbloqueadas;
        this.percentualProgresso = percentualProgresso;
        this.acertosUnicos = acertosUnicos;
        this.totalNoticias = totalNoticias;
        this.noticiasFaltantes = noticiasFaltantes;
        this.nivelGlobal = nivelGlobal;
        this.tituloAtual = tituloAtual;
        this.pontuacaoTotal = pontuacaoTotal;
        this.subiuNivelGlobal = subiuNivelGlobal;
        this.mudouTitulo = mudouTitulo;
        this.pontosParaProximoNivel = pontosParaProximoNivel;
        this.totalTentativas = totalTentativas;
        this.acertosTotais = acertosTotais;
        this.taxaAcerto = taxaAcerto;
        this.conquistasDesbloqueadas = conquistasDesbloqueadas;
    }

    // Getters e Setters
    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public int getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(int pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    public boolean isDesbloqueouNovaPeca() {
        return desbloqueouNovaPeca;
    }

    public void setDesbloqueouNovaPeca(boolean desbloqueouNovaPeca) {
        this.desbloqueouNovaPeca = desbloqueouNovaPeca;
    }

    public int getNivelCategoria() {
        return nivelCategoria;
    }

    public void setNivelCategoria(int nivelCategoria) {
        this.nivelCategoria = nivelCategoria;
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

    public int getNivelGlobal() {
        return nivelGlobal;
    }

    public void setNivelGlobal(int nivelGlobal) {
        this.nivelGlobal = nivelGlobal;
    }

    public String getTituloAtual() {
        return tituloAtual;
    }

    public void setTituloAtual(String tituloAtual) {
        this.tituloAtual = tituloAtual;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public boolean isSubiuNivelGlobal() {
        return subiuNivelGlobal;
    }

    public void setSubiuNivelGlobal(boolean subiuNivelGlobal) {
        this.subiuNivelGlobal = subiuNivelGlobal;
    }

    public boolean isMudouTitulo() {
        return mudouTitulo;
    }

    public void setMudouTitulo(boolean mudouTitulo) {
        this.mudouTitulo = mudouTitulo;
    }

    public int getPontosParaProximoNivel() {
        return pontosParaProximoNivel;
    }

    public void setPontosParaProximoNivel(int pontosParaProximoNivel) {
        this.pontosParaProximoNivel = pontosParaProximoNivel;
    }

    public int getTotalTentativas() {
        return totalTentativas;
    }

    public void setTotalTentativas(int totalTentativas) {
        this.totalTentativas = totalTentativas;
    }

    public int getAcertosTotais() {
        return acertosTotais;
    }

    public void setAcertosTotais(int acertosTotais) {
        this.acertosTotais = acertosTotais;
    }

    public double getTaxaAcerto() {
        return taxaAcerto;
    }

    public void setTaxaAcerto(double taxaAcerto) {
        this.taxaAcerto = taxaAcerto;
    }

    public List<ConquistaInfoDTO> getConquistasDesbloqueadas() {
        return conquistasDesbloqueadas;
    }

    public void setConquistasDesbloqueadas(List<ConquistaInfoDTO> conquistasDesbloqueadas) {
        this.conquistasDesbloqueadas = conquistasDesbloqueadas;
    }
}
