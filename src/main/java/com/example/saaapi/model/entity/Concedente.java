package com.example.saaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Concedente extends Pessoa {

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
}
