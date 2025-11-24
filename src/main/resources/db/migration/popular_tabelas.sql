-- script para popular o banco de dados com dados iniciais
-- insere 2 categorias, 8 notícias e 1 conquista

-- categoria: Política
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (1, 'Política', 'Notícias sobre política nacional e internacional', 4, 10, 'assets/images/politica.png', 'assets/images/CategoriaPolitica.png');

-- notícias da categoria Política
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
((SELECT id FROM categorias WHERE nome = 'Política'), 'URGENTE: Governo anuncia que vai distribuir R$ 50 mil para cada brasileiro ainda este mês',
'O governo federal anunciou nesta madrugada um programa emergencial que vai distribuir R$ 50 mil para cada cidadão brasileiro maior de 18 anos. O dinheiro estará disponível a partir do dia 20 e pode ser sacado em qualquer agência bancária. Segundo fontes do Palácio do Planalto, basta apresentar CPF e RG. COMPARTILHE URGENTE!!!',
false,
'**FALSO.** Esta notícia apresenta diversos sinais clássicos de fake news:

**Sinais identificadores:**

- **"URGENTE" e "COMPARTILHE" em caixa alta:** linguagem sensacionalista típica de fake news
- **Valores absurdos:** R$ 50 mil por pessoa custaria trilhões aos cofres públicos (impossível economicamente)
- **"Fontes do Palácio" sem identificação:** fontes vagas e não nomeadas
- **Promessa simples demais:** "basta apresentar CPF e RG" - programas governamentais têm processos mais complexos
- **Senso de urgência:** "ainda este mês", "nesta madrugada" - cria pressão para compartilhar sem verificar

Programas sociais reais são anunciados oficialmente em canais do governo, passam pelo Congresso e têm ampla cobertura da imprensa tradicional.

**Fontes:** https://www.gov.br/planalto/pt-br - Palácio do Planalto - Site Oficial, https://www12.senado.leg.br/noticias - Agência Senado'),


((SELECT id FROM categorias WHERE nome = 'Política'), 'STF mantém decisão sobre foro privilegiado para crimes cometidos após fim do mandato',
'O Supremo Tribunal Federal (STF) decidiu, por 7 votos a 4, manter o entendimento de que autoridades com foro privilegiado devem ser julgadas pela Corte apenas por crimes cometidos durante o exercício do cargo e relacionados às suas funções. A decisão foi tomada em sessão plenária na última quinta-feira (10) e seguiu o voto do relator, ministro Roberto Barroso.',
true,
'**VERDADEIRO.** Esta notícia apresenta características de jornalismo profissional:

**Sinais de credibilidade:**

- **Linguagem objetiva e técnica:** sem sensacionalismo ou apelos emocionais
- **Informações específicas:** placar da votação (7 a 4), data exata, nome do relator
- **Fatos verificáveis:** decisões do STF são públicas e podem ser consultadas
- **Ausência de pedidos de compartilhamento:** notícias verdadeiras não pedem para "viralizar"
- **Tema plausível:** dentro das atribuições normais do STF
- **Tom neutro:** não usa adjetivos exagerados nem palavras em caixa alta

Decisões judiciais podem ser verificadas nos sites oficiais dos tribunais e são amplamente noticiadas por diversos veículos de imprensa.

**Fontes:** https://portal.stf.jus.br/ - Portal STF, https://www.conjur.com.br/ - Consultor Jurídico'),


((SELECT id FROM categorias WHERE nome = 'Política'), 'Deputados aprovam em SEGREDO lei que proíbe uso de dinheiro em espécie no Brasil',
'Você não viu na TV porque a mídia foi comprada!!! Os deputados votaram às escondidas uma lei que proíbe o dinheiro de papel no Brasil a partir de janeiro. Tudo será digital e o governo vai controlar cada centavo que você gasta. Um amigo que trabalha em Brasília me contou. Acorde Brasil! Eles querem o controle total da população!!!',
false,
'**FALSO.** Esta notícia apresenta características clássicas de teoria conspiratória:

**Sinais identificadores:**

- **Teoria da conspiração:** "votaram às escondidas", "mídia foi comprada"
- **Pontos de exclamação excessivos:** "!!!" - apelo emocional exagerado
- **Fonte não verificável:** "um amigo que trabalha em Brasília" - não é fonte oficial
- **Linguagem alarmista:** "Acorde Brasil!", "controle total"
- **Informação facilmente desmentível:** votações no Congresso são públicas e transmitidas ao vivo

**Fatos:**

- Todas as sessões do Congresso Nacional são públicas e transmitidas pela TV Senado/TV Câmara
- Projetos de lei passam por tramitação pública e transparente
- Uma mudança dessa magnitude seria amplamente noticiada e debatida

**Fontes:** https://www.camara.leg.br/ - Câmara dos Deputados, https://www12.senado.leg.br/ - Senado Federal'),

((SELECT id FROM categorias WHERE nome = 'Política'), 'TSE registra aumento de 5,4% no número de eleitores aptos a votar nas próximas eleições',
'O Tribunal Superior Eleitoral (TSE) divulgou nesta terça-feira (12) que o Brasil conta atualmente com 156,4 milhões de eleitores aptos a votar. O número representa um crescimento de 5,4% em relação ao último pleito eleitoral. Segundo o presidente do TSE, ministro Alexandre de Moraes, o aumento se deve principalmente à inclusão de jovens de 16 e 17 anos que fizeram o cadastramento eleitoral pela primeira vez.',
true,
'**VERDADEIRO.** Esta notícia demonstra características de informação confiável:

**Sinais de credibilidade:**

- **Fonte oficial identificada:** TSE e seu presidente nomeado
- **Dados específicos:** 156,4 milhões de eleitores, crescimento de 5,4%
- **Informações verificáveis:** dados do TSE são públicos e acessíveis
- **Contexto adequado:** explica o motivo do aumento
- **Linguagem jornalística:** objetiva, sem adjetivos exagerados
- **Data específica:** "nesta terça-feira (12)"
- **Ausência de apelos emocionais:** não tenta manipular o leitor

Dados eleitorais oficiais podem ser consultados diretamente no portal do TSE e são rotineiramente divulgados por veículos de imprensa profissionais.

**Fontes:** https://www.tse.jus.br/ - Tribunal Superior Eleitoral, https://agenciabrasil.ebc.com.br/ - Agência Brasil');

-- V4__Add_futebol_category.sql
-- adiciona categoria Futebol com 4 notícias sobre o tema

-- categoria: Futebol
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (2, 'Futebol', 'Notícias e curiosidades sobre o mundo do futebol', 4, 10, 'assets/images/futebol.png', 'assets/images/capafutebol.png');

-- notícias da categoria Futebol
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
((SELECT id FROM categorias WHERE nome = 'Futebol'), 'BOMBA: Neymar assina contrato de R$ 500 milhões por mês com time brasileiro',
'URGENTE!!! Neymar fechou acordo milionário com o Flamengo e vai receber R$ 500 milhões por mês! O anúncio será feito amanhã no Maracanã lotado. Meu primo que trabalha no aeroporto viu ele chegando com a camisa do time. Compartilhe antes que apaguem!!!',
false,
'**FALSO.** Esta notícia apresenta diversos sinais clássicos de fake news esportivas:

**Sinais identificadores:**

- **"BOMBA", "URGENTE" em caixa alta:** linguagem sensacionalista típica de fake news
- **Valores completamente absurdos:** R$ 500 milhões por mês é economicamente impossível para qualquer clube brasileiro
- **"Compartilhe antes que apaguem":** típico apelo conspiratório de fake news
- **Fonte não verificável:** "meu primo que trabalha no aeroporto" - não é fonte oficial
- **Informação vaga sobre anúncio:** "amanhã no Maracanã" sem detalhes concretos
- **Urgência artificial:** pressiona o leitor a compartilhar sem verificar

Contratações reais de jogadores são anunciadas oficialmente pelos clubes, com coletivas de imprensa e ampla cobertura da mídia esportiva profissional.

**Fontes:** https://www.flamengo.com.br/ - Site Oficial do Flamengo, https://ge.globo.com/ - GloboEsporte'),

((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Palmeiras vence São Paulo por 2 a 1 e assume liderança do Campeonato Brasileiro',
'O Palmeiras venceu o São Paulo por 2 a 1 na noite deste domingo (14), no Allianz Parque, pela 33ª rodada do Campeonato Brasileiro. Os gols da vitória foram marcados por Rony aos 23 minutos do primeiro tempo e Raphael Veiga aos 38 da etapa final. Luciano descontou para o São Paulo aos 15 do segundo tempo. Com o resultado, o Palmeiras chegou aos 67 pontos e assumiu a liderança da competição.',
true,
'**VERDADEIRO.** Esta notícia apresenta características de jornalismo esportivo profissional:

**Sinais de credibilidade:**

- **Informações específicas e verificáveis:** placar exato, autores dos gols, minutos em que foram marcados
- **Data e local precisos:** "noite deste domingo (14)", "Allianz Parque"
- **Dados concretos da competição:** 33ª rodada, 67 pontos na classificação
- **Linguagem objetiva:** sem adjetivos exagerados ou apelos emocionais
- **Tom jornalístico neutro:** relata os fatos sem sensacionalismo
- **Ausência de pedidos de compartilhamento:** notícias verdadeiras não pedem para "viralizar"

Resultados de partidas oficiais podem ser verificados nos sites dos clubes, da CBF e em diversos veículos especializados em esporte.

**Fontes:** https://www.cbf.com.br/ - Confederação Brasileira de Futebol, https://www.espn.com.br/ - ESPN Brasil'),

((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Cristiano Ronaldo admite em entrevista que nunca jogou futebol e tudo foi CGI',
'CHOCANTE! Em entrevista exclusiva que a Globo não vai mostrar, Cristiano Ronaldo confessou que nunca jogou futebol de verdade. Tudo era computação gráfica! A FIFA está desesperada tentando abafar o caso. Um hacker russo vazou os arquivos que provam tudo. ACORDA MUNDO! Nos enganaram por anos!!!',
false,
'**FALSO.** Esta notícia apresenta características absurdas de teoria conspiratória:

**Sinais identificadores:**

- **Alegação completamente absurda:** impossível falsificar décadas de jogos ao vivo com milhares de testemunhas
- **Teoria da conspiração:** "a Globo não vai mostrar", "FIFA tentando abafar"
- **Fonte inventada:** "hacker russo vazou arquivos" - clássico de fake news conspiratórias
- **Linguagem alarmista:** "CHOCANTE", "ACORDA MUNDO" em caixa alta
- **Apelo emocional exagerado:** "nos enganaram por anos!!!"
- **Impossibilidade prática:** milhões de pessoas assistiram jogos presencialmente

**Fatos:**

- Cristiano Ronaldo jogou em diversos clubes com milhões de torcedores presentes nos estádios
- Existem inúmeros registros, fotos, vídeos de diferentes ângulos e fontes
- A carreira dele é amplamente documentada por veículos independentes do mundo todo

**Fontes:** https://www.uefa.com/ - UEFA, https://www.fifa.com/ - FIFA'),

((SELECT id FROM categorias WHERE nome = 'Futebol'), 'CBF anuncia novas datas para jogos da Seleção Brasileira nas Eliminatórias da Copa',
'A Confederação Brasileira de Futebol (CBF) confirmou nesta quinta-feira (13) o calendário dos próximos jogos da Seleção Brasileira nas Eliminatórias da Copa do Mundo de 2026. O Brasil enfrentará o Uruguai no dia 15 de novembro, em Salvador, e a Colômbia no dia 19 do mesmo mês, em Barranquilla. Segundo o presidente da CBF, Ednaldo Rodrigues, a convocação dos jogadores será divulgada na próxima semana pelo técnico Dorival Júnior.',
true,
'**VERDADEIRO.** Esta notícia demonstra características de informação esportiva confiável:

**Sinais de credibilidade:**

- **Fonte oficial identificada:** CBF e seu presidente nomeado
- **Informações específicas:** datas exatas (15 e 19 de novembro), locais (Salvador e Barranquilla)
- **Contexto adequado:** Eliminatórias da Copa do Mundo de 2026
- **Nome do técnico mencionado:** Dorival Júnior
- **Linguagem jornalística objetiva:** sem sensacionalismo
- **Data do anúncio:** "nesta quinta-feira (13)"
- **Informação verificável:** calendário oficial pode ser consultado na CBF

Calendários de jogos oficiais das seleções são divulgados pelas confederações e amplamente noticiados por veículos especializados em esporte.

**Fontes:** https://www.cbf.com.br/ - Confederação Brasileira de Futebol, https://ge.globo.com/ - GloboEsporte');

-- Categoria: Biologia
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (3, 'Biologia', 'Notícias sobre ciências biológicas, saúde e descobertas científicas', 4, 10, 'assets/images/conclusaobiologia.jpg', 'assets/images/capabiologia.png');

-- notícias da categoria Biologia
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'URGENTE: Cientistas descobrem que beber água gelada causa câncer imediatamente',
     'ATENÇÃO!!! Pesquisadores da "Universidade Internacional de Saúde" comprovaram que água gelada causa câncer instantâneo porque o choque térmico no estômago cria células cancerígenas na hora. Milhões já morreram e a mídia esconde! Um médico que não pode revelar o nome confirmou. PARE DE BEBER ÁGUA GELADA AGORA! Compartilhe para salvar vidas!!!',
     false,
     '**FALSO.** Esta notícia apresenta diversos sinais clássicos de fake news sobre saúde:

**Sinais identificadores:**
- **"URGENTE" e "COMPARTILHE" em caixa alta:** linguagem sensacionalista típica de fake news
- **Instituição inexistente:** "Universidade Internacional de Saúde" não existe
- **Afirmações absurdas:** "câncer instantâneo", "milhões já morreram" - sem embasamento científico
- **Fonte não identificada:** "um médico que não pode revelar o nome"
- **Teoria conspiratória:** "mídia esconde"
- **Apelo emocional:** "salvar vidas!!!"

**Fatos científicos:**
- Não há evidência científica de que temperatura da água cause câncer
- Câncer é uma doença complexa com múltiplas causas (genéticas, ambientais, estilo de vida)
- Instituições de saúde confiáveis (OMS, INCA) não fazem tais afirmações

**Fontes:** https://www.inca.gov.br/ - Instituto Nacional de Câncer, https://www.who.int/ - Organização Mundial da Saúde'),


    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Estudo publicado na Nature confirma eficácia de nova terapia genética contra anemia falciforme',
     'Pesquisadores da Universidade de Harvard publicaram na revista Nature os resultados promissores de uma terapia genética experimental para tratamento de anemia falciforme. O estudo, conduzido com 45 pacientes ao longo de três anos, mostrou melhora significativa em 89% dos casos, com redução drástica nas crises de dor características da doença. A técnica utiliza edição genética CRISPR para corrigir a mutação que causa a condição.',
     true,
     '**VERDADEIRO.** Esta notícia apresenta características de informação científica confiável:

**Sinais de credibilidade:**
- **Fonte acadêmica respeitada:** Universidade de Harvard
- **Publicação científica renomada:** Nature é revista de alto impacto
- **Dados específicos:** 45 pacientes, três anos, 89% de melhora
- **Método identificado:** CRISPR (técnica real de edição genética)
- **Linguagem técnica mas acessível:** "terapia genética experimental", "mutação"
- **Tom cauteloso:** "resultados promissores", não promete cura milagrosa
- **Informações verificáveis:** publicações na Nature são públicas

Avanços científicos legítimos são publicados em revistas revisadas por pares e amplamente noticiados por veículos especializados.

**Fontes:** https://www.nature.com/ - Nature Journal, https://www.harvard.edu/ - Universidade de Harvard'),


    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'CHOCANTE: Vacinas contém chips microscópicos para controlar a mente das pessoas',
     'BOMBA!!! Vazou documento secreto provando que TODAS as vacinas têm chips microscópicos que o governo usa pra controlar nossa mente!!! Um enfermeiro corajoso filmou com microscópio e dá pra ver os chips se mexendo!!! Por isso querem vacinar todo mundo! Eles querem transformar a população em robôs obedientes! Minha vizinha tomou vacina e agora age estranho! ACORDA HUMANIDADE!!!',
     false,
     '**FALSO.** Esta é uma teoria conspiratória sem fundamento científico:

**Sinais identificadores:**
- **Teoria conspiratória clássica:** controle mental, chips, governo controlador
- **Linguagem sensacionalista:** "BOMBA!!!", "CHOCANTE", múltiplos pontos de exclamação
- **Fonte não verificável:** "documento secreto", "enfermeiro anônimo"
- **Evidência questionável:** "filmou com microscópio" - chips precisariam ser visíveis
- **Apelo emocional:** "ACORDA HUMANIDADE"
- **Evidência anedótica:** "minha vizinha age estranho"

**Fatos científicos:**
- Chips eletrônicos são grandes demais para passar por agulhas de vacina
- Vacinas são compostas de componentes biológicos simples (antígenos, adjuvantes)
- Composição de vacinas é pública e regulamentada
- Não existe tecnologia para "controle mental" via chip

**Fontes:** https://www.who.int/ - Organização Mundial da Saúde, https://portal.fiocruz.br/ - Fundação Oswaldo Cruz'),


    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Fiocruz identifica nova espécie de mosquito transmissor de dengue na região amazônica',
     'Pesquisadores da Fundação Oswaldo Cruz (Fiocruz) identificaram uma nova espécie de mosquito capaz de transmitir o vírus da dengue na região amazônica. O Aedes amazônico, como foi denominado, apresenta hábitos diurnos semelhantes ao Aedes aegypti, mas adaptado ao clima da floresta. A descoberta foi publicada na revista científica PLOS Neglected Tropical Diseases e reforça a necessidade de vigilância epidemiológica na região.',
     true,
     '**VERDADEIRO.** Esta notícia demonstra características de divulgação científica confiável:

**Sinais de credibilidade:**
- **Instituição respeitada:** Fiocruz é referência em pesquisa no Brasil
- **Informação específica:** nome científico dado (Aedes amazônico), região identificada
- **Publicação científica:** PLOS Neglected Tropical Diseases é revista reconhecida
- **Contexto adequado:** compara com espécie conhecida (Aedes aegypti)
- **Implicações realistas:** "necessidade de vigilância" - não sensacionaliza
- **Linguagem técnica apropriada:** "hábitos diurnos", "vigilância epidemiológica"
- **Informações verificáveis:** publicações científicas são acessíveis

Descobertas científicas legítimas seguem protocolo de publicação, revisão por pares e são divulgadas por instituições oficiais.

**Fontes:** https://portal.fiocruz.br/ - Fundação Oswaldo Cruz, https://journals.plos.org/ - PLOS Journals');

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


