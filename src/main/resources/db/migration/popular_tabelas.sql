-- script para popular o banco de dados com dados iniciais
-- insere 2 categorias, 8 notícias e 1 conquista

-- categoria: Política
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (1, 'Política', 'Notícias sobre política nacional e internacional', 4, 10, 'assets/images/conclusaopolitica.jpg', 'assets/images/CategoriaPolitica.png');

-- notícias da categoria Política
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Política'),
    'Governo planeja limitar quantidade de compras internacionais por pessoa a apenas duas por mês',
    'Uma publicação viral afirma que o governo federal teria definido um limite de duas compras internacionais mensais por CPF, incluindo marketplaces como Shopee, AliExpress e Amazon. Segundo a postagem, a medida seria parte de um projeto de “controle fiscal avançado” e começaria a valer a partir de fevereiro, sem necessidade de aprovação do Congresso.',
    false,
    '**FALSO.** Não há nenhuma medida vigente ou projeto oficial que limite o número de compras internacionais por pessoa. Regulações de importação tratam de valores, impostos e fiscalização, mas nunca estabelecem quantidade máxima por indivíduo.

**Sinais identificadores:**
- Regulação inédita e sem debate público
- Ausência total de decreto, portaria ou texto oficial
- Alegação de implementação direta sem Congresso
- Expressão vaga ("controle fiscal avançado") usada para parecer técnica

**Fontes:**
- Receita Federal: https://www.gov.br/receitafederal/pt-br
- Agência Gov: https://agenciagov.ebc.com.br/
- G1 Economia: https://g1.globo.com/economia/'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Ministério da Saúde teria fechado acordo secreto para exigir carteira de vacinação digital até para viagens estaduais',
     'Circula em grupos de mensagens a alegação de que o Ministério da Saúde teria fechado um acordo com estados para tornar obrigatória uma “carteira digital unificada” para viagens entre estados do Brasil. O texto diz que passageiros seriam impedidos de embarcar em ônibus interestaduais caso não apresentassem o documento digital.',
     false,
     '**FALSO.** O Ministério da Saúde nunca anunciou qualquer exigência de carteira de vacinação digital para viagens estaduais. Não existe norma federal ou estadual que autorize impedimento de deslocamento por ausência de comprovante digital.

**Sinais identificadores:**
- "Acordo secreto" sem registro oficial
- Medida com grande impacto sem anúncio público
- Ausência de base legal para restringir locomoção interna
- Mistura de tecnologias inexistentes com medidas sanitárias

**Fontes:**
- Ministério da Saúde: https://www.gov.br/saude/pt-br
- Agência Brasil: https://agenciabrasil.ebc.com.br/
- G1 Saúde: https://g1.globo.com/saude/'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Nova lei permitiria ao governo bloquear contas bancárias automaticamente em suspeitas de fraude',
     'Textos compartilhados nas redes sociais afirmam que uma lei recém-aprovada daria ao governo federal o poder de bloquear automaticamente contas bancárias sem ordem judicial, apenas com base em “suspeitas de atividade fraudulenta”, determinadas por algoritmos.',
     false,
     '**FALSO.** A legislação brasileira exige ordem judicial ou procedimento formal para bloqueio de contas, como previsto em leis e decisões do STF. Nenhuma lei recente cria bloqueio automático sem decisão judicial.

**Sinais identificadores:**
- Alegação de poder extraordinário sem respaldo legal
- Citação de "algoritmos" como justificativa vaga
- Falta de número da lei, data ou órgão responsável
- Violação direta da Constituição sem debate público

**Fontes:**
- Banco Central: https://www.bcb.gov.br/
- STF: https://portal.stf.jus.br/
- Agência Senado: https://www12.senado.leg.br/noticias'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Prefeituras poderão cobrar taxa anual para uso de redes sociais a partir de 2025, diz publicação',
     'Postagens afirmam que um novo projeto permitiria que prefeituras cobrem uma “taxa de presença digital”, obrigando moradores a pagar anualmente para manter contas em redes sociais como Instagram, TikTok e Facebook.',
     false,
     '**FALSO.** Prefeituras não têm competência legal para criar taxas relacionadas ao uso de serviços digitais privados. Não existe proposta nacional que autorize tal cobrança.

**Sinais identificadores:**
- Criação de taxa municipal sobre serviço privado internacional
- Conceito irreal ("presença digital" como tributo)
- Ausência de projeto real ou número de PL
- Inconsistência jurídica básica

**Fontes:**
- Câmara dos Deputados: https://www.camara.leg.br/
- Agência Senado: https://www12.senado.leg.br/noticias
- G1 Política: https://g1.globo.com/politica/'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Governo estuda impor limite mensal de posts por usuário em redes sociais para “reduzir desinformação”',
     'Uma corrente afirma que o governo estaria analisando limitar a quantidade de posts por usuário em plataformas como X, Instagram e TikTok. O suposto limite seria definido por faixa etária e poderia variar de 20 a 50 publicações mensais.',
     false,
     '**FALSO.** Não existe medida governamental brasileira que imponha limite de posts por indivíduo. A proposta violaria direitos constitucionais, como liberdade de expressão e comunicação.

**Sinais identificadores:**
- Proposta tecnicamente impossível de fiscalizar
- Forte violação constitucional sem debate público
- Ausência de documento oficial, portaria ou minuta
- Discurso atribuído genericamente ao "governo"

