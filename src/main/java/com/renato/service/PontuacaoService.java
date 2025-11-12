package com.renato.service;

/**
 * serviço responsável pelos cálculos relacionados a pontuação e níveis.
 * centraliza as regras de progressão do jogo.
 */
public class PontuacaoService {

    // constantes de pontuação
    private static final int PONTOS_ACERTO = 5;  // cada acerto vale 5 pontos
    private static final int PONTOS_ERRO = 0;

    // constantes de níveis (pontos necessários para cada nível)
    // sistema simplificado: cada acerto = 5 pontos
    // níveis vão de 0 a 4
    private static final int PONTOS_NIVEL_0 = 0;    // Nível 0 (0% - sem peças)
    private static final int PONTOS_NIVEL_1 = 5;   // Nível 1 (25% - 1 peça) - 2 acertos
    private static final int PONTOS_NIVEL_2 = 10;   // Nível 2 (50% - 2 peça) - 4 acertos
    private static final int PONTOS_NIVEL_3 = 15;   // Nível 3 (75% - 3 peça) - 6 acertos
    private static final int PONTOS_NIVEL_4 = 20;   // Nível 4 (100% - 4 peça) - 8 acertos (máximo)

    /**
     * calcula os pontos ganhos baseado se o usuário acertou ou errou.
     *
     * @param acertou true se acertou, false se errou
     * @return pontos ganhos (positivo para acerto, negativo para erro)
     */
    public int calcularPontos(boolean acertou) {
        return acertou ? PONTOS_ACERTO : PONTOS_ERRO;
    }

    /**
     * calcula o nível baseado nos pontos de maestria acumulados.
     *
     * @param pontosMaestria total de pontos de maestria
     * @return nível atual (0 a 4)
     */
    public int calcularNivel(int pontosMaestria) {
        if (pontosMaestria < PONTOS_NIVEL_1) return 0;
        if (pontosMaestria < PONTOS_NIVEL_2) return 1;
        if (pontosMaestria < PONTOS_NIVEL_3) return 2;
        if (pontosMaestria < PONTOS_NIVEL_4) return 3;
        return 4; // nível máximo
    }

    /**
     * retorna o nome do nível baseado no número do nível.
     *
     * @param nivel número do nível (0 a 4)
     * @return nome do nível
     */
    public String obterNomeNivel(int nivel) {
        switch (nivel) {
            case 0: return "Iniciante";
            case 1: return "Aprendiz";
            case 2: return "Conhecedor";
            case 3: return "Especialista";
            case 4: return "Mestre";
            default: return "Desconhecido";
        }
    }

    /**
     * calcula quantos pontos faltam para o próximo nível.
     *
     * @param pontosMaestria pontos atuais de maestria
     * @return pontos faltantes para o próximo nível (0 se já está no nível máximo)
     */
    public int pontosParaProximoNivel(int pontosMaestria) {
        int nivelAtual = calcularNivel(pontosMaestria);

        if (nivelAtual >= 4) {
            return 0; // já está no nível máximo
        }

        int pontosProximoNivel = obterPontosNecessariosParaNivel(nivelAtual + 1);
        return pontosProximoNivel - pontosMaestria;
    }

    /**
     * retorna os pontos necessários para atingir um determinado nível.
     *
     * @param nivel número do nível desejado
     * @return pontos necessários
     */
    private int obterPontosNecessariosParaNivel(int nivel) {
        switch (nivel) {
            case 0: return PONTOS_NIVEL_0;
            case 1: return PONTOS_NIVEL_1;
            case 2: return PONTOS_NIVEL_2;
            case 3: return PONTOS_NIVEL_3;
            case 4: return PONTOS_NIVEL_4;
            default: return 0;
        }
    }

    /**
     * calcula a porcentagem de progresso no nível atual.
     *
     * @param pontosMaestria pontos atuais de maestria
     * @return porcentagem de progresso (0 a 100)
     */
    public double calcularPorcentagemProgressoNivel(int pontosMaestria) {
        int nivelAtual = calcularNivel(pontosMaestria);

        if (nivelAtual >= 4) {
            return 100.0; // já está no nível máximo
        }

        int pontosNivelAtual = obterPontosNecessariosParaNivel(nivelAtual);
        int pontosProximoNivel = obterPontosNecessariosParaNivel(nivelAtual + 1);
        int pontosNoNivel = pontosMaestria - pontosNivelAtual;
        int pontosNecessarios = pontosProximoNivel - pontosNivelAtual;

        return (pontosNoNivel * 100.0) / pontosNecessarios;
    }

    /**
     * verifica se os pontos são suficientes para desbloquear uma peça específica.
     * nível 0 não desbloqueia peça (imagem em branco no frontend).
     *
     * @param pontosMaestria pontos atuais de maestria
     * @param numeroPeca número da peça (1 a 4)
     * @return true se pode desbloquear a peça, false caso contrário
     */
    public boolean podeDesbloquearPeca(int pontosMaestria, int numeroPeca) {
        int nivelAtual = calcularNivel(pontosMaestria);
        // nível 0 não desbloqueia nada, então peça 1 só no nível 1
        return nivelAtual >= numeroPeca;
    }

    /**
     * retorna o total de peças disponíveis no jogo.
     *
     * @return número total de peças (4 peças para 5 níveis)
     */
    public int getTotalPecas() {
        return 4;
    }
}