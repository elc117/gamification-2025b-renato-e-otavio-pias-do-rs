CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    total_niveis INTEGER NOT NULL DEFAULT 0,
    pontos_para_proximo_nivel INTEGER NOT NULL DEFAULT 0,
    caminho_imagem_completa VARCHAR(255)
);