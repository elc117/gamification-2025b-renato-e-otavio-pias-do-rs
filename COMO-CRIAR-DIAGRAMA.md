# 📊 Guia: Como Visualizar o Diagrama de Classes no IntelliJ IDEA

## ✅ Modificações Realizadas

Adicionei **associações diretas** entre as classes para que o IntelliJ IDEA mostre as ligações automaticamente no diagrama UML:

### Associações Criadas:

1. **Noticia → Categoria**
   - Adicionado: `private Categoria categoria;`
   
2. **Resposta → Usuario e Noticia**
   - Adicionado: `private Usuario usuario;`
   - Adicionado: `private Noticia noticia;`

3. **ProgressoCategoria → Usuario e Categoria**
   - Adicionado: `private Usuario usuario;`
   - Adicionado: `private Categoria categoria;`

4. **ConquistaUsuario → Usuario e Conquista**
   - Adicionado: `private Usuario usuario;`
   - Adicionado: `private Conquista conquista;`

---

## 🎯 Como Criar o Diagrama no IntelliJ IDEA

### **Método 1: Diagrama Automático (Recomendado)**

1. **Abra o Project Explorer** (Alt + 1)

2. **Navegue até a pasta:** `src/main/java/com/renato/model`

3. **Clique com botão direito** na pasta `model`

4. **Selecione:** `Diagrams` → `Show Diagram` → `Java Classes`
   - Ou use o atalho: **`Ctrl + Alt + Shift + U`**

5. **Escolha o tipo de diagrama:**
   - Selecione: **"Java Class Diagrams"**

6. **Agora você verá o diagrama!** 🎉

---

### **Método 2: Diagrama Individual**

1. **Abra qualquer classe** (ex: `Usuario.java`)

2. **Clique com botão direito** dentro do código

3. **Selecione:** `Diagrams` → `Show Diagram` → `Show Diagram...`

4. **O diagrama será exibido** mostrando a classe e suas relações

---

## 🔗 Como Adicionar/Remover Classes no Diagrama

### **Adicionar Classes:**

1. Com o diagrama aberto, **clique com botão direito** no espaço vazio

2. Selecione: **`Add Class to Diagram...`**

3. Digite o nome da classe que deseja adicionar

4. Pressione **Enter**

### **Adicionar TODAS as classes do pacote:**

1. Clique com botão direito no espaço vazio

2. Selecione: **`Select All Classes`**

3. Ou use o atalho: **`Ctrl + A`** (depois de abrir o menu de adicionar)

### **Remover Classes:**

1. **Clique na classe** que deseja remover

2. Pressione **Delete** ou **Backspace**

---

## ⚙️ Configurando a Visualização do Diagrama

### **Mostrar/Ocultar Elementos:**

1. **Barra de ferramentas do diagrama** (topo do diagrama)

2. Clique nos ícones para mostrar/ocultar:
   - 📦 **Fields** (Atributos)
   - 🔧 **Constructors** (Construtores)
   - ⚙️ **Methods** (Métodos)
   - 📝 **Properties** (Propriedades)
   - 🔗 **Dependencies** (Dependências)

### **Opções Úteis:**

- **Campos (Fields):** Clique no ícone de "campo" para mostrar/ocultar atributos
- **Métodos (Methods):** Clique no ícone de "método" para mostrar/ocultar métodos
- **Visibilidade:** Configure para mostrar apenas públicos, protegidos, etc.

### **Layout Automático:**

1. Clique com botão direito no diagrama

2. Selecione: **`Apply Current Layout`** ou **`Apply Orthogonal Layout`**

3. Isso organiza as classes automaticamente

---

## 📐 Visualizando as Ligações (Associações)

### **As ligações aparecerão automaticamente mostrando:**

✅ **Noticia ──► Categoria**
   - Seta indicando que Noticia tem uma referência a Categoria

✅ **Resposta ──► Usuario**
   - Seta indicando que Resposta tem uma referência a Usuario

✅ **Resposta ──► Noticia**
   - Seta indicando que Resposta tem uma referência a Noticia

✅ **ProgressoCategoria ──► Usuario**
   - Seta indicando progresso do usuário

✅ **ProgressoCategoria ──► Categoria**
   - Seta indicando progresso em uma categoria

✅ **ConquistaUsuario ──► Usuario**
   - Seta indicando conquista de um usuário

