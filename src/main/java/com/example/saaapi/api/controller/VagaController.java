package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.VagaDTO;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Vaga;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.model.entity.Concedente;
import com.example.saaapi.service.VagaService;
import com.example.saaapi.service.CursoService;
import com.example.saaapi.service.ConcedenteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vagas")
@RequiredArgsConstructor
@CrossOrigin
public class VagaController {

    private final VagaService service;
    private final CursoService cursoService;
    private final ConcedenteService concedenteService;

    @GetMapping()
    public ResponseEntity get() {
        List<Vaga> vagas = service.getVagas();
        return ResponseEntity.ok(vagas.stream().map(VagaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Vaga> vaga = service.getVagaById(id);
        if (!vaga.isPresent()) {
            return new ResponseEntity("Vaga não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(vaga.map(VagaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VagaDTO dto) {
        try {
            Vaga vaga = converter(dto);
            vaga = service.salvar(vaga);
            return new ResponseEntity(vaga, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VagaDTO dto) {
        if (!service.getVagaById(id).isPresent()) {
            return new ResponseEntity("Vaga não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Vaga vaga = converter(dto);
            vaga.setId(id);
            service.salvar(vaga);
            return ResponseEntity.ok(vaga);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Vaga> vaga = service.getVagaById(id);
        if (!vaga.isPresent()) {
            return new ResponseEntity("Vaga não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(vaga.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Vaga converter(VagaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Vaga vaga = modelMapper.map(dto, Vaga.class);
        if (dto.getIdCurso() != null) {
            Optional<Curso> curso = cursoService.getCursoById(dto.getIdCurso());
            if (!curso.isPresent()) {
                vaga.setCurso(null);
            } else {
                vaga.setCurso(curso.get());
            }
        }
        if (dto.getIdConcedente() != null) {
            Optional<Concedente> concedente = concedenteService.getConcedenteById(dto.getIdConcedente());
            if (!concedente.isPresent()) {
                vaga.setConcedente(null);
            } else {
                vaga.setConcedente(concedente.get());
            }
        }
        return vaga;
    }
}
