# Fact or Fake

## Classes Implementadas

### Modelo de Domínio

- **Usuario.java** — Representa um usuário do sistema com id, nome, email e nível.

- **Categoria.java** — Define categorias de notícias com nome, descrição, níveis totais, pontos para próximo nível e caminho da imagem completa.

- **Noticia.java** — Contém informações de uma notícia: título, conteúdo, se é verdadeira ou falsa, explicação e categoria associada.

- **Resposta.java** — Registra a resposta de um usuário para uma notícia, incluindo se está correta e pontos ganhos.

- **Conquista.java** — Define conquistas desbloqueáveis com nome, descrição, ícone, critério, tipo e valor requerido.

- **ConquistaUsuario.java** — Relaciona conquistas desbloqueadas por usuários com data de desbloqueio.

- **ProgressoCategoria.java** — Acompanha o progresso do usuário em cada categoria: nível atual, pontos de maestria e peças desbloqueadas.

- **PecaRecompensa.java** — Define as peças do quebra-cabeça de recompensa para cada categoria, incluindo categoria associada, nível requerido para desbloqueio, caminho da imagem e posição no quebra-cabeça (x, y).

- **PecaUsuario.java** — Relaciona as peças de recompensa desbloqueadas por cada usuário, registrando qual peça foi desbloqueada e a data de desbloqueio.

### Camada de Serviço

- **DadosService.java** — Camada de serviço que contém a lógica de negócio da aplicação. Gerencia operações CRUD e regras de negócio utilizando os repositórios para acessar o banco de dados. Inclui métodos para usuários, categorias, notícias, respostas, conquistas e progressos.

- **JogoService.java** — Serviço responsável pela lógica principal do jogo:
  - Processa respostas dos usuários
  - Calcula pontuação
  - Atualiza progresso em categorias
  - Verifica e desbloqueia conquistas automaticamente
  - Gerencia desbloqueio de peças do quebra-cabeça

- **PontuacaoService.java** — Serviço de cálculo de pontuação e níveis:
  - Calcula pontos por resposta (5 pontos por acerto, 0 por erro)
  - Determina nível baseado em pontos acumulados
  - Sistema de 5 níveis (0 a 4):
    - Nível 0: 0 pontos (Iniciante - sem peças)
    - Nível 1: 10 pontos (Aprendiz - 25% - 2 acertos)
    - Nível 2: 20 pontos (Conhecedor - 50% - 4 acertos)
    - Nível 3: 30 pontos (Especialista - 75% - 6 acertos)
    - Nível 4: 40 pontos (Mestre - 100% - 8 acertos)
  - Calcula porcentagem de progresso e pontos para próximo nível

### Sistema de Gamificação

#### Pontuação
- **Acerto:** +5 pontos
- **Erro:** 0 pontos (pode tentar novamente)
- Apenas respostas corretas são salvas no banco
- Notícias erradas podem ser respondidas novamente

#### Conquistas
O sistema de conquistas é desbloqueado automaticamente quando o usuário atinge determinados marcos:

**Conquista Alfa - "Primeiros Passos":**
- **Critério:** Acumular 20 pontos totais
- **Equivalente:** 4 acertos (4 × 5 pontos)
- **Tipo:** PONTOS_TOTAIS

O sistema é extensível para futuros tipos de conquistas

#### Progressão por Categoria
Cada categoria possui sistema independente de maestria:
- 5 níveis de progresso (0 a 4)
- Desbloqueio de peças do quebra-cabeça por nível
- Cada nível desbloqueia 25% da imagem completa
- Imagem final revelada ao atingir nível 4 (Mestre)

### Camada de Persistência (Repositórios)

- **GenericRepository.java** — Repositório genérico base que implementa operações CRUD comuns (save, update, delete, findById, findAll) usando Hibernate/JPA. Outros repositórios herdam desta classe.

- **UsuarioRepository.java** — Repositório específico para usuários, estende GenericRepository e adiciona método `findByEmail()` para buscar usuário por email.

- **CategoriaRepository.java** — Repositório para categorias, adiciona método `findByNome()` para buscar categoria por nome.

- **NoticiaRepository.java** — Repositório para notícias, inclui métodos especializados:
  - `findByCategoria()` - busca notícias de uma categoria específica
  - `findNaoRespondidasPorUsuario()` - retorna notícias não respondidas por um usuário

