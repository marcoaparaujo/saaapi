package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Vaga;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaDTO {

    private Long id;
    private String tituloVaga;
    private String descricaoVaga;
    private Integer cargaHorariaSemanal;
    private String turno;
    private String habilidadesObrigatorias;
    private String habilidadesDesejadas;
    private Float valorBolsa;
    private String beneficios;
    private String dataInicioInscricao;
    private String dataFimInscricao;
    private String linkInscricao;
    private Long idConcedente;
    private Long idCurso;

    public static VagaDTO create(Vaga vaga) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(vaga, VagaDTO.class);
    }
}

