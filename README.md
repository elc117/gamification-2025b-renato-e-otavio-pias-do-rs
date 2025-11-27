## Identificação

- **Nomes:** Otávio Krügel Zimmer e Renato Marquioro da Silva
- **Curso:** Sistemas de Informação

# Fact or Fake

## Descrição do trabalho
Projeto educativo de gamificação para combate à desinformação, onde os usuários identificam se notícias, manchetes e citações são falsas ou verdadeiras. A ideia do projeto é ir além do entretenimento puro, desenvolvendo pensamento crítico e habilidades de checagem de fatos essenciais para a cidadania digital. O sistema funcionará com a exibição de notícias, onde o usuário decide se cada conteúdo é FATO ou FAKE, recebendo feedback educativo com explicações após cada resposta. O usuário ganhará pontos por acerto. Após certa quantidade de pontos, desbloqueia uma peça para formar uma imagem que representa a categoria de notícias que está respondendo. Com todas as peças, a imagem é completa.

## Organização do projeto
O projeto foi implementado em Javalin, com um frontend em HTML, CSS e JavaScript.

Abaixo, a arquitetura do projeto, separada por camadas, com separação de responsabilidades:

| Camada | Responsabilidade |
|--------|------------------|
| **Model (Entidades)** | Representar entidades do domínio com regras de negócio (Rich Domain Model) |
| **Repository (Acesso a Dados)** | Realizar operações CRUD no banco de dados (abstração de persistência) |
| **Service (Lógica de Negócio)** | Orquestrar operações complexas, implementar regras de negócio |
| **Controller (API REST)** | Receber requisições HTTP, validar entrada, retornar respostas JSON |
| **DTO (Transfer Objects)** | Transferir dados entre camadas sem expor entidades (desacoplamento) |
| **Config (Configuração)** | Gerenciar configurações de infraestrutura (banco de dados, ORM) |
| **Resources** | Arquivos de configuração e scripts SQL |
| **Frontend** | Interface do usuário (HTML, CSS, JavaScript) |

## Processo de desenvolvimento
### Início com estrutura básica
Primeiramente, foi necessário entender como funciona o framework Javalin. Já sabíamos que era um framework web, mas não sabíamos ao certo como implementar ele. Depois de estudar sobre ele, inicialmente criamos as classes de modelo principais. Depois, fizemos uma implementação mínima do projeto em arrays, onde testamos as rotas e requisições básicas que nós criamos. Depois, adicionamos mais rotas para compreender as categorias, progressos e conquistas. A principal dificuldade nessa parte foi entender o modelo de programação do Javalin (com as requisições/respostas HTTP) aplicando isso com a Programação Orientada a Objetos.

### Banco de dados
Após implementar inicialmente em arrays, levamos o projeto para o banco de dados. Nossa ideia inicial era trabalhar com o SQLite, até por já termos tido um pouco de contato com ele. Porém, vimos que não seria o ideal para lidar com várias requisições vindas de diversas fontes, então optamos por utilizar o PostgreSQL. Foi um pouco difícil a implementação inicial, pois tudo era meio "novo" pra nós. Tivemos que usar algumas anotações específicas nos modelos pra se adequar ao banco de dados, parte em que tivemos que pesquisar bastante para entender. Contamos com ajuda de IA para compreender o funcionamento e para a aplicação em exemplo mínimo, para depois, finalmente, implementarmos o banco de dados. Utilizamos arquivo Hibernate (ORM) para mapear objetos Java - tabelas SQL e tivemos que configurar anotações JPA (``@Entity``, ``@Table``, ``@Column``, ``@Id``, etc.). Também tivemos que criar um repositorie genérico e repositories para cada uma das classes de modelo, para lidar com o CRUD no banco de dados. As principais dificuldades dessa etapa foram entender anotações do Hibernate (pois nunca tínhamos usado ORM) e configurar ele corretamente pro projeto. Para entender isso, usamos o seguinte prompt:

Como configurar Hibernate com PostgreSQL em Java? Preciso de um exemplo de Hibernate e de uma classe @Entity com o seu relacionamento. Como eu poderia aplicar em meu projeto atual? Me dê exemplos e mostre o que devo alterar.

### Services
Pesquisamos como são arquitetados projetos em Java e vimos que utiliza-se services para lidar com a lógica de negócio (padrão Service Layer). Então fizemos uma refatoração e separamos as definições da lógica, com a criação destes services para fazer as operações básicas, deixando o código mais testável e organizado (com a separação de responsabilidades).

