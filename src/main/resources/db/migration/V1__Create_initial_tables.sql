-- V1__Create_initial_tables.sql
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    nivel INTEGER NOT NULL DEFAULT 1,
    pontuacao_total INTEGER NOT NULL DEFAULT 0,
    titulo_atual VARCHAR(100)
);

CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    total_niveis INTEGER NOT NULL DEFAULT 0,
    pontos_para_proximo_nivel INTEGER NOT NULL DEFAULT 0,
    caminho_imagem_completa VARCHAR(255)
);

CREATE TABLE noticias (
    id BIGSERIAL PRIMARY KEY,
    categoria_id BIGINT REFERENCES categorias(id),
    titulo VARCHAR(255) NOT NULL,
    conteudo TEXT NOT NULL,
    eh_verdadeira BOOLEAN NOT NULL,
    explicacao TEXT NOT NULL,
    dificuldade INTEGER NOT NULL DEFAULT 1,
    fontes TEXT
);

CREATE TABLE respostas (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES usuarios(id),
    noticia_id BIGINT REFERENCES noticias(id),
    resposta_usuario BOOLEAN NOT NULL,
    esta_correta BOOLEAN NOT NULL,
    pontos_ganhos INTEGER NOT NULL,
    UNIQUE(usuario_id, noticia_id)
);

CREATE TABLE progresso_categoria (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES usuarios(id),
    categoria_id BIGINT REFERENCES categorias(id),
    nivel_atual INTEGER NOT NULL DEFAULT 0,
    pontos_maestria INTEGER NOT NULL DEFAULT 0,
    pecas_desbloqueadas INTEGER[],
    UNIQUE(usuario_id, categoria_id)
);

CREATE TABLE conquistas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    icone VARCHAR(50) NOT NULL,
    criterio VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    valor_requerido INTEGER NOT NULL
);

CREATE TABLE conquistas_usuario (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES usuarios(id),
    conquista_id BIGINT REFERENCES conquistas(id),
    data_desbloqueio TIMESTAMP NOT NULL,
    visualizada BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE(usuario_id, conquista_id)
);