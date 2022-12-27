package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.SupervisorEstagioDTO;
import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.SupervisorEstagio;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.service.SupervisorEstagioService;
import com.example.saaapi.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/supervisoresestagio")
@RequiredArgsConstructor
public class SupervisorEstagioController {

    private final SupervisorEstagioService service;
    private final CursoService cursoService;

    @GetMapping()
    public ResponseEntity get() {
        List<SupervisorEstagio> supervisores = service.getSupervisoresEstagio();
        return ResponseEntity.ok(supervisores.stream().map(SupervisorEstagioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<SupervisorEstagio> supervisor = service.getSupervisorEstagioById(id);
        if (!supervisor.isPresent()) {
            return new ResponseEntity("Supervisor Estágio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(supervisor.map(SupervisorEstagioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(SupervisorEstagioDTO dto) {
        try {
            SupervisorEstagio supervisor = converter(dto);
            supervisor = service.salvar(supervisor);
            return new ResponseEntity(supervisor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, SupervisorEstagioDTO dto) {
        if (!service.getSupervisorEstagioById(id).isPresent()) {
            return new ResponseEntity("Supervisor Estágio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            SupervisorEstagio supervisor = converter(dto);
            supervisor.setId(id);
            service.salvar(supervisor);
            return ResponseEntity.ok(supervisor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<SupervisorEstagio> supervisor = service.getSupervisorEstagioById(id);
        if (!supervisor.isPresent()) {
            return new ResponseEntity("Supervisor Estágio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(supervisor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public SupervisorEstagio converter(SupervisorEstagioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEstagio supervisor = modelMapper.map(dto, SupervisorEstagio.class);
        if (dto.getIdCurso() != null) {
            Optional<Curso> curso = cursoService.getCursoById(dto.getIdCurso());
            if (!curso.isPresent()) {
                supervisor.setCurso(null);
            } else {
                supervisor.setCurso(curso.get());
            }
        }
        return supervisor;
    }
}
