package com.example.saaapi.model.repository;

import com.example.saaapi.model.entity.Aluno;
import com.example.saaapi.model.entity.AtividadeComplementar;
import com.example.saaapi.model.entity.Estagio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AtividadeComplementarRepository extends JpaRepository<AtividadeComplementar, Long> {
    List<AtividadeComplementar> findByAluno(Optional<Aluno> aluno);
}