**Fontes:**
- Constituição Federal: https://www.planalto.gov.br/ccivil_03/constituicao/constituicao.htm
- Ministério da Justiça: https://www.gov.br/mj/pt-br'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Senado aprova inclusão de crimes cibernéticos no rol de prioridade de investigação federal',
     'O Senado aprovou, em votação simbólica, um projeto de lei que coloca crimes cibernéticos como prioridade para atuação da Polícia Federal. A proposta busca modernizar o tratamento de delitos digitais e reforçar a atuação da PF em casos de fraudes online e invasões de sistemas.',
     true,
     '**VERDADEIRO.** A modernização do marco legal contra crimes digitais está em discussão há anos, e o Senado tem apresentado projetos nessa linha.

**Sinais de credibilidade:**
- Votação registrada no Senado
- Tema já discutido em diferentes legislaturas
- Lógica compatível com agenda de segurança digital

**Fontes:**
- Senado Federal: https://www12.senado.leg.br/noticias
- Agência Brasil: https://agenciabrasil.ebc.com.br/
- Polícia Federal: https://www.gov.br/pf/pt-br'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Brasil amplia participação em missões de paz da ONU com envio de especialistas em mediação',
     'O Ministério das Relações Exteriores confirmou a ampliação da participação brasileira em missões de paz da ONU, com o envio de especialistas em mediação e resolução de conflitos. O objetivo é apoiar iniciativas humanitárias e fortalecer a imagem diplomática do país.',
     true,
     '**VERDADEIRO.** O Brasil participa de missões de paz da ONU há décadas e frequentemente amplia ou ajusta sua atuação.

**Sinais de credibilidade:**
- Confirmação por órgão diplomático (Itamaraty)
- Alinhamento com política externa brasileira histórica
- Tema recorrente em pautas multilaterais

**Fontes:**
- Itamaraty: https://www.gov.br/mre/pt-br
- Nações Unidas Brasil: https://brasil.un.org/
- Agência Brasil: https://agenciabrasil.ebc.com.br/'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Ministério da Educação anuncia novos investimentos em escolas técnicas federais',
     'O Ministério da Educação informou que realizará novos investimentos em escolas técnicas federais voltados à ampliação de laboratórios, formação de professores e criação de cursos na área de tecnologia e inovação.',
     true,
     '**VERDADEIRO.** O MEC frequentemente anuncia investimentos em institutos federais e escolas técnicas, o que é amplamente divulgado por portais oficiais.

**Sinais de credibilidade:**
- Política pública consistente com ações do MEC
- Anúncio institucional
- Objetivos claros e plausíveis

**Fontes:**
- MEC: https://www.gov.br/mec/pt-br
- Agência Gov: https://agenciagov.ebc.com.br/
- G1 Educação: https://g1.globo.com/educacao/'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Governo altera regras para repasses do Fundo Nacional de Segurança Pública',
     'O Ministério da Justiça atualizou critérios de distribuição dos recursos do Fundo Nacional de Segurança Pública, priorizando municípios com maior índice de vulnerabilidade social e maior incidência de crimes violentos.',
     true,
     '**VERDADEIRO.** Alterações nos critérios do FNSP são comuns e geralmente divulgadas por portais oficiais.

**Sinais de credibilidade:**
- Política pública temática
- Anúncio técnico e mensurável
- Fonte institucional clara

**Fontes:**
- Ministério da Justiça: https://www.gov.br/mj/pt-br
- Agência Gov: https://agenciagov.ebc.com.br/
- G1 Política: https://g1.globo.com/politica/'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Câmara aprova projeto que cria incentivos fiscais para produção nacional de semicondutores',
     'A Câmara dos Deputados aprovou um projeto que concede incentivos fiscais para empresas que investirem na produção de semicondutores no Brasil, buscando fortalecer a indústria tecnológica nacional e reduzir a dependência de importações.',
     true,
     '**VERDADEIRO.** O tema é recorrente em políticas industriais brasileiras e já esteve em debate em diferentes governos.

**Sinais de credibilidade:**
- Tema alinhado com necessidade global de semicondutores
- Tramitação legislativa normal
- Coerência com estratégias industriais

**Fontes:**
- Câmara dos Deputados: https://www.camara.leg.br/
- Ministério da Indústria: https://www.gov.br/mdic/pt-br
- Agência Brasil: https://agenciabrasil.ebc.com.br/');

-- V4__Add_futebol_category.sql
-- adiciona categoria Futebol com 4 notícias sobre o tema

-- categoria: Futebol
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (2, 'Futebol', 'Notícias e curiosidades sobre o mundo do futebol', 4, 10, 'assets/images/conclusaofutebol.jpg', 'assets/images/capafutebol.png');

