package com.example.saaapi.api.dto;

import com.example.saaapi.model.entity.Concedente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcedenteDTO {
    private Long id;
    private String nome;
    private String email;
    private String celular;
    private String dataInicio;
    private String dataFim;
    private Integer tipoPessoa;
    private String numeroDocumento;
    private String registroConselhoProfissional;
    private Integer numeroEmpregados;
    private String nomeRepresentante;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static ConcedenteDTO create(Concedente concedente) {
        ModelMapper modelMapper = new ModelMapper();
        ConcedenteDTO dto = modelMapper.map(concedente, ConcedenteDTO.class);
        return dto;
    }
}
