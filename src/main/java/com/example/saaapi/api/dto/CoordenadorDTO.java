package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Coordenador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordenadorDTO {
    private Long id;
    private String nome;
    private String email;
    private String celular;
    private Long idCurso;

    public static CoordenadorDTO create(Coordenador coordenador) {
        ModelMapper modelMapper = new ModelMapper();
        CoordenadorDTO dto = modelMapper.map(coordenador, CoordenadorDTO.class);
        return dto;
    }
}