-- notícias da categoria Futebol
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Técnico da Seleção Brasileira confirma volta de Marcelo para a Copa América',
     'O técnico Dorival Júnior anunciou durante entrevista coletiva nesta quarta-feira que o lateral-esquerdo Marcelo, atualmente sem clube, será convocado para a próxima Copa América. Segundo o treinador, o jogador de 36 anos manteve boa forma física e sua experiência será fundamental para o elenco. A convocação oficial acontece na próxima semana.',
     false,
     '**FALSO.** Esta notícia usa linguagem profissional mas contém informações falsas:

     **Sinais identificadores:**

     - **Informação facilmente verificável:** convocações oficiais da CBF são públicas e amplamente divulgadas
     - **Detalhes vagos:** "entrevista coletiva nesta quarta-feira" sem local ou horário específico
     - **Falta de contexto realista:** jogadores sem clube raramente são convocados para seleção
     - **Ausência de citações diretas:** não há fala literal do técnico
     - **Não menciona fonte primária:** não cita site oficial da CBF ou veículo que cobriu

     **Como verificar:**
     - Site oficial da CBF sempre anuncia convocações primeiro
     - Grandes portais esportivos (GE, ESPN, Fox Sports) cobrem simultaneamente
     - Técnicos não fazem anúncios isolados sem a CBF

     **Fontes:**
     - CBF - Confederação Brasileira de Futebol: https://www.cbf.com.br/
     - GloboEsporte: https://ge.globo.com/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Real Madrid acerta contratação de jovem promessa brasileira por 35 milhões de euros',
     'O Real Madrid fechou acordo com o Palmeiras pela contratação do meio-campista Luis Fernando, de 19 anos, por 35 milhões de euros. De acordo com jornal espanhol Marca, o jogador assinará contrato de cinco anos e se apresentará ao clube madridista em julho. O Palmeiras ficará com 20% de uma futura venda.',
     false,
     '**FALSO.** Notícia usa formato jornalístico convincente mas tem inconsistências:

     **Sinais identificadores:**

     - **Nome genérico inventado:** "Luis Fernando" sem sobrenome completo (jogadores profissionais têm registro oficial)
     - **Valores plausíveis mas não verificáveis:** 35 milhões de euros é quantia realista, mas não confirmada
     - **Cita veículo real (Marca):** mas informação não está realmente publicada lá
     - **Detalhes técnicos críveis:** percentual de venda futura, duração de contrato
     - **Data específica mas vaga:** "julho" sem dia exato

     **Como verificar:**
     - Checar site oficial do Real Madrid e Palmeiras
     - Buscar no site do jornal Marca citado
     - Grandes transferências são noticiadas por múltiplos veículos simultaneamente
     - Transferências oficiais aparecem em sites especializados (Transfermarkt)

     **Fontes:**
     - Real Madrid Oficial: https://www.realmadrid.com/
     - Palmeiras Oficial: https://www.palmeiras.com.br/
     - Transfermarkt: https://www.transfermarkt.com/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'FIFA anuncia mudança nas regras do impedimento a partir da próxima temporada',
     'A FIFA divulgou comunicado oficial informando alteração na regra do impedimento para a temporada 2025/2026. Segundo a nova diretriz, jogadores estarão em posição legal se qualquer parte do corpo estiver alinhada com o penúltimo defensor, não apenas os pés. A mudança visa reduzir polêmicas com o VAR e foi aprovada pela International Board em reunião realizada em Zurique.',
     false,
     '**FALSO.** Usa linguagem técnica e institucional mas informação é falsa:

     **Sinais identificadores:**

     - **Mudanças de regras são raras:** FIFA não altera regras fundamentais frequentemente
     - **Falta de repercussão:** mudança desse tipo teria cobertura massiva da imprensa mundial
     - **Detalhes semi-técnicos:** cita "International Board" corretamente mas informação falsa
     - **Comunicado não encontrado:** não há comunicado oficial no site da FIFA
     - **Data específica inventada:** reunião em Zurique não ocorreu

     **Como verificar:**
     - Site oficial da FIFA publica todas as mudanças de regras
     - IFAB (International Football Association Board) é órgão responsável
     - Mudanças de regras são noticiadas globalmente
     - Confederações nacionais (CBF, UEFA) republicam oficialmente

     **Fontes:**
     - FIFA Oficial: https://www.fifa.com/
     - IFAB - The International Football Association Board: https://www.theifab.com/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Estudo da Universidade de São Paulo revela que árbitros brasileiros erram 31% das marcações de pênalti',
     'Pesquisadores do Departamento de Educação Física da USP publicaram estudo analisando 500 partidas do Campeonato Brasileiro entre 2020 e 2024. A pesquisa, que utilizou tecnologia de análise de vídeo frame-by-frame, concluiu que 31% das penalidades marcadas pelos árbitros foram equivocadas. O estudo também apontou que times mandantes recebem 15% mais pênaltis favoráveis.',
     false,
     '**FALSO.** Formato acadêmico convincente mas pesquisa não existe:

     **Sinais identificadores:**

     - **Instituição real citada:** USP existe, mas estudo não
     - **Dados estatísticos específicos:** 31%, 15% - números criam falsa credibilidade
     - **Metodologia descrita:** "análise frame-by-frame" soa técnico
     - **Período de tempo definido:** 2020-2024 parece planejado
     - **Departamento real:** Educação Física da USP existe, mas não publicou isso

     **Como verificar:**
     - Buscar no portal de pesquisas da USP ou banco de teses
     - Estudos acadêmicos são publicados em revistas científicas indexadas
     - Pesquisas reais têm nomes de autores e links para publicação
     - CBF e imprensa esportiva divulgariam estudo desse impacto

     **Fontes:**
     - Portal USP de Pesquisa: https://www.usp.br/
     - Scielo Brasil: https://www.scielo.br/
     - CBF: https://www.cbf.com.br/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Confederação Sul-Americana anuncia torneio experimental com regra do gol de ouro para 2025',
     'A CONMEBOL divulgou nota oficial sobre a realização de um torneio experimental em janeiro de 2025, reunindo seleções sub-20 da América do Sul. A competição testará o retorno da regra do gol de ouro em prorrogações, extinta em 2004. Segundo o presidente da entidade, Alejandro Domínguez, o objetivo é avaliar se a regra pode reduzir o número de decisões por pênaltis.',
     false,
     '**FALSO.** Informação institucional falsa com aparência oficial:

     **Sinais identificadores:**

     - **Cita autoridade real:** Alejandro Domínguez é realmente presidente da CONMEBOL
     - **Regra histórica real:** gol de ouro realmente existiu até 2004
     - **Proposta plausível:** torneios experimentais existem no futebol
     - **Nota oficial inexistente:** não há tal comunicado no site da CONMEBOL
     - **Categoria específica:** sub-20 é categoria real e usada em testes

     **Como verificar:**
     - Site oficial da CONMEBOL publica todos os comunicados
     - Veículos especializados sul-americanos (TyC Sports, ESPN) noticiariam
     - CBF republicaria informação sobre seleção brasileira envolvida
     - Calendário de 2025 não inclui tal torneio

     **Fontes:**
     - CONMEBOL Oficial: https://www.conmebol.com/
     - CBF: https://www.cbf.com.br/
     - ESPN Brasil: https://www.espn.com.br/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Lionel Messi é eleito melhor jogador da Copa do Mundo FIFA 2022 no Catar',
    'A FIFA anunciou nesta terça-feira (20) que Lionel Messi foi eleito o melhor jogador da Copa do Mundo 2022, realizada no Catar. O craque argentino liderou sua seleção ao título mundial, marcando sete gols e distribuindo três assistências ao longo do torneio. Messi recebeu a Bola de Ouro da competição em cerimônia oficial após a final contra a França, vencida pela Argentina nos pênaltis por 4 a 2, após empate em 3 a 3 no tempo regulamentar e prorrogação.',
    true,
    '**VERDADEIRO.** Esta notícia apresenta características de cobertura jornalística esportiva profissional:

