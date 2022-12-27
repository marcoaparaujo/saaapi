package com.example.saaapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Boolean possuiEstagioObrigatorio;
    private Integer cargaHorariaMinimaEstagioObrigatorio;
    private Integer periodoMinimoEstagioObrigatorio;
    private Boolean possuiEstagioNaoObrigatorio;
    private Integer cargaHorariaMinimaAtividadesComplementares;

    @ManyToOne
    private Professor coordenador;
    @ManyToOne
    private Professor supervisorEstagio;
    @ManyToOne
    private Professor supervisorAtividadesComplementares;

    @JsonIgnore
    @OneToMany(mappedBy = "curso")
    private List<Aluno> alunos;
}
