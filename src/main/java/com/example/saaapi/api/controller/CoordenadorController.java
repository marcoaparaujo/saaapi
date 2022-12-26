package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.CoordenadorDTO;
import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.model.entity.Concedente;
import com.example.saaapi.model.entity.Coordenador;
import com.example.saaapi.service.CursoService;
import com.example.saaapi.service.CoordenadorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/coordenadores")
@RequiredArgsConstructor
public class CoordenadorController {

    private final CoordenadorService service;
    private final CursoService cursoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Coordenador> professores = service.getCoordenadores();
        return ResponseEntity.ok(professores.stream().map(CoordenadorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Coordenador> professor = service.getCoordenadorById(id);
        if (!professor.isPresent()) {
            return new ResponseEntity("Coordenador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(professor.map(CoordenadorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(CoordenadorDTO dto) {
        try {
            Coordenador coordenador = converter(dto);
            coordenador = service.salvar(coordenador);
            return new ResponseEntity(coordenador, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CoordenadorDTO dto) {
        if (!service.getCoordenadorById(id).isPresent()) {
            return new ResponseEntity("Coordenador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Coordenador coordenador = converter(dto);
            coordenador.setId(id);
            service.salvar(coordenador);
            return ResponseEntity.ok(coordenador);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Coordenador> professor = service.getCoordenadorById(id);
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

    public Coordenador converter(CoordenadorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Coordenador coordenador = modelMapper.map(dto, Coordenador.class);
        Concedente concedente = modelMapper.map(dto, Concedente.class);
        if (dto.getIdCurso() != null) {
            Optional<Curso> curso = cursoService.getCursoById(dto.getIdCurso());
            if (!curso.isPresent()) {
                coordenador.setCurso(null);
            } else {
                coordenador.setCurso(curso.get());
            }
        }
        return coordenador;
    }
}