**Sinais de credibilidade:**
- **Evento histórico confirmado:** Copa do Mundo 2022 no Catar realmente aconteceu
- **Informações específicas verificáveis:** 7 gols, 3 assistências, placar final detalhado
- **Premiação oficial da FIFA:** Bola de Ouro é prêmio real da competição
- **Detalhes precisos da final:** empate 3 a 3, pênaltis 4 a 2
- **Tom factual e objetivo:** sem exageros ou apelos emocionais
- **Data e contexto claros:** cerimônia após a final

Premiações e resultados oficiais de Copas do Mundo são documentados pela FIFA e amplamente cobertos pela imprensa mundial.

**Fontes:**
- FIFA Oficial: https://www.fifa.com/
- GloboEsporte: https://ge.globo.com/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'CBF anuncia reformulação do calendário do futebol brasileiro para temporada 2024',
'A Confederação Brasileira de Futebol (CBF) divulgou nesta quinta-feira (7) o novo calendário do futebol brasileiro para 2024, com mudanças significativas nas datas de início e término das competições nacionais. O Campeonato Brasileiro da Série A começará em 13 de abril e terminará em 8 de dezembro. Já a Copa do Brasil terá início em 21 de fevereiro, com a final prevista para outubro. As alterações visam adequar o calendário brasileiro às janelas internacionais da FIFA e reduzir o desgaste dos atletas.',
true,
'**VERDADEIRO.** Esta notícia demonstra características de informação oficial confiável:

**Sinais de credibilidade:**

- **Fonte oficial identificada:** CBF é a entidade responsável pelo calendário
- **Datas específicas:** 13 de abril, 8 de dezembro, 21 de fevereiro - informações concretas
- **Justificativa plausível:** adequação às janelas FIFA e proteção aos atletas
- **Informações verificáveis:** calendários são públicos e consultáveis
- **Tom institucional:** linguagem formal apropriada para comunicado oficial
- **Contexto adequado:** menciona diferentes competições (Brasileirão, Copa do Brasil)

Calendários oficiais são sempre divulgados pela CBF e amplamente noticiados pela imprensa esportiva especializada.

**Fontes:**
- CBF - Confederação Brasileira de Futebol: https://www.cbf.com.br/
- ESPN Brasil: https://www.espn.com.br/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Manchester City é punido pela Premier League com multa de 10 milhões de libras por violações financeiras',
'A Premier League anunciou nesta segunda-feira (11) que o Manchester City foi multado em 10 milhões de libras por violações das regras de Fair Play Financeiro entre as temporadas 2015 e 2018. O clube inglês admitiu as infrações relacionadas a atrasos no fornecimento de informações financeiras solicitadas pela liga. Em comunicado oficial, o Manchester City afirmou que colaborou plenamente com as investigações e implementou novos procedimentos internos para garantir total conformidade com as regulamentações.',
true,
'**VERDADEIRO.** Esta notícia apresenta características de jornalismo esportivo sobre questões institucionais:

**Sinais de credibilidade:**

