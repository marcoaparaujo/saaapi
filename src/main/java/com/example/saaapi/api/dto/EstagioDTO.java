package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Estagio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstagioDTO {

    private Long id;
    private String dataInicio;
    private String dataFim;
    private Integer cargaHoraria;
    private Integer tipoEstagio;
    private String planoAtividades;
    private Long idAluno;
    private Long idConcedente;

    public static EstagioDTO create(Estagio estagio) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(estagio, EstagioDTO.class);
    }
}
