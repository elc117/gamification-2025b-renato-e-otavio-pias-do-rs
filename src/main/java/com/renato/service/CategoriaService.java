package com.renato.service;

import com.renato.model.Categoria;
import com.renato.repository.CategoriaRepository;
import java.util.List;

/**
 * Service responsável por operações relacionadas a categorias.
 */
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService() {
        this.categoriaRepository = new CategoriaRepository();
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria encontrarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId);
    }

    public Categoria encontrarCategoriaPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    public Categoria salvarCategoria(Categoria categoria) {
        if (categoria != null) {
            categoria.setId(null);
        }
        return categoriaRepository.save(categoria);
    }

    public void atualizarCategoria(Categoria categoria) {
        categoriaRepository.update(categoria);
    }

    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria != null) {
            categoriaRepository.delete(categoria);
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }
}