- **Informação oficial da liga:** Premier League é autoridade competente
- **Valor específico da multa:** 10 milhões de libras
- **Período definido:** temporadas 2015 a 2018
- **Natureza técnica da violação:** atrasos em documentação, não fraude grave
- **Posicionamento do clube:** menciona resposta oficial do Manchester City
- **Contexto regulatório:** Fair Play Financeiro é regra real do futebol europeu
- **Tom equilibrado:** apresenta informações sem sensacionalismo

Punições financeiras a clubes são oficialmente documentadas pelas ligas e federações, com ampla cobertura da imprensa especializada.

**Fontes:**
- Premier League Oficial: https://www.premierleague.com/
- Manchester City Oficial: https://www.mancity.com/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'Seleção Brasileira feminina vence Japão por 3 a 1 em amistoso internacional na Austrália',
'A Seleção Brasileira feminina de futebol derrotou o Japão por 3 a 1 em partida amistosa disputada nesta quinta-feira (16) em Melbourne, Austrália. Os gols da vitória foram marcados por Debinha aos 12 minutos do primeiro tempo, Ary Borges aos 34, e Geyse aos 42 da etapa final. O Japão descontou com Miyazawa aos 28 do segundo tempo. A técnica Pia Sundhage utilizou o amistoso para testar novas opções táticas visando os próximos compromissos da equipe em competições oficiais.',
true,
'**VERDADEIRO.** Esta notícia demonstra características de cobertura esportiva profissional:

**Sinais de credibilidade:**

- **Detalhes específicos do jogo:** placar, autoras dos gols, minutos exatos
- **Local e data precisos:** Melbourne, Austrália, "quinta-feira (16)"
- **Contexto da partida:** amistoso internacional
- **Menção à comissão técnica:** Pia Sundhage é técnica real da seleção
- **Objetivo declarado:** teste de opções táticas - informação realista
- **Linguagem jornalística objetiva:** sem adjetivos exagerados
- **Informações verificáveis:** resultados de jogos oficiais são públicos

Jogos de seleções nacionais são amplamente documentados pelas confederações (CBF, FIFA) e cobertos pela mídia esportiva internacional.

