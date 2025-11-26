package com.renato.controller.dto;

import com.renato.model.Categoria;
import com.renato.model.ProgressoCategoria;

/**
 * Composição mantém os objetos separados e completos, e o frontend acessa: response.categoria.nome,
 * response.progresso.nivelAtual
 */
public class RespostaCategoriaDTO {
    private Categoria categoria;
    private ProgressoCategoria progresso;
    private EstatisticasCategoriaDTO estatisticas;

    public RespostaCategoriaDTO() {
    }

    public RespostaCategoriaDTO(Categoria categoria, ProgressoCategoria progresso, 
                               EstatisticasCategoriaDTO estatisticas) {
        this.categoria = categoria;
        this.progresso = progresso;
        this.estatisticas = estatisticas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ProgressoCategoria getProgresso() {
        return progresso;
    }

    public void setProgresso(ProgressoCategoria progresso) {
        this.progresso = progresso;
    }

    public EstatisticasCategoriaDTO getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticasCategoriaDTO estatisticas) {
        this.estatisticas = estatisticas;
    }
}
