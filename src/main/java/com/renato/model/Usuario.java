package com.renato.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int nivel;

    @Column(name = "pontuacao_total")
    private int pontuacaoTotal;

    @Column(name = "titulo_atual")
    private String tituloAtual;

    @Column(name = "total_tentativas", nullable = false)
    private int totalTentativas = 0;

    @Column(name = "acertos_totais", nullable = false)
    private int acertosTotais = 0;

    @Column(name = "taxa_acerto", nullable = false)
    private double taxaAcerto = 0.0;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, int nivel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nivel = nivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public String getTituloAtual() {
        return tituloAtual;
    }

    public void setTituloAtual(String tituloAtual) {
        this.tituloAtual = tituloAtual;
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

    public void incrementarTentativas() {
        this.totalTentativas++;
        atualizarTaxaAcerto();
    }

    public void incrementarAcertos() {
        this.acertosTotais++;
        atualizarTaxaAcerto();
    }

    /**
     * Atualiza a taxa de acerto com base nos acertos totais e tentativas totais.
     * Taxa de acerto = (acertos_totais / total_tentativas) * 100
     */
    private void atualizarTaxaAcerto() {
        if (this.totalTentativas > 0) {
            this.taxaAcerto = Math.round((this.acertosTotais * 100.0 / this.totalTentativas) * 100.0) / 100.0;
        } else {
            this.taxaAcerto = 0.0;
        }
    }

    /**
     * adiciona pontos à pontuação total e atualiza o nível automaticamente.
     * @param pontos pontos a adicionar (pode ser negativo para penalidades)
     */
    public void adicionarPontos(int pontos) {
        this.pontuacaoTotal += pontos;
        // garantir que a pontuação não fique negativa
        if (this.pontuacaoTotal < 0) {
            this.pontuacaoTotal = 0;
        }
        atualizarNivel();
    }

    /**
     * atualiza o nível e título do usuário baseado na pontuação total.
     * sistema de progressão: XP para subir = 100 × nível_atual
     * exemplo: nível 1→2 = 100 pontos, nível 2→3 = 200 pontos, nível 10→11 = 1000 pontos
     * nível máximo: 30
     */
    private void atualizarNivel() {
        // calcular nível baseado no XP total acumulado
        // fórmula: XP necessário = soma de (100 × n) para n de 1 até nível
        int novoNivel = calcularNivelPorPontos(this.pontuacaoTotal);

        this.nivel = novoNivel;
        this.tituloAtual = obterTituloPorNivel(novoNivel);
    }

    /**
     * calcula o nível baseado nos pontos totais acumulados.
     * usa progressão aritmética: cada nível requer 100 × nível_atual pontos.
     *
     * @param pontos pontos totais acumulados
     * @return nível correspondente
     */
    private int calcularNivelPorPontos(int pontos) {
        int nivel = 1;
        int pontosAcumulados = 0;

        // tabela pré-calculada para performance (níveis 1-30)
        while (nivel <= 30) {
            int pontosParaProximoNivel = 100 * nivel;
            if (pontosAcumulados + pontosParaProximoNivel > pontos) {
                break; // ainda não tem pontos suficientes para o próximo nível
            }
            pontosAcumulados += pontosParaProximoNivel;
            nivel++;
        }

        return nivel;
    }

    /**
     * retorna o título baseado no nível do usuário.
     * progressão: Reporter → Analista → Investigador → Investigador Sênior → Detetive → Detetive Master → Caçador Supremo
     *
     * @param nivel nível do usuário
     * @return título correspondente ao nível
     */
    private String obterTituloPorNivel(int nivel) {
        if (nivel >= 30) return "Caçador Supremo";      // 46500+ pontos (4650+ acertos)
        if (nivel >= 25) return "Detetive Master";      // 32500+ pontos (3250+ acertos)
        if (nivel >= 20) return "Detetive";             // 21000+ pontos (2100+ acertos)
        if (nivel >= 15) return "Investigador Sênior";  // 12000+ pontos (1200+ acertos)
        if (nivel >= 10) return "Investigador";         // 4500+ pontos (450+ acertos)
        if (nivel >= 5) return "Analista";              // 1000+ pontos (100+ acertos)
        if (nivel >= 1) return "Reporter";              // 0+ pontos
        return "Reporter";                              // nível inicial
    }

    /**
     * calcula quantos pontos faltam para o próximo nível.
     * @return pontos necessários para o próximo nível (0 se já está no máximo)
     */
    public int pontosParaProximoNivel() {
        if (this.nivel >= 30) {
            return 0; // já está no nível máximo
        }

        // calcular quantos pontos já foram gastos até o nível atual
        int pontosAcumuladosAteNivelAtual = 0;
        for (int n = 1; n < this.nivel; n++) {
            pontosAcumuladosAteNivelAtual += 100 * n;
        }

        // calcular pontos necessários para o próximo nível
        int pontosParaProximoNivel = 100 * this.nivel;

        // calcular quantos pontos faltam
        int pontosGastos = this.pontuacaoTotal - pontosAcumuladosAteNivelAtual;
        return pontosParaProximoNivel - pontosGastos;
    }
}
