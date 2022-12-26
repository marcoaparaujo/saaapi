package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String descricao;
    private Integer cargaHorariaMinima;
    private Integer cargaHorariaMaxima;
    private Integer quantidadeMinimaCategoria;

    public static CategoriaDTO create(Categoria categoria) {
        ModelMapper modelMapper = new ModelMapper();
        CategoriaDTO dto = modelMapper.map(categoria, CategoriaDTO.class);
        return dto;
    }
}