### Criação de conta, login e logout
Implementamos também um sistema de contas dos usuários, sendo necessário, por ora, informar apenas o nome e o email para criar uma conta. Depois disso, pode-se fazer o login na conta e o logout.

### Figura (imagens)
Para a parte da figura da categoria (que é parte do processo de gamificação do projeto), implementamos uma lógica de divisão em 4 partes: 25%, 50%, 75% e 100%. Fizemos isso de forma dinâmica, ou seja, é identificada a quantidade de notícias de uma determinada categoria e é feita uma divisão destas notícias. A cada 25% de acertos que o usuário consegue sobre o total de notícias da categoria, desbloqueia-se uma peça para completar a figura. A implementação visual disto foi feita posteriormente no frontend, como explicado a seguir.

### Frontend
Para o frontend, criamos inicialmente um html bem básico, somente para testar as requisições com cliques. Depois, contamos com a ajuda de IA para "embelezar" as telas, com a adição do JS e do CSS. No frontend é implementado um menu, contendo a parte para "jogar", o perfil do usuário e as conquistas do usuário. Na parte de "Jogar", pode-se escolher uma categoria de notícias e ir respondendo as notícias que aparecem, completando as figuras e desbloqueando conquistas. No "Meu Perfil", o usuário pode ver o seu nível de conta, quantas notícias já respondeu, quantas acertou, a sua taxa de acertos e o progresso em cada categoria. Aí que entra a implementação da figura que representa a categoria: o backend envia a quantidade de peças desbloqueadas pelo usuário (que são obtidas conforme a porcentagem de conclusão da categoria) para o frontend, e este monta a figura com um sistema semelhante a "quadrantes". 25% (1 peça) desbloqueia o canto superior esquerdo, 50% (2 peças) o canto superior direito, 75% (3 peças) o canto inferior esquerdo e 100% (4 peças) o canto inferior direito, completando a imagem. Além disso, também pode-se consultar as conquistas do usuário na opção "Conquistas". Prompt usado:

Crie um JS e um CSS básico para testarmos o projeto, de modo que ele reconheça a quantidade de peças desbloqueadas pelo usuário (já implementado pelo backend) e monte a imagem que representa a categoria, com uma espécie de "quadrantes": 25% (1 peça) desbloqueia o canto superior esquerdo, 50% (2 peças) o canto superior direito, 75% (3 peças) o canto inferior esquerdo e 100% (4 peças) o canto inferior direito.

A IA montou uma estrutura básica que permitiu nós testarmos e verificarmos que a lógica do backend estava certa, e foi possível implementar visualmente a imagem.

### Refatoração
Chegamos à conclusão que, em determinado momento, poderíamos fazer uma refatoração no projeto, pois alguns services estavam muito grandes, os repositories talvez não estivessem na forma ideal e o Main estava com muitas responsabilidades. Para isso, então, criamos novos services, arrumamos os repositories, criamos algumas DTOs, criamos controllers para lidar com as rotas e um ``App.java``, responsável por configurar e inicializar a aplicação Javalin. Dessa forma, tudo ficou mais organizado e o Main com bem poucas atribuições.

### render.com
Adicionamos o projeto para o render.com, sendo necessário, para isso, mudar algumas coisas, como mudar o arquivo hibernate para permitir a execução no render.com, com variáveis de ambiente (como ``DATABASE_URL``, ``DB_USER``, ``DB_PASSWORD``), em vez de salvar as variáveis no banco de dados (além de mudar o formato de algumas variáveis), adicionamos um dockerfile (necessário para rodar o web service), ajustamos os fetchs no frontend para permitir o acesso do render.com e não apenas o localhost (como vínhamos fazendo) e ajustamos o JDBC URL para se adaptar ao jeito que o render.com espera. Também contamos com ajuda de IA nesta parte, pois não entendíamos muito bem o que teríamos que alterar no projeto para sair do localhost e permitir a execução no render.com, com a IA nos guiando bastante nesse processo.

A principal dificuldade foi o fato de que o formato de ``DATABASE_URL`` do Render é incompatível com o jeito que estávamos fazendo no Hibernate, então fizemos o seguinte prompt:

Render.com fornece DATABASE_URL no formato postgresql://user:pass@host:port/db mas Hibernate espera jdbc:postgresql://host:port/db. Como fazemos para converter?

