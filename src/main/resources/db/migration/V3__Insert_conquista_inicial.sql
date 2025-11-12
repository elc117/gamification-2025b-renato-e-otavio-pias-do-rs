-- Script para adicionar conquista inicial
-- Conquista beta: Primeiros Passos
-- Cada questão correta = 5 pontos
-- Para desbloquear a conquista = 20 pontos (equivalente a 4 acertos)

INSERT INTO conquistas (nome, descricao, caminho_imagem_completa, criterio, tipo, valor_requerido)
VALUES (
    'Primeiros Passos',
    'Acumule 20 pontos respondendo questões corretamente. Cada acerto vale 5 pontos!',
    '🏆',
    'Acumular pontos totais',
    'PONTOS_TOTAIS',
    20
);

