-- script para popular o banco de dados com dados iniciais
-- insere 2 categorias, 8 notícias e 1 conquista

-- categoria: Política
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa)
VALUES ('Política', 'Notícias sobre política nacional e internacional', 4, 10, '/assets/images/politica.png');

-- notícias da categoria Política
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

(1, 'Senadores brasileiros têm mandato de 4 anos',
'Os senadores do Brasil são eleitos para mandatos de 4 anos, com possibilidade de reeleição.',
false,
'FALSO. Os senadores brasileiros são eleitos para mandatos de 8 anos, e a cada 4 anos é renovado um terço ou dois terços das cadeiras no Senado. Deputados federais, sim, têm mandatos de 4 anos.

Fontes: <a href="https://www.senado.leg.br" target="_blank">Senado Federal</a>, <a href="https://www.planalto.gov.br/ccivil_03/constituicao/constituicao.htm" target="_blank">Constituição Federal de 1988, artigo 46</a>');

-- V4__Add_futebol_category.sql
-- adiciona categoria Futebol com 4 notícias sobre o tema

-- categoria: Futebol
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa)
VALUES ('Futebol', 'Notícias e curiosidades sobre o mundo do futebol', 4, 10, 'assets/images/futebol.png');

-- notícias da categoria Futebol
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
((SELECT id FROM categorias WHERE nome = 'Futebol'), 
'Brasil é o país com mais títulos de Copa do Mundo',
'A Seleção Brasileira é a única pentacampeã mundial, tendo vencido as Copas de 1958, 1962, 1970, 1994 e 2002.',
true,
'VERDADEIRO. O Brasil é o país com mais títulos de Copa do Mundo FIFA, com 5 conquistas (1958, 1962, 1970, 1994 e 2002). A Alemanha e a Itália vêm em segundo lugar, com 4 títulos cada.

Fontes: <a href="https://www.fifa.com" target="_blank">FIFA</a>, <a href="https://www.cbf.com.br" target="_blank">CBF</a>'),

((SELECT id FROM categorias WHERE nome = 'Futebol'), 
'Pelé marcou mais de 1000 gols na carreira',
'Pelé, considerado o rei do futebol, marcou oficialmente mais de 1000 gols durante sua carreira profissional.',
true,
'VERDADEIRO. Pelé marcou seu milésimo gol em 19 de novembro de 1969, em um pênalti contra o Vasco da Gama. Segundo dados oficiais, ele marcou 1283 gols em 1363 jogos ao longo de sua carreira, contando partidas oficiais e amistosos.

Fontes: <a href="https://www.santosfc.com.br" target="_blank">Santos FC</a>, <a href="https://www.fifa.com" target="_blank">FIFA</a>'),

((SELECT id FROM categorias WHERE nome = 'Futebol'), 
'O futebol foi inventado na Inglaterra no século XIX',
'O futebol moderno, com suas regras padronizadas, foi criado na Inglaterra em 1863.',
true,
'VERDADEIRO. O futebol moderno foi oficialmente criado na Inglaterra em 1863, quando foi fundada a Football Association (FA) e estabelecidas as primeiras regras padronizadas do esporte. Embora jogos com bola existissem há séculos, foi na Inglaterra que o futebol ganhou as regras que conhecemos hoje.

Fontes: <a href="https://www.thefa.com/about-football-association/what-we-do/history" target="_blank">The FA</a>, <a href="https://www.fifa.com/about-fifa/who-we-are/the-game" target="_blank">FIFA</a>'),

((SELECT id FROM categorias WHERE nome = 'Futebol'), 
'Neymar é o maior artilheiro da história da Seleção Brasileira',
'Neymar Jr. ultrapassou Pelé e se tornou o maior artilheiro de todos os tempos da Seleção Brasileira.',
true,
'VERDADEIRO. Em setembro de 2023, Neymar ultrapassou Pelé e se tornou o maior artilheiro da história da Seleção Brasileira. Pelé tinha 77 gols em 92 jogos, e Neymar ultrapassou essa marca, chegando a mais de 79 gols pela seleção.

Fontes: <a href="https://www.cbf.com.br" target="_blank">CBF</a>, <a href="https://ge.globo.com" target="_blank">GloboEsporte</a>');

-- script para adicionar conquista inicial
-- conquista beta: Primeiros Passos
-- para desbloquear a conquista = 40 pontos

INSERT INTO conquistas (nome, descricao, caminho_imagem_completa, criterio, tipo, valor_requerido)
VALUES (
    'Primeiros Passos',
    'Acumulou 40 pontos respondendo questões corretamente!',
    '🏆',
    'Acumular pontos totais',
    'PONTOS_TOTAIS',
    40
);