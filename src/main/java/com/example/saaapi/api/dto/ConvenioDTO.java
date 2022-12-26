package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Convenio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioDTO {
    private Long id;
    private String dataInicio;
    private String dataFim;
    private Long idConcedente;

    public static ConvenioDTO create(Convenio convenio) {
        ModelMapper modelMapper = new ModelMapper();
        ConvenioDTO dto = modelMapper.map(convenio, ConvenioDTO.class);
        return dto;
    }
}

