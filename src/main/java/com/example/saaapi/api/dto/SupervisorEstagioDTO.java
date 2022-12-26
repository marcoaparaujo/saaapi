package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.SupervisorEstagio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorEstagioDTO {
    private Long id;
    private String nome;
    private String email;
    private String celular;
    private Long idCurso;

    public static SupervisorEstagioDTO create(SupervisorEstagio supervisor) {
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEstagioDTO dto = modelMapper.map(supervisor, SupervisorEstagioDTO.class);
        return dto;
    }
}