### itch.io
Para fazer o upload do projeto no itch.io, também tivemos que mudar algumas coisas, como corrigir o CORS para aceitar as requisições de diferentes domínios e não só o do render.com (esta parte foi bastante difícil, sendo necessário pesquisar como funciona o CORS e pedindo ajuda para a IA conforme o que iríamos implementar). Também implementamos um sistema de tokens para manter a sessão de um usuário, pois percebemos que depois de fazer o upload do projeto para o itch.io, no frontend, quando o usuário mudava de tela, "perdia-se" a sessão dele, de modo que não era possível fazer as requisições direito. Então vimos que os projetos assim lidam com os tokens, que foram implementados para permitir isso, tendo sido necessário mudar o controller de autenticação e o frontend, salvando o token no ``localStorage`` do navegador. 
As principais dificuldades foram o CORS bloqueando requisições entre domínios diferentes e o problema dos tokens.

Prompts usados:
Como implementar autenticação com tokens JWT usando o Javalin? Como o frontend deve enviar o token em cada requisição?

Como lidar com problema do CORS? Estamos tentando fazer requisições do itch.io para um servidor hospedado no render.com. Porém, o CORS está bloqueando as requisições. O que deve ser feito para solucionar esse problema?

### Conclusão versão inicial
Com isso, foi possível entregar uma boa versão inicial de nosso projeto, já com bastante funcionalidades para serem utilizadas.

## Versão final
Para a versão final, fomos refinando o projeto para deixá-lo cada vez mais completo e orientado a objetos.

### Ajustes frontend
Após a primeira entrega, primeiramente atualizamos aspectos do frontend do projeto, para deixá-lo mais temático e visualmente atrativo, simulando um jogo de "detetive". Isso também fez com que tivéssemos que alterar como as requisições eram processadas, para se adequar às novas telas e garantir o correto funcionamento do projeto. Isto foi realizado até o final do projeto, sempre com novos commits. A IA foi fundamental para aplicar nossas ideias, ajudando a montar uma parte bem grande do visual final de nosso projeto.

### Senha
Após isso, adicionamos um sistema de senha para o usuário, tendo que lidar com essa adição tanto no banco de dados quanto no backend e no frontend.

### Async Operation Feedback
Notamos um problema recorrente durante os diversos testes que realizamos no projeto: a demora quando o usuário realizava a sua primeira criação de conta ou o seu primeiro login, pois isso dependia do banco de dados, o qual na primeira ativação ainda estava "dormindo". Comentamos isso com nossos colegas, e o colega Michel Altmann nos recomendou a utilização do ``Async Operation Feedback``. Pesquisamos como isso funciona e resolvemos implementar em nosso projeto. Basicamente, consiste em "parar" a execução do programa (para o usuário final é mostrada uma tela de "Carregando") enquanto a requisição não é concluída. Com isso, é mostrado esse feedback visual na tela, para não parecer que o projeto "travou" quando realizadas essas primeiras requisições.

### Adição de novas notícias, categorias e conquistas
Para a versão final, também adicionamos novas notícias, categorias e conquistas, para diversificar ainda mais o conteúdo do jogo.

### Refatoração
Notamos que o projeto estava funcionando tudo certo, sem problema algum. Porém, percebemos que, apesar disso, poderia ser utilizada uma melhor orientação a objetos em relação ao que vínhamos fazendo. Chegamos à conclusão de que o projeto poderia estar tendendo mais à programação orientada a dados (resultado das diversas operações realizadas com o banco de dados) do que propriamente à programação orientada a objetos. Tínhamos, sim, bastante aspectos da orientação a objetos, mas sentimos que poderíamos aprimorar ainda mais. Para entender tudo o que poderia ser alterado para deixar o projeto mais POO, contamos com a ajuda de IA para visualizarmos como um todo o escopo e compreender mudanças que seriam interessantes/produtivas. A IA nos auxilou nas transformações que seriam necessárias e no que deveria ser adicionado/excluído. Com isso, conseguimos compreender como deveria ser a implementação, bem orientada a objetos. Então o que alteramos foi o seguinte:
- criamos DTOs, eliminando ``Map<String,objects>``;
- aproveitamos melhor herança, composição e encapsulamento no projeto;
- ajustamos os métodos de comportamentos para as entidades;
- implementamos injeção de dependências;
- removemos algumas tabelas e classes que estavam sendo "desnecessárias", duplicando código;
- substituímos a maior parte de services procedurais (bastante casos de void, o que acabava descaracterizando a POO) por comportamentos nas entidades. Prompt usado:

