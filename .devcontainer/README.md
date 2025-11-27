# DevContainer - Fact or Fake

## 🎯 O que é isso?

Este DevContainer permite executar o projeto **Fact or Fake** no **GitHub Codespaces** ou localmente no **VS Code** sem precisar instalar NADA manualmente.

## ✨ O que é instalado/configurado automaticamente?

Quando você abrir o projeto no Codespaces ou DevContainer, tudo isso acontece automaticamente:

1. ✅ **Java 17** instalado
2. ✅ **Maven** instalado
3. ✅ **PostgreSQL 15** rodando em container separado
4. ✅ **Banco de dados `fact_or_fake`** criado
5. ✅ **Tabelas criadas** (executa `criar_tabelas_iniciais.sql` automaticamente)
6. ✅ **Dados populados** (executa `popular_tabelas.sql` automaticamente)
7. ✅ **Projeto compilado** e pronto para rodar

**Você não precisa fazer NADA manualmente!** 🎉

---

## 🚀 Como usar no GitHub Codespaces

### Passo 1: Abrir no Codespaces

1. Acesse o repositório no GitHub
2. Clique no botão verde **"Code"**
3. Vá na aba **"Codespaces"**
4. Clique em **"Create codespace on main"**

### Passo 2: Aguardar configuração automática

O VS Code abrirá no navegador e você verá mensagens no terminal mostrando:
- 🎮 Configurando ambiente...
- ☕ Verificando Java e Maven...
- 🐘 Aguardando PostgreSQL inicializar...
- 📊 Verificando banco de dados...
- 🔨 Compilando projeto...
- ✨ **Ambiente PRONTO!**

**Isso leva cerca de 2-3 minutos na primeira vez.**

### Passo 3: Rodar a aplicação

Quando aparecer a mensagem **"✨ Ambiente PRONTO!"**, execute:

```bash
mvn exec:java
```

Pronto! A aplicação estará rodando em `http://localhost:3000`

---

## 🔧 Como usar localmente (VS Code com Docker)

### Pré-requisitos

- VS Code instalado
- Docker Desktop rodando
- Extensão "Dev Containers" instalada

### Passos

1. Abra o projeto no VS Code
2. Pressione `F1` e digite: **"Dev Containers: Reopen in Container"**
3. Aguarde a configuração automática (2-3 minutos)
4. Quando pronto, execute: `mvn exec:java`

---

## 📊 Verificar o banco de dados

### Ver tabelas criadas

```bash
psql -h localhost -U postgres -d fact_or_fake -c '\dt'
```

### Ver categorias inseridas

```bash
psql -h localhost -U postgres -d fact_or_fake -c 'SELECT * FROM categorias;'
```

### Conectar ao banco interativamente

```bash
psql -h localhost -U postgres -d fact_or_fake
```

*(Senha: `postgres`)*

---

## 🧪 Testar a API

```bash
# Listar categorias
curl http://localhost:3000/categorias

# Listar notícias
curl http://localhost:3000/noticias

# Criar usuário
curl -X POST http://localhost:3000/criar-conta \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","email":"teste@example.com","senha":"123456"}'
```

---

## 🛠️ Estrutura do DevContainer

```
.devcontainer/
├── devcontainer.json       # Configuração principal
├── docker-compose.yml      # Orquestração dos containers (app + PostgreSQL)
├── Dockerfile              # Imagem do container de desenvolvimento
├── setup.sh                # Script de configuração automática
└── README.md               # Este arquivo
```

### Como funciona?

1. **docker-compose.yml**: Cria 2 containers:
   - `app`: Container com Java 17 + Maven
   - `db`: Container PostgreSQL 15
   
2. **Volumes montados**: Os scripts SQL são montados em `/docker-entrypoint-initdb.d/`:
   - `1-criar_tabelas.sql` → `src/main/resources/db/migration/criar_tabelas_iniciais.sql`
   - `2-popular_tabelas.sql` → `src/main/resources/db/migration/popular_tabelas.sql`
   
   O PostgreSQL **executa automaticamente** todos os `.sql` nesta pasta na primeira inicialização!

3. **setup.sh**: Aguarda PostgreSQL ficar pronto e compila o projeto

---

## ❓ Problemas Comuns

### PostgreSQL não está respondendo

Se você ver `❌ PostgreSQL não respondeu`, tente:

```bash
# Ver status do container
docker ps

# Ver logs do PostgreSQL
docker logs <container_id_do_postgres>
```

### Tabelas não foram criadas

Verifique se os scripts SQL estão corretos:

```bash
cat src/main/resources/db/migration/criar_tabelas_iniciais.sql
cat src/main/resources/db/migration/popular_tabelas.sql
```

### Recriar tudo do zero

Se algo der errado, você pode recriar o DevContainer:

1. Pressione `F1`
2. Digite: **"Dev Containers: Rebuild Container"**
3. Aguarde a reconstrução completa

---

## 📝 Notas Importantes

- ⚠️ **ATENÇÃO**: O arquivo `hibernate.cfg.xml` está configurado com:
  - URL: `jdbc:postgresql://localhost:5432/fact_or_fake`
  - Usuário: `postgres`
  - Senha: `postgres`
  
  Se você rodar localmente fora do DevContainer, ajuste essas configurações conforme seu ambiente.

- 💾 **Dados persistentes**: O PostgreSQL usa um volume Docker (`postgres-data`), então os dados **não são perdidos** quando você para o container.

- 🔄 **Scripts SQL**: São executados apenas na **primeira inicialização** do PostgreSQL. Se você recriar o volume, eles rodarão novamente.

---

## 🎓 Para a professora avaliar

**Zero configuração necessária!** Apenas:

1. Abra o repositório no GitHub
2. Clique em **"Code" → "Codespaces" → "Create codespace"**
3. Aguarde 2-3 minutos
4. Execute: `mvn exec:java`
5. Acesse: `http://localhost:3000`

**Tudo funciona igual ao localhost!** ✨
