-- Script para popular o banco de dados com dados iniciais
-- Insere 1 categoria e 3 notícias

-- Categoria: Política
INSERT INTO categorias (nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa)
VALUES ('Política', 'Notícias sobre política nacional e internacional', 6, 50, '/images/categorias/politica_completa.png');

-- Notícias da categoria Política
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao, dificuldade, fontes)
VALUES
(1, 'Brasil é o país com mais árvores do mundo',
'O Brasil possui a maior quantidade de árvores do planeta, com mais de 300 bilhões de árvores na Amazônia.',
false,
'FALSO. Embora o Brasil tenha a maior floresta tropical do mundo (Amazônia), a Rússia é o país com mais árvores do planeta, possuindo cerca de 640 bilhões de árvores. O Brasil fica em segundo lugar com aproximadamente 300 bilhões de árvores.',
2,
'Fontes: Global Forest Watch, Nature Journal 2015'),

(2, 'Voto é obrigatório no Brasil',
'No Brasil, todos os cidadãos entre 18 e 70 anos são obrigados por lei a votar nas eleições.',
true,
'VERDADEIRO. O voto é obrigatório no Brasil para cidadãos entre 18 e 70 anos, conforme a Constituição Federal de 1988, artigo 14. Quem não vota e não justifica pode pagar multa e ter restrições como impossibilidade de tirar passaporte ou participar de concursos públicos.',
1,
'Fontes: Constituição Federal de 1988, TSE'),

(3, 'Presidente pode vetar leis aprovadas pelo Congresso',
'O Presidente da República tem o poder de vetar parcial ou totalmente projetos de lei aprovados pelo Congresso Nacional.',
true,
'VERDADEIRO. Segundo a Constituição Federal, o Presidente pode vetar total ou parcialmente um projeto de lei aprovado pelo Congresso. Porém, o veto pode ser derrubado pelo Congresso com maioria absoluta em sessão conjunta.',
2,
'Fontes: Constituição Federal de 1988, artigo 66');

