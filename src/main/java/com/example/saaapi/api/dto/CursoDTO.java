package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Curso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CursoDTO {
    private Long id;
    private String nome;
    private Boolean possuiEstagioObrigatorio;
    private Integer cargaHorariaMinimaEstagioObrigatorio;
    private Integer periodoMinimoEstagioObrigatorio;
    private Boolean possuiEstagioNaoObrigatorio;
    private Integer cargaHorariaMinimaAtividadesComplementares;
    private Long idCoordenador;
    private Long idSupervisorEstagio;
    private Long idSupervisorAtividadesComplementares;

    public static CursoDTO create(Curso curso) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(curso, CursoDTO.class);
    }

}