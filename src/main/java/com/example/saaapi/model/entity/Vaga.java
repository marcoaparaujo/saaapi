package com.example.saaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    private Concedente concedente;

    @ManyToOne
    private Curso curso;

}
