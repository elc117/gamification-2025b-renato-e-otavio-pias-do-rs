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
    private String senha;

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

    public Usuario(Long id, String nome, String email, String senha, int nivel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    // ========== COMPORTAMENTOS (Métodos de Negócio) ==========

    /**
     * Registra uma tentativa de resposta e atualiza estatísticas automaticamente.
     * Encapsula a lógica de cálculo da taxa de acerto.
     */
    public void registrarTentativa(boolean acertou) {
        this.totalTentativas++;
        if (acertou) {
            this.acertosTotais++;
        }
        recalcularTaxaAcerto();
    }

    /**
     * Adiciona pontos ao usuário e verifica se subiu de nível.
     * @return true se subiu de nível
     */
    public boolean adicionarPontos(int pontos) {
        int nivelAnterior = this.nivel;
        this.pontuacaoTotal += pontos;
        this.nivel = calcularNivel(this.pontuacaoTotal);
        return this.nivel > nivelAnterior;
    }

    /**
     * Atualiza o título baseado no nível atual.
     * @return true se o título mudou
     */
    public boolean atualizarTitulo() {
        String tituloAnterior = this.tituloAtual;
        this.tituloAtual = calcularTitulo(this.nivel);
        return !this.tituloAtual.equals(tituloAnterior);
    }

    /**
     * Calcula pontos necessários para o próximo nível.
     */
    public int calcularPontosParaProximoNivel() {
        int pontosProximoNivel = 100 * this.nivel;
        return Math.max(0, pontosProximoNivel - this.pontuacaoTotal);
    }

    /**
     * Recalcula a taxa de acerto baseada nas estatísticas atuais.
     */
    private void recalcularTaxaAcerto() {
        if (this.totalTentativas > 0) {
            this.taxaAcerto = (this.acertosTotais * 100.0) / this.totalTentativas;
        } else {
            this.taxaAcerto = 0.0;
        }
    }

    /**
     * Calcula o nível baseado na pontuação total.
     * Regra: Cada 100 pontos = 1 nível
     */
    private int calcularNivel(int pontuacao) {
        return 1 + (pontuacao / 100);
    }

    /**
     * Determina o título baseado no nível do usuário.
     */
    private String calcularTitulo(int nivel) {
        if (nivel >= 20) return "Lenda da Verdade";
        if (nivel >= 15) return "Mestre Investigador";
        if (nivel >= 10) return "Detetive Expert";
        if (nivel >= 7) return "Caçador de Fakes";
        if (nivel >= 5) return "Verificador";
        if (nivel >= 3) return "Aprendiz";
        return "Iniciante";
    }

}
