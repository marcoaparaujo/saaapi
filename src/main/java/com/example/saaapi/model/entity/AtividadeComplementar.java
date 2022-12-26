package com.example.saaapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeComplementar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String entidadePromotora;
    private Integer cargaHoraria;
    private String dataInicio;
    private String dataFim;
    private String certificado;
    private String link;

    @ManyToOne
    private Aluno aluno;
    @ManyToOne
    private Categoria categoria;
}
