package com.renato.service;

/**
 * Serviço responsável pelos cálculos relacionados a pontuação e níveis.
 * Centraliza as regras de progressão do jogo.
 */
public class PontuacaoService {

    // Constantes de pontuação
    private static final int PONTOS_ACERTO = 10;
    private static final int PONTOS_ERRO = -5;

    // Constantes de níveis (pontos necessários para cada nível)
    private static final int PONTOS_NIVEL_1 = 0;    // Iniciante
    private static final int PONTOS_NIVEL_2 = 50;   // Aprendiz
    private static final int PONTOS_NIVEL_3 = 100;  // Conhecedor
    private static final int PONTOS_NIVEL_4 = 200;  // Especialista
    private static final int PONTOS_NIVEL_5 = 350;  // Mestre
    private static final int PONTOS_NIVEL_6 = 550;  // Lenda (nível máximo)

    /**
     * Calcula os pontos ganhos baseado se o usuário acertou ou errou.
     *
     * @param acertou true se acertou, false se errou
     * @return pontos ganhos (positivo para acerto, negativo para erro)
     */
    public int calcularPontos(boolean acertou) {
        return acertou ? PONTOS_ACERTO : PONTOS_ERRO;
    }

    /**
     * Calcula o nível baseado nos pontos de maestria acumulados.
     *
     * @param pontosMaestria total de pontos de maestria
     * @return nível atual (1 a 6)
     */
    public int calcularNivel(int pontosMaestria) {
        if (pontosMaestria < PONTOS_NIVEL_2) return 1;
        if (pontosMaestria < PONTOS_NIVEL_3) return 2;
        if (pontosMaestria < PONTOS_NIVEL_4) return 3;
        if (pontosMaestria < PONTOS_NIVEL_5) return 4;
        if (pontosMaestria < PONTOS_NIVEL_6) return 5;
        return 6; // nível máximo
    }

    /**
     * Retorna o nome do nível baseado no número do nível.
     *
     * @param nivel número do nível (1 a 6)
     * @return nome do nível
     */
    public String obterNomeNivel(int nivel) {
        switch (nivel) {
            case 1: return "Iniciante";
            case 2: return "Aprendiz";
            case 3: return "Conhecedor";
            case 4: return "Especialista";
            case 5: return "Mestre";
            case 6: return "Lenda";
            default: return "Desconhecido";
        }
    }

    /**
     * Calcula quantos pontos faltam para o próximo nível.
     *
     * @param pontosMaestria pontos atuais de maestria
     * @return pontos faltantes para o próximo nível (0 se já está no nível máximo)
     */
    public int pontosParaProximoNivel(int pontosMaestria) {
        int nivelAtual = calcularNivel(pontosMaestria);

        if (nivelAtual >= 6) {
            return 0; // já está no nível máximo
        }

        int pontosProximoNivel = obterPontosNecessariosParaNivel(nivelAtual + 1);
        return pontosProximoNivel - pontosMaestria;
    }

    /**
     * Retorna os pontos necessários para atingir um determinado nível.
     *
     * @param nivel número do nível desejado
     * @return pontos necessários
     */
    private int obterPontosNecessariosParaNivel(int nivel) {
        switch (nivel) {
            case 1: return PONTOS_NIVEL_1;
            case 2: return PONTOS_NIVEL_2;
            case 3: return PONTOS_NIVEL_3;
            case 4: return PONTOS_NIVEL_4;
            case 5: return PONTOS_NIVEL_5;
            case 6: return PONTOS_NIVEL_6;
            default: return 0;
        }
    }

    /**
     * Calcula a porcentagem de progresso no nível atual.
     *
     * @param pontosMaestria pontos atuais de maestria
     * @return porcentagem de progresso (0 a 100)
     */
    public double calcularPorcentagemProgressoNivel(int pontosMaestria) {
        int nivelAtual = calcularNivel(pontosMaestria);

        if (nivelAtual >= 6) {
            return 100.0; // já está no nível máximo
        }

        int pontosNivelAtual = obterPontosNecessariosParaNivel(nivelAtual);
        int pontosProximoNivel = obterPontosNecessariosParaNivel(nivelAtual + 1);
        int pontosNoNivel = pontosMaestria - pontosNivelAtual;
        int pontosNecessarios = pontosProximoNivel - pontosNivelAtual;

        return (pontosNoNivel * 100.0) / pontosNecessarios;
    }

    /**
     * Verifica se os pontos são suficientes para desbloquear uma peça específica.
     *
     * @param pontosMaestria pontos atuais de maestria
     * @param numeroPeca número da peça (1 a 6)
     * @return true se pode desbloquear a peça, false caso contrário
     */
    public boolean podeDesbloquearPeca(int pontosMaestria, int numeroPeca) {
        int nivelAtual = calcularNivel(pontosMaestria);
        return nivelAtual >= numeroPeca;
    }

    /**
     * Retorna o total de peças disponíveis no jogo.
     *
     * @return número total de peças (igual ao número de níveis)
     */
    public int getTotalPecas() {
        return 6;
    }
}