**Fontes:**
- CBF - Confederação Brasileira de Futebol: https://www.cbf.com.br/
- FIFA: https://www.fifa.com/'),

    ((SELECT id FROM categorias WHERE nome = 'Futebol'), 'UEFA Champions League implementa novo formato de disputa a partir da temporada 2024/2025',
'A UEFA confirmou oficialmente a mudança no formato da Champions League para a temporada 2024/2025. A principal competição de clubes da Europa passará de 32 para 36 equipes e abandonará o tradicional sistema de grupos. No novo modelo, todas as equipes disputarão uma fase de liga única, enfrentando oito adversários diferentes. Os oito primeiros classificados avançam diretamente às oitavas de final, enquanto times entre a 9ª e 24ª posições disputarão playoffs para definir os demais classificados.',
true,
'**VERDADEIRO.** Esta notícia apresenta características de informação institucional oficial:

**Sinais de credibilidade:**

- **Mudança real anunciada pela UEFA:** reforma no formato foi oficialmente confirmada
- **Detalhes específicos do novo sistema:** 36 times, fase de liga única, 8 jogos
- **Mecânica de classificação clara:** top 8 direto, 9º-24º em playoffs
- **Temporada específica:** 2024/2025
- **Tom institucional:** linguagem formal apropriada para anúncio oficial
- **Informação facilmente verificável:** UEFA divulgou amplamente em seu site oficial
- **Cobertura massiva:** todos os grandes veículos esportivos noticiaram

Mudanças em competições oficiais da UEFA são documentadas oficialmente e amplamente divulgadas pela imprensa esportiva internacional.

**Fontes:**
- UEFA Oficial: https://www.uefa.com/
- ESPN Brasil: https://www.espn.com.br/');

-- Categoria: Biologia
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (3, 'Biologia', 'Notícias sobre ciências biológicas, saúde e descobertas científicas', 4, 10, 'assets/images/conclusaobiologia.jpg', 'assets/images/capabiologia.png');

-- notícias da categoria Biologia
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Estudo da Fiocruz indica que jejum intermitente de 18 horas aumenta longevidade em 22%',
     'Pesquisadores da Fundação Oswaldo Cruz publicaram estudo de acompanhamento de 3.200 voluntários ao longo de seis anos, indicando que a prática do jejum intermitente de 18 horas diárias está associada a aumento de 22% na expectativa de vida. Segundo a pesquisa, o jejum prolongado ativa mecanismos celulares de autofagia que retardam o envelhecimento. O estudo foi coordenado pela Dra. Marina Santos e os resultados preliminares foram apresentados no Congresso Brasileiro de Gerontologia.',
     false,
     '**FALSO.** Esta notícia usa formato científico convincente mas contém informações falsas:

     **Sinais identificadores:**

     - **Instituição real citada:** Fiocruz existe, mas estudo não foi publicado
     - **Dados estatísticos específicos:** 22%, 3.200 voluntários, 6 anos - números criam falsa credibilidade
     - **Nome genérico inventado:** "Dra. Marina Santos" sem registro profissional verificável
     - **Conceito científico real:** autofagia é processo real, mas aplicação é distorcida
     - **Evento não verificável:** "Congresso Brasileiro de Gerontologia" sem data ou local específico
     - **Percentual exagerado:** 22% de aumento na longevidade é afirmação extraordinária que exigiria evidência extraordinária

     **Como verificar:**
     - Buscar no portal de pesquisas da Fiocruz
     - Estudos sobre longevidade levam décadas, não 6 anos
     - Pesquisas legítimas são publicadas em revistas científicas indexadas
     - Nome completo de pesquisadores pode ser verificado na Plataforma Lattes

     **Fontes:**
     - Fiocruz: https://portal.fiocruz.br/
     - Plataforma Lattes CNPq: http://lattes.cnpq.br/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Ministério da Saúde recomenda substituição da vacina tríplice viral por nova formulação em 2025',
     'O Ministério da Saúde anunciou nesta terça-feira que pretende substituir a vacina tríplice viral (sarampo, caxumba e rubéola) por uma nova formulação quadrivalente que incluirá proteção contra varicela. Segundo nota técnica divulgada pela pasta, a mudança será implementada gradualmente a partir de março de 2025 e não afetará o calendário de vacinação infantil. A nova vacina foi desenvolvida pelo Instituto Butantan em parceria com laboratório europeu.',
     false,
     '**FALSO.** Notícia institucional falsa com aparência oficial:

     **Sinais identificadores:**

     - **Órgão real citado:** Ministério da Saúde existe, mas comunicado não
     - **Informação técnica plausível:** vacina quadrivalente é conceito real
     - **Instituto real mencionado:** Butantan existe, mas não desenvolveu tal vacina
     - **Data futura específica:** março de 2025 - cria urgência mas não é verificável agora
     - **"Nota técnica":** documento oficial que não existe
     - **Parceria vaga:** "laboratório europeu" não identificado

     **Como verificar:**
     - Site oficial do Ministério da Saúde publica todos os comunicados
     - Mudanças no calendário vacinal são amplamente divulgadas
     - Instituto Butantan divulga suas pesquisas oficialmente
     - ANVISA precisa aprovar qualquer nova vacina

     **Fontes:**
     - Ministério da Saúde: https://www.gov.br/saude/
     - Instituto Butantan: https://butantan.gov.br/
     - ANVISA: https://www.gov.br/anvisa/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Pesquisa da USP identifica proteína que previne Alzheimer em população ribeirinha da Amazônia',
     'Cientistas do Instituto de Biociências da Universidade de São Paulo identificaram uma variante genética presente em 34% da população ribeirinha do Amazonas que produz uma proteína específica associada à proteção contra desenvolvimento de Alzheimer. O estudo, que analisou DNA de 1.800 indivíduos, sugere que a dieta rica em peixes de água doce pode ter influenciado a seleção natural dessa característica. A descoberta foi publicada na revista Brain Research e abre perspectivas para desenvolvimento de terapias preventivas.',
     false,
     '**FALSO.** Formato acadêmico convincente mas pesquisa não existe:

     **Sinais identificadores:**

     - **Instituição real:** USP e Instituto de Biociências existem
     - **Revista científica real:** Brain Research é publicação legítima
     - **Percentual específico:** 34% da população - dado cria credibilidade falsa
     - **Hipótese plausível:** dieta influenciando genética é conceito real
     - **Tamanho de amostra realista:** 1.800 indivíduos
     - **Pesquisa não encontrada:** não há publicação com esse conteúdo

     **Como verificar:**
     - Buscar na revista Brain Research citada
     - Consultar portal de pesquisas da USP
     - Grandes descobertas científicas têm cobertura ampla da mídia
     - Pesquisadores reais são nomeados em estudos legítimos

     **Fontes:**
     - USP: https://www.usp.br/
     - Scielo Brasil: https://www.scielo.br/
     - PubMed: https://pubmed.ncbi.nlm.nih.gov/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'OMS atualiza recomendação sobre tempo de sono ideal para adultos de 7 para 8 horas diárias',
     'A Organização Mundial da Saúde (OMS) divulgou atualização de suas diretrizes sobre sono, aumentando de 7 para 8 horas a recomendação de sono diário para adultos entre 18 e 64 anos. A mudança, baseada em metanálise de 127 estudos recentes, indica que 8 horas de sono estão associadas a 18% menos risco de doenças cardiovasculares. A nova diretriz foi publicada no boletim oficial da OMS e entrará em vigor a partir de janeiro de 2025.',
     false,
     '**FALSO.** Informação institucional falsa com detalhes convincentes:

     **Sinais identificadores:**

     - **Organização real:** OMS é autoridade global em saúde
     - **Mudança sutil:** de 7 para 8 horas parece pequena e plausível
     - **Metanálise citada:** 127 estudos - número específico cria credibilidade
     - **Redução de risco:** 18% é percentual realista, não absurdo
     - **Boletim oficial:** documento que não existe
     - **Data futura:** janeiro de 2025 dificulta verificação imediata

     **Como verificar:**
     - Site oficial da OMS publica todas as diretrizes
     - Mudanças em recomendações internacionais têm ampla cobertura
     - OMS disponibiliza documentos técnicos gratuitamente
     - Diretrizes atuais já recomendam 7-9 horas (não mudou)

     **Fontes:**
     - Organização Mundial da Saúde: https://www.who.int/
     - Ministério da Saúde: https://www.gov.br/saude/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Laboratório paulista desenvolve teste de sangue capaz de detectar cinco tipos de câncer com 92% de precisão',
     'O Laboratório Fleury anunciou o desenvolvimento de teste sanguíneo que detecta simultaneamente cinco tipos de câncer (pulmão, mama, cólon, pâncreas e próstata) com taxa de precisão de 92%. O exame, chamado OncoDetect-5, utiliza técnica de biópsia líquida para identificar marcadores tumorais e DNA circulante. Segundo o diretor científico Dr. Roberto Ferreira, o teste estará disponível na rede privada a partir de abril de 2025 ao custo estimado de R$ 1.200. A ANVISA aprovou o exame em fase de teste clínico expandido.',
     false,
     '**FALSO.** Notícia corporativa falsa com detalhes técnicos convincentes:

     **Sinais identificadores:**

     - **Laboratório real:** Fleury é rede de laboratórios legítima
     - **Tecnologia real:** biópsia líquida e marcadores tumorais existem
     - **Nome comercial inventado:** "OncoDetect-5" soa profissional
     - **Taxa de precisão:** 92% é alta mas plausível
     - **Preço específico:** R$ 1.200 parece realista
     - **Aprovação ANVISA falsa:** não há registro desse teste
     - **Nome genérico:** "Dr. Roberto Ferreira" não é verificável

     **Como verificar:**
     - Site do Fleury divulga seus exames disponíveis
     - ANVISA publica aprovações de novos testes
     - Avanços médicos desse porte têm cobertura massiva
     - Buscar em revistas médicas científicas

     **Fontes:**
     - ANVISA: https://www.gov.br/anvisa/
     - Fleury: https://www.fleury.com.br/
     - Conselho Federal de Medicina: https://portal.cfm.org.br/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'CRISPR-Cas9: Terapia genética recebe aprovação da FDA para tratamento de anemia falciforme',
    'A agência reguladora norte-americana FDA (Food and Drug Administration) aprovou o primeiro tratamento baseado em edição genética CRISPR para anemia falciforme e beta-talassemia. A terapia, desenvolvida pela Vertex Pharmaceuticals e CRISPR Therapeutics, modifica células-tronco do paciente para produzir hemoglobina fetal funcional. Em ensaios clínicos, 93% dos pacientes com anemia falciforme não apresentaram crises de dor debilitantes por pelo menos 12 meses após o tratamento. A aprovação representa marco histórico na medicina de precisão.',
    true,
    '**VERDADEIRO.** Esta notícia apresenta características de divulgação científica e regulatória confiável:

**Sinais de credibilidade:**
- **Agência reguladora oficial:** FDA é autoridade reconhecida mundialmente
- **Empresas farmacêuticas reais:** Vertex e CRISPR Therapeutics existem e desenvolveram a terapia
- **Tecnologia real e específica:** CRISPR-Cas9 é técnica de edição genética consolidada
- **Dados de eficácia:** 93% dos pacientes, 12 meses - informações de ensaios clínicos reais
- **Contexto histórico apropriado:** primeira aprovação de CRISPR é fato verificável
- **Linguagem técnica precisa:** células-tronco, hemoglobina fetal, beta-talassemia
- **Tom equilibrado:** apresenta dados sem promessas milagrosas

Aprovações da FDA são públicas, documentadas e amplamente noticiadas pela imprensa científica internacional.

**Fontes:**
- FDA: https://www.fda.gov/
- Nature Medicine: https://www.nature.com/nm/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Cientistas brasileiros sequenciam genoma completo de espécie de planta endêmica da Mata Atlântica',
'Pesquisadores da Universidade Estadual de Campinas (Unicamp) concluíram o sequenciamento completo do genoma da Araucária angustifolia, também conhecida como pinheiro-do-paraná, árvore ameaçada de extinção da Mata Atlântica. O trabalho, publicado na revista científica Genome Biology and Evolution, identificou 32.000 genes e revelou adaptações genéticas únicas para sobrevivência em altitudes elevadas. A pesquisa pode auxiliar programas de conservação e reflorestamento da espécie.',
true,
'**VERDADEIRO.** Esta notícia demonstra características de divulgação científica acadêmica confiável:

**Sinais de credibilidade:**

- **Instituição acadêmica reconhecida:** Unicamp é universidade de referência em pesquisa
- **Espécie real e relevante:** Araucária angustifolia é árvore nativa brasileira ameaçada
- **Revista científica legítima:** Genome Biology and Evolution é publicação indexada
- **Dados específicos:** 32.000 genes identificados
- **Contexto científico:** adaptações genéticas para altitudes - informação técnica apropriada
- **Aplicação prática:** conservação e reflorestamento - objetivo realista
- **Linguagem científica adequada:** sequenciamento, genoma, adaptações genéticas

Projetos de sequenciamento genômico são públicos, publicados em revistas científicas e divulgados por instituições de pesquisa.

**Fontes:**
- Unicamp: https://www.unicamp.br/
- Genome Biology and Evolution: https://academic.oup.com/gbe'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'OMS declara fim da emergência global de saúde pública relacionada à COVID-19',
'A Organização Mundial da Saúde (OMS) anunciou oficialmente em maio de 2023 o fim da Emergência de Saúde Pública de Importância Internacional (ESPII) relacionada à COVID-19, declarada originalmente em janeiro de 2020. O diretor-geral Tedros Adhanom Ghebreyesus destacou que a decisão não significa que a COVID-19 deixou de ser ameaça à saúde global, mas reflete a redução de hospitalizações, mortes e pressão sobre sistemas de saúde. A OMS mantém monitoramento contínuo de variantes e recomenda continuidade da vacinação para grupos de risco.',
true,
'**VERDADEIRO.** Esta notícia apresenta características de comunicação institucional oficial:

**Sinais de credibilidade:**

- **Organização internacional oficial:** OMS é autoridade máxima em saúde global
- **Data específica:** maio de 2023 - momento histórico verificável
- **Termo técnico correto:** ESPII (Emergência de Saúde Pública de Importância Internacional)
- **Autoridade nomeada:** Tedros Adhanom Ghebreyesus é diretor-geral real da OMS
- **Contexto equilibrado:** esclarece que pandemia não acabou completamente
- **Recomendações continuadas:** vacinação para grupos de risco
- **Tom institucional:** linguagem formal apropriada para comunicado oficial

Declarações oficiais da OMS são amplamente documentadas, transmitidas ao vivo e cobertas por toda imprensa internacional.

**Fontes:**
- Organização Mundial da Saúde: https://www.who.int/
- Ministério da Saúde: https://www.gov.br/saude/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Estudo publicado na The Lancet associa consumo excessivo de carne processada a maior risco de diabetes tipo 2',
'Pesquisa internacional envolvendo 1,9 milhão de participantes de 20 países, publicada na revista The Lancet, encontrou associação significativa entre consumo diário de carne processada e aumento de 15% no risco de desenvolver diabetes tipo 2. O estudo, liderado pela Universidade de Cambridge, analisou dados coletados ao longo de 10 anos e ajustou resultados para fatores como peso corporal, atividade física e histórico familiar. Pesquisadores recomendam limitação do consumo de bacon, salsicha e embutidos a menos de 50 gramas por dia.',
true,
'**VERDADEIRO.** Esta notícia demonstra características de divulgação de pesquisa epidemiológica confiável:

**Sinais de credibilidade:**

- **Revista científica de alto impacto:** The Lancet é uma das mais prestigiadas do mundo
- **Instituição acadêmica reconhecida:** Universidade de Cambridge tem reputação estabelecida
- **Amostra robusta:** 1,9 milhão de participantes, 20 países - dados impressionantes mas verificáveis
- **Período de acompanhamento:** 10 anos - tempo adequado para estudos epidemiológicos
- **Percentual moderado:** 15% de aumento - não exagera riscos
- **Metodologia mencionada:** ajuste para fatores confundidores é prática científica padrão
- **Recomendação específica:** menos de 50g/dia - orientação clara e mensurável

Estudos epidemiológicos em grandes revistas são revisados por pares e amplamente discutidos pela comunidade científica.

**Fontes:**
- The Lancet: https://www.thelancet.com/
- Universidade de Cambridge: https://www.cam.ac.uk/'),

    ((SELECT id FROM categorias WHERE nome = 'Biologia'), 'Vacina contra malária desenvolvida pela Universidade de Oxford alcança 77% de eficácia em ensaios clínicos',
'A vacina R21/Matrix-M contra malária, desenvolvida pela Universidade de Oxford em parceria com o Serum Institute of India, demonstrou eficácia de 77% em ensaios clínicos de fase 3 conduzidos com 4.800 crianças em Burkina Faso. Os resultados, publicados na revista The Lancet, superam a meta de 75% estabelecida pela Organização Mundial da Saúde. A malária mata aproximadamente 600.000 pessoas anualmente, a maioria crianças africanas menores de cinco anos. A vacina recebeu recomendação da OMS em outubro de 2023 para uso em regiões endêmicas.',
true,
'**VERDADEIRO.** Esta notícia apresenta características de divulgação científica e de saúde pública confiável:

**Sinais de credibilidade:**

- **Instituição acadêmica reconhecida:** Universidade de Oxford é referência global
- **Parceria identificada:** Serum Institute of India é fabricante real de vacinas
- **Nome técnico específico:** R21/Matrix-M - nomenclatura científica verificável
- **Dados de ensaio clínico:** fase 3, 4.800 crianças, 77% eficácia - informações concretas
- **Local específico:** Burkina Faso - país africano real onde malária é endêmica
- **Revista científica prestigiada:** The Lancet publicou os resultados
- **Contexto epidemiológico:** 600.000 mortes anuais - dado real da OMS
- **Aprovação oficial:** recomendação da OMS em outubro de 2023 - fato verificável

Desenvolvimento de vacinas contra doenças negligenciadas é amplamente documentado por organizações internacionais de saúde.

**Fontes:**
- Organização Mundial da Saúde: https://www.who.int/
- Universidade de Oxford: https://www.ox.ac.uk/
- The Lancet: https://www.thelancet.com/');
INSERT INTO conquistas (nome, descricao, caminho_imagem_completa, criterio, tipo, valor_requerido)
VALUES (
    'Primeiros Passos',
    'Acumulou 40 pontos respondendo questões corretamente!',
    '🏆',
    'Acumular pontos totais',
    'PONTOS_TOTAIS',
    40
);


