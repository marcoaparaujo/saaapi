package com.example.saaapi.model.repository;

import com.example.saaapi.model.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