- **RespostaRepository.java** — Repositório para respostas, com métodos:
  - `findByUsuario()` - busca todas as respostas de um usuário
  - `existsByUsuarioAndNoticia()` - verifica se usuário já respondeu uma notícia
  - `countByUsuario()` - conta total de respostas do usuário
  - `countAcertosByUsuario()` - conta acertos do usuário

- **ConquistaRepository.java** — Repositório para conquistas, gerencia as conquistas disponíveis no sistema.

- **ConquistaUsuarioRepository.java** — Repositório para relacionamento de conquistas desbloqueadas por usuários, inclui `findByUsuario()` para listar conquistas de um usuário.

- **ProgressoCategoriaRepository.java** — Repositório para progresso por categoria, com métodos:
  - `findByUsuario()` - busca todos os progressos de um usuário
  - `findByUsuarioAndCategoria()` - busca progresso específico de usuário em uma categoria

- **PecaRecompensaRepository.java** — Repositório para peças de recompensa, adiciona `findByCategoria()` para buscar peças de uma categoria específica.

- **PecaUsuarioRepository.java** — Repositório para peças desbloqueadas por usuários, inclui `findByUsuario()` para listar peças de um usuário.

### Configuração

- **HibernateConfig.java** — Classe de configuração do Hibernate que gerencia a SessionFactory, responsável por inicializar a conexão com o banco de dados PostgreSQL e fornecer sessões para operações de persistência.

### Banco de Dados

O projeto utiliza **PostgreSQL** como sistema de gerenciamento de banco de dados. A estrutura inclui:

**Tabelas principais:**
- `usuarios` — armazena dados dos usuários
- `categorias` — categorias de notícias
- `noticias` — conteúdo de notícias verificadas
- `respostas` — respostas dos usuários às notícias
- `conquistas` — conquistas disponíveis no sistema
- `conquistas_usuario` — conquistas desbloqueadas por usuários
- `progresso_categoria` — progresso dos usuários em cada categoria
- `pecas_recompensa` — peças do quebra-cabeça de cada categoria
- `pecas_usuario` — peças desbloqueadas pelos usuários

### API REST (Main.java)

O arquivo **Main.java** implementa a API REST usando Javalin, com endpoints para:

**Usuários:**
- `GET /usuarios` — lista todos os usuários
- `GET /usuarios/{id}` — busca usuário por ID
- `POST /usuarios` — cria novo usuário
- `PUT /usuarios/{id}` — atualiza usuário existente
- `DELETE /usuarios/{id}` — deleta usuário
- `GET /usuarios/{id}/perfil` — retorna perfil completo do usuário com progressos e conquistas
- `GET /usuarios/{id}/respostas` — lista respostas de um usuário

**Categorias:**
- `GET /categorias` — lista todas as categorias
- `GET /categorias/{id}` — busca categoria por ID
- `POST /categorias` — cria nova categoria
- `PUT /categorias/{id}` — atualiza categoria
- `DELETE /categorias/{id}` — deleta categoria
- `GET /categorias/{id}/progresso/{usuarioId}` — busca progresso do usuário em uma categoria

**Notícias:**
- `GET /noticias` — lista todas as notícias
- `GET /noticias/{id}` — busca notícia por ID
- `GET /noticias/random/{usuarioId}/categoria/{categoriaId}` — retorna notícia aleatória não respondida de uma categoria específica
- `GET /noticias/categoria/{categoriaId}` — lista todas as notícias de uma categoria
- `POST /noticias` — cria nova notícia
- `PUT /noticias/{id}` — atualiza notícia existente
- `DELETE /noticias/{id}` — deleta notícia

**Respostas:**
- `POST /noticias/{id}/responder` — registra resposta do usuário, atualiza pontuação e progresso

**Conquistas:**
- `GET /conquistas` — lista todas as conquistas
- `GET /usuarios/{id}/conquistas` — lista conquistas de um usuário

**Observação:** O sistema foi migrado de arrays em memória para persistência completa em banco de dados PostgreSQL, garantindo que todos os dados sejam salvos permanentemente. Todos os endpoints CRUD (Create, Read, Update, Delete) estão implementados para as principais entidades.

