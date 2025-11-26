package com.renato.controller.dto;

import com.renato.model.Conquista;
import java.time.LocalDateTime;

/**
 * Herança OO clássica: ConquistaComStatus ESTENDE Conquista.
 * Adiciona campos de estado do usuário (desbloqueada, dataDesbloqueio).
 * Frontend acessa: conquista.nome, conquista.desbloqueada (sem aninhamento).
 */
public class ConquistaComStatus extends Conquista {
    private LocalDateTime dataDesbloqueio;
    private boolean desbloqueada;

    public ConquistaComStatus() {
        super();
    }

    public ConquistaComStatus(Conquista conquista, LocalDateTime dataDesbloqueio, boolean desbloqueada) {
        // Copiar campos da superclasse
        super(conquista.getId(), 
              conquista.getNome(), 
              conquista.getDescricao(), 
              conquista.getIcone(), 
              conquista.getCriterio(), 
              conquista.getTipo(), 
              conquista.getValorRequerido());
        
        // Campos específicos de status
        this.dataDesbloqueio = dataDesbloqueio;
        this.desbloqueada = desbloqueada;
    }

    public LocalDateTime getDataDesbloqueio() {
        return dataDesbloqueio;
    }

    public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }

    public boolean isDesbloqueada() {
        return desbloqueada;
    }

    public void setDesbloqueada(boolean desbloqueada) {
        this.desbloqueada = desbloqueada;
    }
}
