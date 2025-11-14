-- Script para popular o banco de dados com dados iniciais
-- Insere 1 categoria e 4 notícias

-- Categoria: Política
-- Sistema: Níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- 1 acerto = 10 pontos = 1 nível. Nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa)
VALUES ('Política', 'Notícias sobre política nacional e internacional', 4, 10, '/images/categorias/politica_completa.png');

-- Notícias da categoria Política
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
(1, 'Brasil é o país com mais árvores do mundo',
'O Brasil possui a maior quantidade de árvores do planeta, com mais de 300 bilhões de árvores na Amazônia.',
false,
'FALSO. Embora o Brasil tenha a maior floresta tropical do mundo (Amazônia), a Rússia é o país com mais árvores do planeta, possuindo cerca de 640 bilhões de árvores. O Brasil fica em segundo lugar com aproximadamente 300 bilhões de árvores.

Fontes: <a href="https://www.globalforestwatch.org" target="_blank">Global Forest Watch</a>, <a href="https://www.nature.com/articles/nature14967" target="_blank">Nature Journal (2015)</a>'),

(1, 'Voto é obrigatório no Brasil',
'No Brasil, todos os cidadãos entre 18 e 70 anos são obrigados por lei a votar nas eleições.',
true,
'VERDADEIRO. O voto é obrigatório no Brasil para cidadãos entre 18 e 70 anos, conforme a Constituição Federal de 1988, artigo 14. Quem não vota e não justifica pode pagar multa e ter restrições como impossibilidade de tirar passaporte ou participar de concursos públicos.

Fontes: <a href="https://www.planalto.gov.br/ccivil_03/constituicao/constituicao.htm" target="_blank">Constituição Federal de 1988</a>, <a href="https://www.tse.jus.br/" target="_blank">TSE</a>'),

(1, 'Presidente pode vetar leis aprovadas pelo Congresso',
'O Presidente da República tem o poder de vetar parcial ou totalmente projetos de lei aprovados pelo Congresso Nacional.',
true,
'VERDADEIRO. Segundo a Constituição Federal, o Presidente pode vetar total ou parcialmente um projeto de lei aprovado pelo Congresso. Porém, o veto pode ser derrubado pelo Congresso com maioria absoluta em sessão conjunta.

Fontes: <a href="https://www.planalto.gov.br/ccivil_03/constituicao/constituicao.htm" target="_blank">Constituição Federal de 1988, artigo 66</a>'),

-- Nova notícia
(1, 'Senadores brasileiros têm mandato de 4 anos',
'Os senadores do Brasil são eleitos para mandatos de 4 anos, com possibilidade de reeleição.',
false,
'FALSO. Os senadores brasileiros são eleitos para mandatos de 8 anos, e a cada 4 anos é renovado um terço ou dois terços das cadeiras no Senado. Deputados federais, sim, têm mandatos de 4 anos.

Fontes: <a href="https://www.senado.leg.br" target="_blank">Senado Federal</a>, <a href="https://www.planalto.gov.br/ccivil_03/constituicao/constituicao.htm" target="_blank">Constituição Federal de 1988, artigo 46</a>');

-- Script para adicionar conquista inicial
-- Conquista beta: Primeiros Passos
-- Cada questão correta = 10 pontos
-- Para desbloquear a conquista = 40 pontos (equivalente a 4 acertos)

INSERT INTO conquistas (nome, descricao, caminho_imagem_completa, criterio, tipo, valor_requerido)
VALUES (
    'Primeiros Passos',
    'Acumule 40 pontos respondendo questões corretamente. Cada acerto vale 10 pontos!',
    '🏆',
    'Acumular pontos totais',
    'PONTOS_TOTAIS',
    40
);