✅ **ConquistaUsuario ──► Conquista**
   - Seta indicando qual conquista foi obtida

---

## 🎨 Personalizando o Diagrama

### **Alterar Layout:**

1. **Botão direito no diagrama** → `Layout`

2. Escolha entre:
   - **Orthogonal** (linhas retas com ângulos de 90°)
   - **Hierarchical** (hierárquico)
   - **Organic** (orgânico)
   - **Circular** (circular)

### **Salvar como Imagem:**

1. **Botão direito no diagrama**

2. Selecione: **`Export to File...`**

3. Escolha o formato:
   - PNG
   - SVG
   - DOT (Graphviz)

4. Salve onde desejar

---

## 🖼️ Exemplo de Como Ficará o Diagrama

```
┌─────────────────┐
│    Usuario      │
│─────────────────│
│ - id            │
│ - nome          │
│ - email         │
│ - nivel         │
└─────────────────┘
        ▲
        │
        │ (referenciado por)
        │
┌───────┴─────────────┐
│    Resposta         │
│─────────────────────│
│ - id                │
│ - usuarioId         │
│ - noticiaId         │
│ - usuario      ──────► Usuario
│ - noticia      ──────► Noticia
│ - respostaUsuario   │
│ - estaCorreta       │
│ - pontosGanhos      │
└─────────────────────┘
```

---

## 🚀 Atalhos Úteis

| Ação | Atalho |
|------|--------|
| Abrir diagrama | `Ctrl + Alt + Shift + U` |
| Adicionar classe | `Ins` ou `Insert` |
| Remover classe | `Del` ou `Backspace` |
| Zoom In | `Ctrl + +` |
| Zoom Out | `Ctrl + -` |
| Ajustar ao tamanho | `Ctrl + 0` |
| Selecionar tudo | `Ctrl + A` |
| Salvar como imagem | `Ctrl + S` |

---

## 🔍 Tipos de Setas no Diagrama

| Seta | Significado |
|------|-------------|
| `─────►` | **Associação** (uma classe tem referência a outra) |
| `- - ->` | **Dependência** (usa temporariamente) |
| `◄─────` | **Agregação** (tem um, mas pode existir independente) |
| `◄─◆───` | **Composição** (tem um, e não existe sem ele) |
| `◄─────` | **Herança** (extends) |
| `- - -▷` | **Implementação** (implements) |

---

## ✨ Dicas Extras

### **1. Navegação Rápida:**
- **Duplo clique** em uma classe no diagrama para abrir o código

### **2. Filtrar Classes:**
- Use a **barra de pesquisa** no topo do diagrama

### **3. Múltiplos Diagramas:**
- Você pode criar vários diagramas diferentes
- Útil para mostrar diferentes visões do sistema

### **4. Sincronizar com Código:**
- O diagrama atualiza automaticamente quando você modifica o código
- As associações aparecem quando você adiciona atributos de tipo classe

### **5. Melhor Visualização:**
- Configure para mostrar **apenas public** para um diagrama mais limpo
- Use **layout orthogonal** para melhor legibilidade

---

## 🎓 Entendendo as Ligações do Seu Projeto

### **Relacionamentos 1:N (Um para Muitos):**

1. **Usuario → Respostas**
   - Um usuário pode ter várias respostas

2. **Usuario → ProgressoCategoria**
   - Um usuário pode ter progresso em várias categorias

3. **Usuario → ConquistaUsuario**
   - Um usuário pode ter várias conquistas

4. **Categoria → Noticias**
   - Uma categoria contém várias notícias

5. **Categoria → ProgressoCategoria**
   - Uma categoria pode ter progresso de vários usuários

6. **Noticia → Respostas**
   - Uma notícia pode ser respondida várias vezes

7. **Conquista → ConquistaUsuario**
   - Uma conquista pode ser obtida por vários usuários

---

## 📝 Próximos Passos

Agora que as associações estão configuradas:

1. ✅ Abra o diagrama usando `Ctrl + Alt + Shift + U`
2. ✅ Adicione todas as classes do pacote `model`
3. ✅ Configure o layout para **Orthogonal**
4. ✅ Ajuste a visualização (mostre campos e métodos)
5. ✅ Exporte o diagrama como PNG para documentação

---

**Pronto!** Agora você consegue visualizar todas as ligações entre as classes no diagrama UML do IntelliJ IDEA! 🎉

Se tiver alguma dúvida, me avise!

