package com.estacionamento.estacionamento.adapter.out.persistence.mapper;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.VagaEntity;
import com.estacionamento.estacionamento.domain.model.Vaga;

public class VagaMapper {

    // O id vai junto: sem ele, salvar uma vaga existente criaria uma nova linha.
    public static VagaEntity toEntity(Vaga vaga) {

        return new VagaEntity(
                vaga.getId(),
                vaga.getNumero(),
                vaga.getTipo(),
                vaga.getStatus()
        );
    }

    public static Vaga toDomain(VagaEntity entity) {

        return new Vaga(
                entity.getId(),
                entity.getNumero(),
                entity.getTipo(),
                entity.getStatus()
        );
    }
}