Analise este projeto Java e identifique pontos onde poderia deixar a programação mais orientada a objetos. Sugira o que pode ser feito de refatoração para aplicar conceitos de orientação a objetos em lugares em que isso não esteja tão presente.

Com isso, conseguimos realizar mudanças no nosso projeto.
Exemplo de mudanças:
#### ANTES
<pre>
// Lógica dispersa no Service
public Map<String, Object> processar() {
    Map<String, Object> resultado = new HashMap<>();
    usuario.setTotalTentativas(usuario.getTotalTentativas() + 1);
    usuario.setAcertosTotais(usuario.getAcertosTotais() + 1);
    double taxa = (usuario.getAcertosTotais() * 100.0) / usuario.getTotalTentativas();
    usuario.setTaxaAcerto(taxa);
    resultado.put("acertou", true);
    resultado.put("taxa", taxa);
    return resultado;
}
</pre>

#### DEPOIS
<pre>
// Comportamento na Entidade
public ResultadoRespostaDTO processar() {
    usuario.registrarTentativa(true); // encapsulado!
    boolean subiu = usuario.adicionarPontos(10); // retorna info útil
    return new ResultadoRespostaDTO(true, 10, usuario, subiu);
}
</pre>

### Criação de notícias
Tivemos ajuda da IA para a criação de notícias. Avaliamos os resultados e fizemos algumas modificações/adições manuais. Um dos prompts utilizados:
Crie 10 notícias sobre política, sendo 5 verdadeiras e 5 falsas, com explicações educativas do porquê ser verdadeira ou falsa.

## Recursos de Orientação a Objetos utilizados

#### **1. Encapsulamento**
- **Entidades:** Campos privados com getters/setters
- **Métodos privados:** Lógica interna protegida

Alguns exemplos:
  ```java
  private void recalcularTaxaAcerto() { ... }
  private int calcularNivel(int pontuacao) { ... }
  ```

#### **2. Herança**
Exemplo:
- **``ConquistaComStatus extends Conquista``**
  - Reutiliza campos/métodos da superclasse
  - Adiciona campos específicos (dataDesbloqueio, desbloqueada)
  - Frontend acessa ``conquista.nome`` (herdado) sem aninhamento

#### **3. Composição (Composition)**
Exemplo:
- **``RespostaCategoriaDTO``** contém 3 objetos completos:
  ```java
  private Categoria categoria;
  private ProgressoCategoria progresso;
  private EstatisticasCategoriaDTO estatisticas;
  ```
Dessa forma, permite acesso organizado, como: ``response.categoria.nome``, ``response.progresso.nivel``

#### **4. Polimorfismo**
- **GenericRepository<T>:** métodos genéricos reutilizáveis deste repository.
Exemplo: 
  ```java
  public T findById(Long id) { ... }
  public List<T> findAll() { ... }
  ```
- Permite que, por exemplo, ``CategoriaRepository`` e ``UsuarioRepository`` compartilhem lógica.

#### **5. Abstração**
- **DTOs:** abstraem comunicação entre camadas
- **Services:** abstraem lógica de negócio
- **Repositories:** abstraem acesso a dados

#### **6. Rich Domain Model**
- **Comportamentos nas entidades** (não apenas dados):
Exemplos:
  - ``Usuario.registrarTentativa(boolean)``
  - ``Usuario.adicionarPontos(int) - boolean``
  - ``ProgressoCategoria.atualizarProgresso(double) - boolean``

#### **7. Injeção de Dependências**
Exemplo:
- **Dual constructor pattern:**
  ```java
  // Construtor padrão
  public JogoOrquestradorService() {
      this(new NoticiaSelecaoService(), ...);
  }
  
  // Construtor com DI (para testes)
  public JogoOrquestradorService(NoticiaSelecaoService service, ...) {
      this.noticiaSelecaoService = service;
  }
  ```
- Isso facilita testes unitários

### Contribuições dos integrantes

#### **Otávio Krügel Zimmer:**
- **Backend:**
  - Implementação do Hibernate e repositórios
  - Refatoração OO (DTOs, Rich Domain Model)
  - Autenticação JWT e gerenciamento de sessão
  - Configuração de deploy no Render.com
  - Lógica geral nas entidades
- **Frontend:**
  - Sistema de peças progressivas (grid 2x2)
  - Integração com API
  - Deploy no itch.io

