package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.AtividadeComplementar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeComplementarDTO {
    private Long id;
    private String titulo;
    private String entidadePromotora;
    private Integer cargaHoraria;
    private String dataInicio;
    private String dataFim;
    private String certificado;
    private String link;
    private Long idAluno;
    private Long idCategoria;

    public static AtividadeComplementarDTO create(AtividadeComplementar atividadeComplementar) {
        ModelMapper modelMapper = new ModelMapper();
        AtividadeComplementarDTO dto = modelMapper.map(atividadeComplementar, AtividadeComplementarDTO.class);
        return dto;
    }
}
