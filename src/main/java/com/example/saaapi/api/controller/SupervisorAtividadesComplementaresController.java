package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.SupervisorAtividadesComplementaresDTO;
import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.SupervisorAtividadesComplementares;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.service.SupervisorAtividadesComplementaresService;
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
@RequestMapping("/api/v1/supervisoresatividadescomplementares")
@RequiredArgsConstructor
public class SupervisorAtividadesComplementaresController {

    private final SupervisorAtividadesComplementaresService service;
    private final CursoService cursoService;

    @GetMapping()
    public ResponseEntity get() {
        List<SupervisorAtividadesComplementares> supervisores = service.getSupervisoresAtividadesComplementares();
        return ResponseEntity.ok(supervisores.stream().map(SupervisorAtividadesComplementaresDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<SupervisorAtividadesComplementares> supervisor = service.getSupervisorAtividadesComplementaresById(id);
        if (!supervisor.isPresent()) {
            return new ResponseEntity("Supervisor Atividades Complementares não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(supervisor.map(SupervisorAtividadesComplementaresDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(SupervisorAtividadesComplementaresDTO dto) {
        try {
            SupervisorAtividadesComplementares supervisor = converter(dto);
            supervisor = service.salvar(supervisor);
            return new ResponseEntity(supervisor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, SupervisorAtividadesComplementaresDTO dto) {
        if (!service.getSupervisorAtividadesComplementaresById(id).isPresent()) {
            return new ResponseEntity("Supervisor Atividades Complementares não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            SupervisorAtividadesComplementares supervisor = converter(dto);
            supervisor.setId(id);
            service.salvar(supervisor);
            return ResponseEntity.ok(supervisor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<SupervisorAtividadesComplementares> supervisor = service.getSupervisorAtividadesComplementaresById(id);
        if (!supervisor.isPresent()) {
            return new ResponseEntity("Supervisor Atividades Complementares não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(supervisor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public SupervisorAtividadesComplementares converter(SupervisorAtividadesComplementaresDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        SupervisorAtividadesComplementares supervisor = modelMapper.map(dto, SupervisorAtividadesComplementares.class);
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