#### **Renato Marquioro da Silva:**
- **Backend:**
  - Services de lógica de negócio
  - Sistema de pontuação e níveis
  - Sistema de conquistas automáticas
  - Lógica com banco de dados
- **Frontend:**
  - Design temático (CSS noir/detetive)
  - Telas de login/cadastro
  - Feedback visual (loading states)
  - Implementação de músicas

**Trabalho em dupla:**
- Decisões arquiteturais (padrões OO, estrutura de pastas)
- Debug de problemas complexos (CORS, sessões, banco)
- Refatoração final para OO puro (fizemos uma espécie de "pair programming" virtual)
- README.md
- Criação de diagramas UML

## Projeto em si
### Modelo de Domínio
- **Usuario.java** — representa um usuário do sistema.
- **Categoria.java** — define categorias de notícis.
- **Noticia.java** — contém informações de uma notícia.
- **Resposta.java** — registra a resposta de um usuário para uma notícia, incluindo se está correta e pontos ganhos.
- **Conquista.java** — define conquistas desbloqueáveis.
- **ConquistaUsuario.java** — relaciona conquistas desbloqueadas por usuários com data de desbloqueio.
- **ProgressoCategoria.java** — acompanha o progresso do usuário em cada categoria.

### Configuração
- **HibernateConfig.java** — Classe de configuração do Hibernate que gerencia a SessionFactory, responsável por inicializar a conexão com o banco de dados PostgreSQL e fornecer sessões para operações de persistência.

## Banco de Dados
O projeto utiliza **PostgreSQL** como sistema de gerenciamento de banco de dados. A estrutura inclui:

**Tabelas principais:**
- `usuarios` — armazena dados dos usuários
- `categorias` — categorias de notícias
- `noticias` — conteúdo de notícias verificadas
- `respostas` — respostas dos usuários às notícias
- `conquistas` — conquistas disponíveis no sistema
- `conquistas_usuario` — conquistas desbloqueadas por usuários
- `progresso_categoria` — progresso dos usuários em cada categoria

Na primeira vez que incicializamos o projeto, nós rodamos duas queries, com os arquivos ``criar_tabelas_iniciais.sql`` e ``popular_tabelas.sql``, para inicializar o banco de dados e permitir que possam ser realizadas as requisições.

## Sistema de gamificação do projeto

#### Pontuação
- **Acerto:** um acerto dá 10 pontos.
- **Erro:** 0 pontos (pode tentar novamente)
- Apenas respostas corretas são salvas no banco
- As notícias erradas podem ser respondidas novamente

#### Conquistas
O sistema de conquistas é desbloqueado automaticamente quando o usuário atinge determinados marcos:

Exemplo:
**Conquista - "Primeiros Passos":**
- **Critério:** acumular 40 pontos totais
- **Tipo:** PONTOS_TOTAIS

O sistema foi feito de forma extensível para futuros tipos de conquistas também.

#### Progressão por Categoria
Cada categoria possui sistema independente de níveis:
- 5 níveis de progresso (0 a 4)
- Desbloqueio de peças do quebra-cabeça por nível
- Cada nível desbloqueia 25% da imagem completa
- Imagem final revelada ao atingir nível 4

#### Níveis da Conta
A progressão é **global** (soma de todas as categorias):

```
XP necessário = 100 × nível atual
```

| Nível | XP Necessário | XP Total Acumulado | Título |
|-------|---------------|-------------------|---------|
| 1 | 0 | 0 | Reporter |
| 2-4 | 100-400 | 100-1000 | Reporter |
| 5-9 | 500-900 | 1500-4500 | Analista |
| 10-14 | 1000-1400 | 5500-10500 | Investigador |
| 15-19 | 1500-1900 | 12000-19000 | Investigador Sênior |
| 20-24 | 2000-2400 | 21000-30000 | Detetive |
| 25-29 | 2500-2900 | 32500-43500 | Detetive Master |
| 30+ | 3000+ | 46500+ | **Caçador Supremo** |

#### Taxa de Acerto
Métrica de desempenho geral:

```
Taxa de Acerto = (total_acertos / total_tentativas) × 100
```

Não afeta pontos ou nível, apenas estatística.

#### 🎮 Frontend

Interface web desenvolvida com HTML, CSS e JavaScript.

**Funcionalidades:**
- Página inicial
- Jogo (responder notícias)
- Perfil do usuário
  - Barra de progresso de nível
  - Progresso por categoria
  - Conquistas desbloqueadas
  - Recompensas visuais (peças)
