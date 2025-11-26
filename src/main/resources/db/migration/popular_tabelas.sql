-- categoria: Política
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (1, 'Política', 'Notícias sobre política nacional e internacional', 4, 10, 'assets/images/conclusaopolitica.jpg', 'assets/images/CategoriaPolitica.png');

-- notícias da categoria Política
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Senado aprova projeto que estabelece novas regras para financiamento de campanhas eleitorais',
     'O Senado Federal aprovou nesta terça-feira (12) projeto de lei que estabelece novas diretrizes para o financiamento de campanhas eleitorais no Brasil. A proposta, que teve 54 votos favoráveis e 23 contrários, limita doações de pessoas físicas a 10% da renda anual declarada e proíbe contribuições de empresas condenadas por corrupção nos últimos 8 anos. O texto segue agora para sanção presidencial e, se aprovado, entrará em vigor nas eleições de 2026.',
     true,
     '**VERDADEIRO.** Esta notícia é internamente coerente e descreve um processo legislativo plausível:

**Por que faz sentido:**
- Placar de votação realista (54 a 23) - não é unânime nem absurdo
- Limites percentuais plausíveis (10% da renda anual)
- Prazo de vedação coerente (8 anos)
- Tramitação correta: Senado → Presidência → vigência futura
- Ano de implementação lógico (2026 - próximas eleições)

**Elementos de coerência:**
- Processo democrático respeitado (votação no Congresso)
- Medida anticorrupção factível
- Não promete mudanças imediatas ou radicais
- Percentuais e números dentro da realidade'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Congresso aprova emenda constitucional que aumenta mandato presidencial de 4 para 6 anos',
     'Foi aprovada ontem no Congresso Nacional, por unanimidade de 513 votos, emenda constitucional que altera o mandato presidencial de 4 para 6 anos. A mudança vale a partir de janeiro e se aplica ao atual presidente. Segundo líderes partidários, a medida foi necessária para dar mais tempo aos projetos governamentais. A emenda foi votada em sessão extraordinária e entra em vigor imediatamente após publicação no Diário Oficial.',
     false,
     '**FALSO.** Esta notícia contém múltiplas impossibilidades constitucionais:

**Os erros estão aqui:**
- Emenda constitucional exige aprovação em DOIS TURNOS em cada casa (Câmara e Senado)
- Precisa de 3/5 dos votos (308 deputados + 49 senadores), não "unanimidade de 513"
- "513 votos" é o total de deputados - impossível ter esse número em uma votação conjunta
- PEC não pode valer para mandato em curso (princípio da irretroatividade)
- Mudança de mandato presidencial não pode ter efeito imediato

**Por que isso é impossível:**
- Alteração de cláusula pétrea exigiria processo complexo de décadas
- Mandato presidencial não pode ser estendido retroativamente
- Procedimento descrito ignora completamente o rito constitucional
- Aprovação "por unanimidade" em matéria polêmica é inverossímil'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Presidente veta artigo de lei que estabelecia limite para reajuste de combustíveis',
     'O presidente da República vetou parcialmente projeto de lei aprovado pelo Congresso Nacional que estabelecia teto para reajuste mensal de combustíveis. O veto atingiu especificamente o artigo 7º, que limitava aumentos a 3% ao mês. Em mensagem ao Congresso, o Executivo argumentou que a medida interferiria indevidamente na política de preços da Petrobras e poderia causar desabastecimento. O Congresso pode derrubar o veto com maioria absoluta em sessão conjunta.',
     true,
     '**VERDADEIRO.** Esta notícia descreve corretamente o processo de veto presidencial:

**Por que faz sentido:**
- Veto parcial é previsto constitucionalmente
- Presidente pode vetar artigos específicos (artigo 7º mencionado)
- Justificativa técnica coerente (interferência em política de preços)
- Percentual plausível (3% ao mês)
- Processo democrático respeitado: Congresso pode derrubar veto
- Maioria absoluta é o quórum correto para derrubar veto

**Elementos de coerência:**
- Separação de poderes funciona (Legislativo aprova, Executivo veta)
- Argumento técnico versus político
- Consequência prevista realista (desabastecimento)
- Próximos passos bem definidos'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'STF determina que todas as leis aprovadas pelo Congresso em 2024 sejam revogadas automaticamente',
     'O Supremo Tribunal Federal decidiu por 11 votos a 0 que todas as leis federais aprovadas pelo Congresso Nacional durante o ano de 2024 sejam automaticamente revogadas. A decisão, tomada em sessão plenária extraordinária, baseia-se em suposta inconstitucionalidade processual detectada no regimento interno da Câmara. Segundo o ministro relator, as 247 leis aprovadas no período deixarão de valer a partir da próxima segunda-feira, incluindo o orçamento federal de 2025.',
     false,
     '**FALSO.** Esta notícia descreve uma impossibilidade jurídica e constitucional:

**Os erros estão aqui:**
- STF não pode revogar leis "em bloco" por decisão administrativa
- Cada lei precisa ser questionada individualmente via ADI (Ação Direta de Inconstitucionalidade)
- Impossível declarar 247 leis inconstitucionais de uma vez
- Revogação do orçamento federal causaria colapso do Estado
- STF não julga "inconstitucionalidade processual" do Regimento da Câmara dessa forma

