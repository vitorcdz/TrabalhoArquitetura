package com.estacionamento.estacionamento.adapter.out.persistence.mapper;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.VeiculoEntity;
import com.estacionamento.estacionamento.domain.model.Veiculo;

public class VeiculoMapper {

    public static VeiculoEntity toEntity(Veiculo veiculo) {

        return new VeiculoEntity(
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getTipo()
        );
    }

    public static Veiculo toDomain(VeiculoEntity entity) {

        return new Veiculo(
                entity.getId(),
                entity.getPlaca(),
                entity.getModelo(),
                entity.getCor(),
                entity.getTipo()
        );
    }
}
