package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String celular;

    public static ProfessorDTO create(Professor coordenador) {
        ModelMapper modelMapper = new ModelMapper();
        ProfessorDTO dto = modelMapper.map(coordenador, ProfessorDTO.class);
        return dto;
    }
}
