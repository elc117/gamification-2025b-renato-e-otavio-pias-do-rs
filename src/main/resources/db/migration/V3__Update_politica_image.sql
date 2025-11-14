-- V3__Update_politica_image.sql
-- Atualiza o caminho da imagem da categoria Política para a imagem da balança

UPDATE categorias
SET caminho_imagem_completa = 'assets/images/balanca2.png'
WHERE nome = 'Política';