**Por que isso é impossível:**
- Separação dos Poderes: STF não legisla nem revoga leis administrativamente
- Controle de constitucionalidade é feito caso a caso
- Efeito seria caótico e inviabilizaria o funcionamento do país
- Votação unânime (11 a 0) em decisão tão extrema é inverossímil'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Tribunal Superior Eleitoral divulga calendário oficial das eleições municipais de 2024',
     'O Tribunal Superior Eleitoral (TSE) publicou nesta quinta-feira a resolução com o calendário completo das eleições municipais de 2024. As convenções partidárias para escolha de candidatos ocorrerão entre 20 de julho e 5 de agosto. O registro de candidaturas vai até 15 de agosto, e a propaganda eleitoral está liberada a partir de 16 de agosto. O primeiro turno está marcado para 6 de outubro, e eventual segundo turno acontecerá em 27 de outubro.',
     true,
     '**VERDADEIRO.** Esta notícia apresenta informações coerentes sobre processo eleitoral:

**Por que faz sentido:**
- TSE é o órgão competente para definir calendário eleitoral
- Datas seguem sequência lógica: convenções → registro → propaganda → votação
- Prazos entre etapas são razoáveis (15-20 dias)
- Primeiro turno em outubro é padrão das eleições municipais
- Intervalo de 21 dias entre turnos é o previsto em lei
- Propaganda depois do registro faz sentido legal

**Elementos de coerência:**
- Processo organizado em etapas sequenciais
- Prazos compatíveis com organização eleitoral
- Todos os marcos importantes mencionados
- Estrutura típica de eleições brasileiras'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Nova lei determina que projetos aprovados na Câmara passem automaticamente no Senado sem votação',
     'Entrou em vigor nesta semana lei complementar que altera o processo legislativo brasileiro. A partir de agora, projetos de lei aprovados por maioria qualificada na Câmara dos Deputados (3/5 dos votos) passam automaticamente no Senado Federal, sem necessidade de nova votação. A medida, segundo seus defensores, visa acelerar a tramitação de propostas e reduzir custos do Legislativo. O Senado permanece com poder de veto, mas apenas para projetos de emenda constitucional.',
     false,
     '**FALSO.** Esta notícia descreve mudança que viola a estrutura constitucional do Congresso:

**Os erros estão aqui:**
- Câmara e Senado são casas independentes - ambas DEVEM votar cada projeto
- Não existe "aprovação automática" entre as casas legislativas
- Lei complementar não pode alterar processo previsto na Constituição
- A Constituição exige bicameralismo (duas votações independentes)
- Contraditório: diz que Senado "permanece com poder de veto" mas não vota

**Por que isso é impossível:**
- Bicameralismo é princípio fundamental (cláusula pétrea implícita)
- Senado representa Estados, Câmara representa população - papéis distintos
- Eliminar votação no Senado seria golpe institucional
- Mudança dessa magnitude exigiria nova Constituição'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Governo federal anuncia programa de regularização fundiária para comunidades tradicionais',
     'O Ministério do Desenvolvimento Agrário lançou nesta segunda-feira programa de regularização fundiária voltado para comunidades quilombolas, indígenas e ribeirinhas. A iniciativa prevê titulação de 450 mil hectares em 12 estados ao longo dos próximos três anos. O programa conta com orçamento de R$ 380 milhões e envolve parcerias com INCRA, FUNAI e governos estaduais. As primeiras titulações devem ocorrer ainda no primeiro semestre deste ano em comunidades do Pará e Maranhão.',
     true,
     '**VERDADEIRO.** Esta notícia descreve política pública plausível e bem estruturada:

**Por que faz sentido:**
- Ministério adequado para o tema (Desenvolvimento Agrário)
- Área razoável (450 mil hectares em 12 estados)
- Prazo exequível (3 anos)
- Orçamento dentro da realidade fiscal (R$ 380 milhões)
- Órgãos competentes envolvidos (INCRA, FUNAI)
- Começo gradual em estados específicos (Pará e Maranhão)

**Elementos de coerência:**
- Programa bem delimitado em escopo e prazo
- Parcerias institucionais coerentes
- Valores proporcionais à área e complexidade
- Estados mencionados têm histórico dessas comunidades'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Câmara aprova PEC que torna obrigatório referendo popular para qualquer aumento de impostos',
     'A Câmara dos Deputados aprovou em segundo turno PEC que estabelece consulta popular obrigatória antes de qualquer criação ou aumento de tributo no país. Pela proposta, todo aumento de imposto, taxa ou contribuição deve ser submetido a referendo nacional, marcado em até 45 dias após aprovação no Congresso. Se a população votar "não", o aumento é automaticamente cancelado. A PEC teve 410 votos favoráveis e agora segue para o Senado. Economistas estimam que cada referendo custará R$ 2,3 bilhões aos cofres públicos.',
     false,
     '**FALSO.** Esta notícia propõe mecanismo impraticável e que inviabilizaria o funcionamento do Estado:

**Os erros estão aqui:**
- Referendo para CADA alteração tributária tornaria país ingovernável
- Custo de R$ 2,3 bilhões por consulta seria proibitivo
- Orçamento precisaria ser aprovado anualmente por voto popular (inviável)
- 45 dias é prazo curto demais para organizar referendo nacional
- Impossibilidade prática: centenas de normas tributárias por ano

**Por que isso é impossível:**
- Sistema tributário muda constantemente (ajustes técnicos frequentes)
- Referendos custam caro e são lentos - incompatível com gestão fiscal
- Medida tornaria país incapaz de reagir a crises econômicas
- Democracia representativa ficaria esvaziada
- Proposta populista que ignora realidade administrativa'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Supremo Tribunal Federal julga constitucional cobrança de pedágio em rodovias federais',
     'O Supremo Tribunal Federal decidiu por 7 votos a 4 que a cobrança de pedágio em rodovias federais concedidas à iniciativa privada é constitucional. O julgamento analisou ação que questionava a legalidade da cobrança de tarifa por uso de estradas públicas. Segundo o relator, a cobrança é legítima quando associada a investimentos em manutenção e melhorias. A decisão mantém vigentes os 72 contratos de concessão atualmente ativos no país, que totalizam cerca de 18 mil quilômetros de rodovias pedagiadas.',
     true,
     '**VERDADEIRO.** Esta notícia descreve decisão judicial coerente sobre tema infraestrutural:

**Por que faz sentido:**
- STF julga questões de constitucionalidade (papel correto)
- Placar dividido (7 a 4) é realista para tema polêmico
- Argumento jurídico coerente (cobrança vinculada a melhorias)
- Número plausível de contratos (72 concessões)
- Quilometragem razoável (18 mil km)
- Decisão tem efeito concreto (mantém contratos vigentes)

