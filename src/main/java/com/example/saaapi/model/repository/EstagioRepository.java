package com.example.saaapi.model.repository;

import com.example.saaapi.model.entity.Aluno;
import com.example.saaapi.model.entity.Estagio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstagioRepository extends JpaRepository<Estagio, Long> {
    List<Estagio> findByAluno(Optional<Aluno> aluno);
}

