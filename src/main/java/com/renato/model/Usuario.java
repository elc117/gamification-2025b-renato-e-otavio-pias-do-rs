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

    public void incrementarTentativas() {
        this.totalTentativas++;
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
     * sistema de progressão: a cada 50 pontos = 1 nível
     * nível máximo: 20
     */
    private void atualizarNivel() {
        // calcular nível baseado na pontuação total
        // a cada 50 pontos = 1 nível (começando do nível 0)
        int novoNivel = this.pontuacaoTotal / 50;

        // garantir que não passa do nível máximo
        if (novoNivel > 20) {
            novoNivel = 20;
        }

        this.nivel = novoNivel;
        this.tituloAtual = obterTituloPorNivel(novoNivel);
    }

    /**
     * retorna o título baseado no nível do usuário.
     * @param nivel nível do usuário
     * @return título correspondente ao nível
     */
    private String obterTituloPorNivel(int nivel) {
        if (nivel >= 18) return "Caçador Supremo";      // 850+ pontos
        if (nivel >= 15) return "Detetive Master";      // 700+ pontos
        if (nivel >= 12) return "Detetive";             // 550+ pontos
        if (nivel >= 10) return "Investigador Sênior";  // 450+ pontos
        if (nivel >= 7) return "Investigador";          // 300+ pontos
        if (nivel >= 5) return "Analista";              // 200+ pontos
        if (nivel >= 3) return "Reporter";              // 100+ pontos
        return "Novato";                                // 0-99 pontos
    }

    /**
     * calcula quantos pontos faltam para o próximo nível.
     * @return pontos necessários para o próximo nível (0 se já está no máximo)
     */
    public int pontosParaProximoNivel() {
        if (this.nivel >= 20) {
            return 0; // já está no nível máximo
        }

        int pontosProximoNivel = (this.nivel + 1) * 50;
        return pontosProximoNivel - this.pontuacaoTotal;
    }
}
