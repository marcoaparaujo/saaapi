package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.SupervisorAtividadesComplementares;
import com.example.saaapi.model.entity.SupervisorEstagio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorAtividadesComplementaresDTO {
    private Long id;
    private String nome;
    private String email;
    private String celular;
    private Long idCurso;

    public static SupervisorAtividadesComplementaresDTO create(SupervisorAtividadesComplementares supervisor) {
        ModelMapper modelMapper = new ModelMapper();
        SupervisorAtividadesComplementaresDTO dto = modelMapper.map(supervisor, SupervisorAtividadesComplementaresDTO.class);
        return dto;
    }
}
