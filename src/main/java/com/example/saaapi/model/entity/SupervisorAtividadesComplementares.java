package com.example.saaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorAtividadesComplementares extends Pessoa {
    @ManyToOne
    private Curso curso;
}