**Elementos de coerência:**
- STF atuando em sua competência constitucional
- Votação não-unânime reflete complexidade do tema
- Justificativa técnica (investimento em infraestrutura)
- Impacto prático definido e mensurável'),

    ((SELECT id FROM categorias WHERE nome = 'Política'),
     'Presidente assina decreto que suspende todas as eleições municipais até resolução de reforma política',
     'O presidente da República assinou decreto presidencial na manhã de hoje determinando o adiamento por tempo indeterminado de todas as eleições municipais previstas para este ano. Segundo nota oficial do Planalto, a medida é necessária para que o Congresso conclua a reforma política em andamento. Os atuais prefeitos e vereadores terão mandatos automaticamente prorrogados até que novo calendário eleitoral seja definido. A decisão foi tomada após reunião com ministros do STF e presidentes da Câmara e Senado.',
     false,
     '**FALSO.** Esta notícia descreve ato que viola frontalmente a Constituição:

**Os erros estão aqui:**
- Presidente NÃO pode suspender eleições por decreto
- Eleições têm data constitucional - não podem ser adiadas unilateralmente
- Prorrogação de mandatos sem eleição é inconstitucional
- Decreto presidencial não pode alterar prazo eleitoral
- Mesmo reunião com STF e Congresso não legitima ato anticonstitucional

**Por que isso é impossível:**
- Suspensão de eleições caracteriza golpe de Estado
- Calendário eleitoral é definido por lei, não por decreto
- Prorrogação de mandatos viola alternância democrática
- STF derrubaria decreto imediatamente
- Medida seria crime de responsabilidade do presidente
- Impossível haver "reunião prévia" validando golpe institucional');

-- categoria: Futebol
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (2, 'Futebol', 'Notícias e curiosidades sobre o mundo do futebol', 4, 10, 'assets/images/conclusaofutebol.jpg', 'assets/images/capafutebol.png');

-- notícias da categoria Futebol
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Futebol'),
    'CBF anuncia criação da Copa Nacional Sub-23 com 32 clubes a partir de 2026',
    'A Confederação Brasileira de Futebol anunciou nesta sexta-feira (8) a criação da Copa Nacional Sub-23, torneio que reunirá 32 clubes de todas as regiões do país a partir da temporada 2026. A competição terá formato eliminatório, com jogos únicos até a semifinal e final em estádio previamente definido. Segundo a CBF, o objetivo é aumentar a transição entre categorias de base e o profissional.',
    true,
    '**VERDADEIRO.** A notícia é internamente coerente e apresenta medidas comuns adotadas por federações:

    **Por que faz sentido:**
    - Criação de torneio Sub-23 é plausível e já existe em outros países
    - Número de clubes (32) segue padrão típico de copas
    - Formato eliminatório com jogo único é comum
    - Justificativa coerente: transição da base para o profissional'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Estádios da Série B receberão novo sistema de iluminação LED obrigatório a partir de 2027',
 'A Liga do Futebol Brasileiro (LFB) confirmou que todos os estádios utilizados na Série B deverão, a partir de 2027, adotar sistemas de iluminação LED com intensidade mínima de 1.600 lux. A medida visa padronizar transmissões televisivas e melhorar a visibilidade para torcedores e jogadores. Clubes terão dois anos para adequação.',
 true,
 '**VERDADEIRO.** O texto descreve uma regulamentação plausível e coerente:

 **Elementos de coerência:**
 - Exigência de iluminação é comum em ligas profissionais
 - Intensidade de 1.600 lux é um valor técnico realista
 - Prazo de adequação de dois anos é padrão em mudanças estruturais
 - Objetivo alinhado à qualidade de transmissão'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Pesquisa aponta crescimento de 37% no público feminino em jogos da primeira divisão',
 'Um levantamento realizado pelo Instituto Esportivo Nacional (IEN) indicou aumento de 37% na presença de torcedoras em jogos da primeira divisão entre 2019 e 2024. O estudo analisou mais de 1,4 milhão de registros de ingressos cadastrados por CPF e identificou maior participação de mulheres em jogos noturnos e finais de semana.',
 true,
 '**VERDADEIRO.** A notícia é consistente e apresenta dados plausíveis:

 **Por que faz sentido:**
 - Uso de dados de ingressos por CPF é prática comum
 - Crescimento percentual moderado e plausível
 - Recorte temporal adequado (5 anos)
 - Instituição fictícia, porém verossímil (“Instituto Esportivo Nacional”)'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Clube anuncia tecnologia de sensores nas chuteiras para monitorar carga física dos atletas',
 'O Grêmio do Norte Esportivo anunciou a implementação de sensores integrados nas chuteiras dos atletas para medir carga física, velocidade de arranque e padrão de passada durante treinos. Os dados serão enviados em tempo real ao departamento de desempenho através de uma plataforma própria desenvolvida em parceria com uma startup de tecnologia esportiva.',
 true,
 '**VERDADEIRO.** Nada no texto contradiz práticas modernas:

 **Elementos coerentes:**
 - Sensores integrados já são usados em diversos esportes
 - Parceria com startup é estratégia comum
 - Coleta de dados de passada e arranque é realista
 - Clube fictício, porém plausível dentro do universo do jogo'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Federação estadual determina limite de 18 jogadores estrangeiros inscritos por temporada',
 'A Federação de Futebol do Centro-Sul aprovou, nesta segunda-feira (14), um regulamento que limita a inscrição de jogadores estrangeiros a 18 por clube em competições estaduais. A medida visa evitar disparidades entre equipes e incentivar o uso de atletas formados em categorias de base locais.',
 true,
 '**VERDADEIRO.** A regra é moderada e plausível:

 **Por que faz sentido:**
 - Federações têm autonomia para definir limites
 - Número de 18 não é excessivamente restritivo
 - Justificativa de equilíbrio competitivo é comum
 - Aplica-se apenas ao torneio estadual, o que é coerente'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Novo regulamento da Liga Norte permite dois gols válidos marcados simultaneamente na mesma jogada',
 'A Liga Norte de Futebol aprovou de forma inédita uma regra que permitirá a validação de dois gols marcados simultaneamente na mesma jogada, desde que a bola atravesse a linha em ambos os lances durante o período de vantagem. A entidade alega que a medida moderniza o esporte e cria novas possibilidades estratégicas para as equipes.',
 false,
 '**FALSO.** A notícia apresenta mudanças impossíveis dentro das regras do futebol:

 **Onde estão os erros:**
 - O futebol só utiliza uma bola em jogo, impossibilitando dois gols simultâneos
 - O conceito de “período de vantagem” não existe na regra oficial
 - Nenhuma federação pode criar regra que contradiga normas internacionais da IFAB
 - A ideia de validar dois gols é incompatível com a estrutura do esporte

 **Por que isso é impossível:**
 - As regras do futebol não permitem múltiplos gols na mesma jogada
 - Invenção de conceitos inexistentes indica falsificação
 - Alterações que desvirtuam o jogo jamais seriam aprovadas por qualquer liga'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Pesquisadores identificam novo tipo de gramado híbrido que reduz em 70% o consumo de água durante jogos',
 'Cientistas da Universidade Federal Atlântica anunciaram a criação de um gramado híbrido composto por fibras naturais e polímeros absorventes capazes de reduzir em até 70% o consumo de água durante os jogos. O composto, chamado de “TurfBio-7”, libera umidade de forma automática toda vez que a temperatura cai abaixo de 12°C durante a partida.',
 false,
 '**FALSO.** A notícia tenta soar científica, mas contém afirmações incompatíveis com o funcionamento de gramados esportivos:

 **Onde estão os erros:**
 - Gramados não liberam umidade automaticamente com base apenas em temperatura
 - O consumo de água durante um jogo não funciona dessa forma
 - A faixa de 12°C não tem qualquer relação com acionamento de irrigação
 - A tecnologia descrita não existe em horticultura esportiva

 **Por que isso é impossível:**
 - Sistemas de irrigação dependem de sensores específicos e não atuam por polímeros
 - A redução de 70% é exagerada e irreal
 - O processo descrito contradiz princípios básicos de manutenção de campos'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Estudo revela que jogar com meião acima do joelho aumenta precisão de passes em 14%',
 'Um estudo publicado pelo Centro Nacional de Biomecânica Esportiva afirma que jogadores que utilizam meião acima da altura do joelho apresentam aumento de 14% na precisão de passes. Segundo os pesquisadores, a leve compressão na região melhora “o fluxo neuromuscular descendente” durante a execução do movimento.',
 false,
 '**FALSO.** A notícia usa linguagem técnica, mas apresenta conceitos inexistentes e conclusões sem base científica:

 **Onde estão os erros:**
 - “Fluxo neuromuscular descendente” não é um termo reconhecido pela biomecânica
 - A altura do meião não altera precisão de passes
 - O percentual apresentado é arbitrário
 - Estudo fictício com justificativa fisiológica incorreta

 **Por que isso é impossível:**
 - A compressão de vestimenta não afeta coordenação motora dessa forma
 - Termos falsamente técnicos são indicativo de pseudociência
 - Não há mecanismo fisiológico que produza o efeito descrito'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Federação aprova bola com pequenos propulsores para corrigir desvios de trajetória causados pelo vento',
 'A Federação Sul-Americana Alternativa anunciou o uso experimental de bolas equipadas com micropropulsores laterais que corrigem automaticamente desvios causados pelo vento. A tecnologia, chamada “AeroKick-L3”, seria acionada sempre que a velocidade do vento ultrapassasse 20 km/h.',
 false,
 '**FALSO.** O texto descreve um equipamento incompatível com qualquer regra do futebol:

 **Onde estão os erros:**
 - Bolas com propulsores violam totalmente as especificações da IFAB
 - A correção automática de trajetória não é permitida no esporte
 - A proposta altera o comportamento natural da bola
 - Tecnologia descrita não existe em nenhuma modalidade regulamentada

 **Por que isso é impossível:**
 - A bola deve ser passiva, sem mecanismos internos
 - Propulsores alterariam massa, aerodinâmica e imprevisibilidade do jogo
 - Nenhuma federação poderia homologar um equipamento assim'),

((SELECT id FROM categorias WHERE nome = 'Futebol'),
 'Nova linha de uniformes utiliza tecido que diminui resistência do ar ao redor do jogador em 28%',
 'Uma fabricante de material esportivo apresentou um tecido avançado que reduz em até 28% a resistência do ar ao redor do jogador durante corridas. O material, batizado de “AeroFlex Carbon”, utiliza pequenas partículas que criam um microcampo de baixa pressão ao redor do atleta.',
 false,
 '**FALSO.** A notícia tenta parecer tecnológica, mas descreve fenômenos fisicamente impossíveis:

 **Onde estão os erros:**
 - Tecidos não são capazes de criar “microcampos de baixa pressão”
 - A redução de 28% na resistência do ar é irreal para vestuário
 - Partículas em fibras não alteram aerodinâmica dessa forma
 - Terminologia pseudocientífica (“microcampo”) sem base física

 **Por que isso é impossível:**
 - Apenas formatos aerodinâmicos podem reduzir arrasto, não tecidos comuns
 - A física impede criação de zonas de baixa pressão estáveis ao redor do corpo
 - O efeito descrito violaria princípios básicos da dinâmica dos fluidos');

-- Categoria: Biologia
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (3, 'Biologia', 'Notícias sobre ciências biológicas, saúde e descobertas científicas', 4, 10, 'assets/images/conclusaobiologia.jpg', 'assets/images/capabiologia.png');

-- notícias da categoria Biologia
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Biologia'),
    'Tartarugas-verdes apresentam aumento de 18% na taxa de desova após recuperação de áreas de manguezal',
    'Pesquisadores do Instituto de Conservação Costeira registraram um aumento de 18% na desova de tartarugas-verdes em regiões onde houve recuperação de manguezais degradados. O estudo, conduzido entre 2019 e 2024, aponta que a melhoria da qualidade da água e a redução da erosão contribuíram diretamente para o crescimento das populações reprodutivas.',
    true,
    '**VERDADEIRO.** A notícia é coerente e biologicamente plausível:

    - Programas de restauração de manguezais realmente impactam espécies marinhas
    - Aumento percentual moderado (18%) condiz com dados reais de conservação
    - Intervalo de 5 anos é suficiente para observar efeitos ambientais
    - Instituições de conservação costumam publicar esse tipo de estudo'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Pesquisadores descobrem bactéria marinha capaz de converter microplástico em oxigênio puro',
'Cientistas da Universidade Oceanográfica de Santa Marta anunciaram a descoberta de uma bactéria encontrada em águas profundas capaz de metabolizar microplásticos e liberar oxigênio puro como subproduto. O estudo afirma que, em laboratório, a espécie reduziu até 40% de partículas plásticas em 72 horas e produziu oxigênio suficiente para manter pequenos peixes vivos.',
false,
'**FALSO.** A notícia contém elementos impossíveis do ponto de vista biológico:

**Erros identificáveis:**
- Nenhuma bactéria conhecida converte plástico diretamente em oxigênio
- Produção de oxigênio exige processos fotossintéticos, ausentes em bactérias marinhas de águas profundas
- Taxa de "40% de redução de plástico em 72h" é irrealistamente alta
- Laboratórios não usam “peixes vivos respirando oxigênio produzido” como métrica experimental

**Por que é impossível:**
- Microorganismos degradadores de plástico atuam em escala de meses ou anos, não dias
- Oxigênio não é subproduto plausível de degradação química de polímeros
- O mecanismo viola princípios básicos de metabolismo microbiano'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Cientistas identificam nova espécie de anfíbio fluorescente na Mata Atlântica',
'Uma equipe de pesquisadores da Universidade Federal do Sul revelou a descoberta de um pequeno anfíbio de 3 cm capaz de emitir fluorescência verde sob luz ultravioleta. A espécie foi encontrada em áreas remanescentes da Mata Atlântica e apresenta padrões únicos de pigmentação, possivelmente usados na comunicação entre indivíduos.',
true,
'**VERDADEIRO.** A descoberta é plausível:

- Novas espécies de anfíbios são descobertas anualmente no Brasil
- Fluorescência em anfíbios é fenômeno já registrado em alguns grupos
- Dimensão pequena (3 cm) é comum entre espécies endêmicas
- Explicação ecológica consistente com comportamento conhecido'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Estudo revela que células-tronco humanas podem sobreviver 28 dias fora de ambiente controlado',
'Um laboratório de biotecnologia anunciou resultados preliminares sugerindo que células-tronco humanas conseguem permanecer viáveis por 28 dias mesmo fora de incubadoras específicas, desde que mantidas em temperatura ambiente e em solução nutritiva simples.',
false,
'**FALSO.** A notícia viola princípios básicos de cultivo celular:

**Erros identificáveis:**
- Células-tronco exigem controle rigoroso de CO₂, umidade e temperatura
- Sobrevivência por “28 dias em temperatura ambiente” é biologicamente impossível
- Soluções nutritivas simples não mantêm células-tronco pluripotentes

**Por que é impossível:**
- Células mamíferas morrem em poucas horas sem condições adequadas
- Pesquisas reais jamais tratam manutenção celular com tanta simplicidade
- O experimento contradiz décadas de literatura sobre cultivo celular'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Nova análise genética mostra que polinizadores urbanos são mais diversos que polinizadores de áreas rurais',
'Relatório do Centro de Ecologia Aplicada revelou que, em áreas urbanas com alta diversidade de plantas ornamentais, a variedade genética de polinizadores como abelhas nativas é até 12% maior do que em regiões rurais dominadas por monoculturas agrícolas.',
true,
'**VERDADEIRO.** O resultado é plausível ecologicamente:

- Monoculturas realmente reduzem diversidade genética
- Ambientes urbanos variados podem sustentar mais espécies
- Percentual de 12% é moderado e realista
- Estudos semelhantes já observaram tendências parecidas'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Brasil autoriza uso de enzima sintética capaz de duplicar a velocidade do metabolismo humano por até 4 horas',
'A Agência Nacional de Biotecnologia aprovou uma enzima sintética que, segundo testes clínicos, é capaz de dobrar temporariamente a taxa metabólica humana por até 4 horas, permitindo maior gasto calórico e resistência física.',
false,
'**FALSO.** A notícia contém vários absurdos fisiológicos e regulatórios:

**Erros identificáveis:**
- “Agência Nacional de Biotecnologia” não existe
- Nenhuma enzima externa é capaz de dobrar metabolismo humano de forma segura
- Alterar metabolismo exige mudanças hormonais complexas, não uma enzima única

**Por que é impossível:**
- Elevação súbita da taxa metabólica levaria a hipertermia e colapso cardiovascular
- Estudos clínicos jamais aprovariam algo com riscos tão altos
- A premissa viola fundamentos da bioquímica humana'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Pesquisadores confirmam que fungo amazônico é capaz de decompor poliestireno sem gerar resíduos tóxicos',
'Cientistas do Laboratório de Micologia Tropical identificaram um fungo capaz de decompor poliestireno em compostos orgânicos simples, sem liberação de derivados tóxicos. A pesquisa abre caminho para novas soluções de biodegradação de plástico.',
true,
'**VERDADEIRO.** Totalmente plausível:

- Fungos degradadores de plástico já foram identificados no mundo
- Poliestireno pode ser biotransformado por determinados micro-organismos
- A descoberta se encaixa na linha de pesquisa ambiental atual'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Relatório aponta que árvores de eucalipto emitem feromônios capazes de reduzir a agressividade humana',
'Um estudo divulgado pelo Instituto Nacional Florestal afirma que eucaliptos liberam feromônios voláteis que, ao serem inalados, diminuem a agressividade humana em até 30%. Segundo o relatório, a plantação de eucalipto em áreas urbanas poderia melhorar comportamentos sociais.',
false,
'**FALSO.** A afirmação é pseudocientífica:

**Erros identificáveis:**
- Plantas não liberam “feromônios”, termo exclusivo de comunicação animal
- Não existe mecanismo biológico que induza redução comportamental humana via aroma vegetal
- “30% de redução na agressividade” é métrica impossível de confirmar

**Por que é impossível:**
- Pesquisas comportamentais não utilizam esse tipo de metodologia
- Eucalipto libera óleos aromáticos, não substâncias neuromoduladoras
- A conclusão ignora completamente neurobiologia humana'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Estudo identifica padrão de migração de borboletas Monarca influenciado por ruído urbano',
'Pesquisadores da Universidade de Ecologia Norte-Sul detectaram que borboletas Monarca alteram rotas de migração ao atravessar áreas urbanas com altos níveis de ruído, preferindo trajetos mais silenciosos. O estudo analisou dados de 12 anos e indicou correlação moderada entre poluição sonora e mudança de rota.',
true,
'**VERDADEIRO.** A notícia é coerente:

- Poluição sonora causa estresse em vários animais
- 12 anos é período adequado para observar padrões migratórios
- Alteração moderada de rota é plausível e documentada em outros insetos'),

((SELECT id FROM categorias WHERE nome = 'Biologia'),
'Geneticistas afirmam que humanos podem desenvolver olhos bioluminescentes até 2070 por mutação artificial controlada',
'Um grupo internacional de geneticistas publicou relatório sugerindo que, até 2070, humanos poderão ter olhos com bioluminescência natural graças à inserção controlada de genes derivados de águas-vivas. O estudo indica que a alteração seria segura e teria aplicação estética.',
false,
'**FALSO.** A notícia é cientificamente impossível:

**Erros identificáveis:**
- Olhos humanos não possuem estrutura capaz de emitir luz
- Inserção de genes de águas-vivas não gera bioluminescência funcional em tecidos complexos
- A promessa de “segurança e uso estético” é típica de ficção científica

**Por que é impossível:**
- Expressão de proteínas luminosas exige sistemas bioquímicos ausentes em humanos
- Modificar olhos para emitir luz exigiria reconstrução anatômica completa
- Nenhum estudo prospectivo sério faz esse tipo de previsão');

-- Categoria: Geografia
-- sistema: níveis de 0 a 4, com 4 peças (nível 0 = 0%, nível 1 = 25%, nível 2 = 50%, nível 3 = 75%, nível 4 = 100%)
-- nível 0 inicia sem peça desbloqueada.
INSERT INTO categorias (id, nome, descricao, total_niveis, pontos_para_proximo_nivel, caminho_imagem_completa, caminho_imagem_categoria)
VALUES (4, 'Geografia', 'Notícias sobre clima e meteorologia, biomas e relevo, dinâmica territorial e ambiental.', 4, 10, 'assets/images/conclusaogeografia.jpg', 'assets/images/capageografia.jpg');

-- notícias da categoria Geografia
INSERT INTO noticias (categoria_id, titulo, conteudo, eh_verdadeira, explicacao)
VALUES
    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'ONU aponta expansão de 6% nas áreas desérticas do Sahel na última década',
     'Relatório divulgado pela Organização das Nações Unidas indica que as áreas desérticas da região do Sahel cresceram aproximadamente 6% entre 2013 e 2023. O aumento é atribuído à combinação de uso intensivo do solo, variação climática e redução da cobertura vegetal.',
     true,
     '**VERDADEIRO.** O conteúdo é coerente e condiz com tendências reais:

- Avanço da desertificação no Sahel é fenômeno amplamente documentado
- Percentual moderado (6%) é plausível
- Causas citadas são consistentes (uso do solo + clima + vegetação)
- Relatórios da ONU frequentemente abordam a região'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Nova ilha vulcânica surge no Pacífico após erupção submarina de grande magnitude',
     'Pescadores da região de Tonga relataram a formação de uma nova ilha após uma erupção submarina ocorrida a 2,4 km da costa. Cientistas confirmaram que a estrutura possui cerca de 1,6 km² e deve permanecer estável pelos próximos meses.',
     true,
     '**VERDADEIRO.** Notícia plausível:

- Regiões como Tonga frequentemente registram formação de ilhas temporárias
- Dimensões modestas (1,6 km²) são realistas para ilhas recém-emergidas
- Erupções submarinas são comuns na área
- Permanência de "meses" é coerente com ilhas vulcânicas instáveis'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Estudo mostra que erosão costeira avança 1,4 metro por ano em média no litoral nordestino',
     'Pesquisadores do Instituto de Geodinâmica Costeira publicaram levantamento indicando que trechos do litoral nordestino vêm perdendo cerca de 1,4 metro de faixa de areia por ano devido ao aumento do nível do mar e interferências humanas, como construções irregulares.',
     true,
     '**VERDADEIRO.** A notícia é plausível e bem fundamentada:

- Erosão costeira no Nordeste é amplamente documentada
- Média anual de 1,4 metro é realista para trechos vulneráveis
- Combinação de causas climáticas e humanas faz sentido
- Institutos de pesquisa regionais costumam divulgar dados semelhantes'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Brasil e Uruguai assinam acordo que redefine em 32 km a fronteira entre os países na região do Chuí',
     'Os governos do Brasil e do Uruguai anunciaram um acordo que desloca a fronteira em 32 km na região do Chuí, transferindo áreas agrícolas para administração uruguaia. Segundo o comunicado, a mudança corrige “desalinhamentos históricos” dos mapas originais.',
     false,
     '**FALSO.** A notícia contém inconsistências territoriais e diplomáticas:

**Problemas identificados:**
- Fronteiras internacionais consolidadas não são modificadas em dezenas de quilômetros
- Não existe histórico de "desalinhamento de 32 km" entre Brasil e Uruguai
- Alterações fronteiriças exigem processos extremamente longos, não um simples acordo
- Transferência territorial envolveria plebiscitos, consultas e tratados complexos

**Por que é impossível:**
- O limite Brasil–Uruguai é definido por marcos físicos e rios, sem margem para grandes mudanças
- Qualquer alteração seria amplamente noticiada e envolveria organismos internacionais
- 32 km é escala absurda para correção cartográfica'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'População da região metropolitana de Goiânia cresce 11% em 5 anos, aponta IBGE',
     'Relatório preliminar do IBGE indica que a região metropolitana de Goiânia registrou crescimento populacional de 11% entre 2019 e 2024, impulsionado principalmente pela expansão do setor de serviços e pela migração interna.',
     true,
     '**VERDADEIRO.** Totalmente plausível:

- Crescimento urbano em regiões centrais do país é comum
- Percentual de 11% em 5 anos é moderado
- Migração interna é fator conhecido de expansão urbana
- IBGE publica relatórios periódicos compatíveis com esse conteúdo'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Novo estudo revela que a Caatinga pode se transformar completamente em cerrado até 2040',
     'Pesquisadores da Universidade Climática Interamericana afirmam que, até 2040, o bioma Caatinga deixará de existir e será totalmente substituído pelo cerrado devido a mudanças severas na temperatura e umidade.',
     false,
     '**FALSO.** A notícia apresenta previsões impossíveis e alarmistas:

**Erros identificáveis:**
- Transformação total de um bioma em 16 anos é inviável ecologicamente
- Temperatura e umidade não mudam biomas inteiros em tão pouco tempo
- A Caatinga possui espécies extremamente resistentes, não desaparece abruptamente

**Por que é impossível:**
- Transições de biomas ocorrem em séculos, não décadas
- Não existem dados científicos que suportem essa previsão extrema
- Instituições sérias não fazem afirmações absolutas sobre extinção total de biomas'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Satélite brasileiro registra aumento de 4% na superfície de água doce temporária no Pantanal',
     'Dados do satélite CBERS-6 revelaram que áreas alagadas temporárias do Pantanal cresceram 4% em comparação com a média dos últimos dez anos, devido ao aumento das chuvas sazonais.',
     true,
     '**VERDADEIRO.** A notícia é coerente:

- O Pantanal apresenta grande variação anual de áreas alagadas
- Percentual de 4% é realista e moderado
- Sistemas CBERS monitoram biomas brasileiros
- Chuvas sazonais são fator central no ciclo hidrológico local'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Pesquisadores encontram evidências de que o Rio Amazonas mudou completamente de direção entre 1970 e 1980',
     'Relatório de geógrafos afirma que o Rio Amazonas teria invertido totalmente seu fluxo entre as décadas de 1970 e 1980 devido a movimentos tectônicos não detectados na época.',
     false,
     '**FALSO.** A notícia contém erros geológicos graves:

**Erros identificáveis:**
- Um rio do porte do Amazonas não muda de direção em 10 anos
- Não houve atividade tectônica significativa na região na época
- Mudança de fluxo exigiria elevação continental imensa

**Por que é impossível:**
- Uma inversão desse tipo deixaria registros geológicos massivos
- O fluxo do Amazonas é estável há milhões de anos
- Movimentos tectônicos no Brasil são mínimos e incapazes de tal fenômeno'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Mapa atualizado mostra que São Paulo ultrapassou 22,9 milhões de habitantes na região metropolitana',
     'O novo mapa populacional divulgado pela Secretaria de Planejamento indica que a região metropolitana de São Paulo atingiu 22,9 milhões de habitantes em 2024, impulsionada principalmente pela expansão de municípios do entorno.',
     true,
     '**VERDADEIRO.** Notícia consistente:

- Números populacionais estão próximos de estimativas reais
- Crescimento se concentra em municípios periféricos, como ocorre na prática
- Secretarias estaduais frequentemente divulgam mapas atualizados'),

    ((SELECT id FROM categorias WHERE nome = 'Geografia'),
     'Cientistas afirmam que Groenlândia pode se dividir em duas ilhas até 2032 devido ao derretimento acelerado',
     'Um estudo divulgado pela Federação Glaciológica Global afirma que o derretimento do gelo da Groenlândia irá separar o território em duas ilhas distintas até 2032, criando um canal de 70 km de largura.',
     false,
     '**FALSO.** A previsão é impossível no horizonte apresentado:

**Erros identificáveis:**
- A massa de gelo da Groenlândia não derrete rápido o suficiente para expor canais desse tamanho em menos de 10 anos
- "Canal de 70 km" é geologicamente absurdo no período indicado
- Estudos reais nunca fazem previsões tão específicas e drásticas

**Por que é impossível:**
- Mudanças desse porte exigem séculos, não anos
- O núcleo rochoso da Groenlândia é contínuo; não se divide tão facilmente
- Seria necessário derretimento em escala cataclísmica, sem base científica');

INSERT INTO conquistas (nome, descricao, icone, caminho_imagem_completa, criterio, tipo, valor_requerido)
VALUES (
    'Primeiros Passos',
    'Acumulou 40 pontos respondendo questões corretamente!',
    '🏆',
    NULL,
    'Acumular pontos totais',
    'PONTOS_TOTAIS',
    40
);