- Estatísticas
- Sistema de sessão (login)

---

## Diagramas

### Diagrama de Controladores
![Diagrama de Controladores](DiagramaPng/Diagrama_de_Controladores.png)

### Diagrama de Entidades
![Diagrama de Entidades](DiagramaPng/Diagrama_de_Entidades.png)

### Diagrama de Repositórios
![Diagrama de Repositórios](DiagramaPng/Diagrama_de_Repositórios.png)

### Diagrama de Serviços
![Diagrama de Serviços](DiagramaPng/Diagrama_de_Serviços.png)

### Diagrama Geral
![Diagrama Geral](DiagramaPng/Visão_Geral.png)

## Orientações para execução
O projeto está configurado com um **DevContainer** para facilitar o desenvolvimento e avaliação sem necessidade de instalar dependências localmente.

### O que está incluído?
- Java 17 (OpenJDK)
- Maven
- PostgreSQL
- Ferramentas de desenvolvimento

### Como usar?

#### Opção 1: GitHub Codespaces (Recomendado)
1. Acesse o repositório no GitHub
2. Clique em **Code** > **Codespaces** > **Create codespace on main**
3. Aguarde o ambiente inicializar
4. Execute: `mvn exec:java`
5. Acesse: `http://localhost:3000`

#### Opção 2: VS Code Local
1. Instale a extensão **Dev Containers**
2. Abra o projeto
3. Pressione `F1` > **Dev Containers: Reopen in Container**
4. Execute: `mvn exec:java`

## Como executar localmente (sem DevContainer)

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL

### Configuração do Banco de Dados
1. Crie o banco: `CREATE DATABASE banco_local;`
2. Configure as credenciais em `hibernate.cfg.xml`
3. Insira as tabelas no banco de dados
4. Popule as tabelas no banco de dados

### Executar
```bash
mvn clean package
mvn exec:java
```

A API estará disponível para teste em: `http://localhost:3000`

### itch.io
Acesse este link: https://otaviozimmer.itch.io/fact-or-fake

---

## Resultado final


https://github.com/user-attachments/assets/d0554158-9eb3-4832-9241-180da8339c86



## Referências e créditos
- Javalin: https://javalin.io/documentation#getting-started

- POM: https://maven.apache.org/pom.html

- Maven: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html 

- PostgreSQL: https://jdbc.postgresql.org/documentation/setup/

- Anotações: https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html;
https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html;
https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html;
https://docs.hibernate.org/orm/6.4/javadocs/org/hibernate/annotations/Type.html

- Hibernate: https://hibernate.org/orm/documentation/7.1/;
https://docs.hibernate.org/orm/6.4/userguide/html_single/#annotations

- Repositories: https://www.dio.me/articles/o-que-e-o-repository-866d13a8e1e5

- Services: https://pt.stackoverflow.com/questions/381324/o-que-%C3%A9-um-service-e-qual-sua-diferen%C3%A7a-para-um-controller

- Controllers: https://pt.stackoverflow.com/questions/329167/como-utilizar-o-controller-em-uma-aplica%C3%A7%C3%A3o-java

- DTOs: https://medium.com/@jigorsilva/entendendo-dtos-em-java-um-guia-completo-para-iniciantes-55e82264918f;
https://docs.oracle.com/en/java/javase/17/language/records.html;
https://docs.oracle.com/en/java/javase/17/language/records.html

- Docker: https://javalin.io/tutorials/docker

- CORS: https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS

- Tokens: https://jwt.io/introduction

- Colega Michel Altmann: Async Operation Feedback

- Async Operation Feedback: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function

- ChatGPT, Copilot e Claude AI: **importante:** sempre validamos, testamos e ajustamos o código sugerido pela IA. Ela foi usada por nós como consultor técnico, não como substituto de aprendizado (prompts foram citados na seção de processo de desenvolvimento)

- Padrões de projeto em Java: https://www.devmedia.com.br/guia/padroes-de-projeto-em-java/34456

- Service Layer: https://martinfowler.com/eaaCatalog/serviceLayer.html

- Dependency Injection: https://martinfowler.com/articles/injection.html

- Música geral: gerada por Suno.ia - https://suno.com

- Música de desbloqueio de conquista: AURA, feita por: Ogryzek - https://open.spotify.com/intl-pt/track/6DvGOGRRjhURCE7weXWV3x?si=685ad3d567f449b4
