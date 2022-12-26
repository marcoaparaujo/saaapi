package com.example.saaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estagio {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String dataInicio;
        private String dataFim;
        private Integer cargaHoraria;
        private Integer tipoEstagio;
        private String planoAtividades;

        @ManyToOne
        private Aluno aluno;

        @ManyToOne
        private Concedente concedente;
}
