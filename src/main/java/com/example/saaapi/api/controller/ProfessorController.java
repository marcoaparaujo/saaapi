package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.ProfessorDTO;
import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.model.entity.Professor;
import com.example.saaapi.service.CursoService;
import com.example.saaapi.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService service;
    private final CursoService cursoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Professor> professores = service.getProfessores();
        return ResponseEntity.ok(professores.stream().map(ProfessorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Professor> professor = service.getProfessorById(id);
        if (!professor.isPresent()) {
            return new ResponseEntity("Professor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(professor.map(ProfessorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ProfessorDTO dto) {
        try {
            Professor professor = converter(dto);
            professor = service.salvar(professor);
            return new ResponseEntity(professor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ProfessorDTO dto) {
        if (!service.getProfessorById(id).isPresent()) {
            return new ResponseEntity("Professor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Professor professor = converter(dto);
            professor.setId(id);
            service.salvar(professor);
            return ResponseEntity.ok(professor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Professor> professor = service.getProfessorById(id);
        if (!professor.isPresent()) {
            return new ResponseEntity("Professor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(professor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Professor converter(ProfessorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Professor professor = modelMapper.map(dto, Professor.class);
        return professor;
    }
}