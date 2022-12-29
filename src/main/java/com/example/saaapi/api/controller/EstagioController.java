package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.EstagioDTO;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Aluno;
import com.example.saaapi.model.entity.Estagio;
import com.example.saaapi.model.entity.Concedente;
import com.example.saaapi.service.AlunoService;
import com.example.saaapi.service.EstagioService;
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
@RequestMapping("/api/v1/estagios")
@RequiredArgsConstructor
public class EstagioController {

    private final EstagioService service;
    private final AlunoService alunoService;
    private final ConcedenteService concedenteService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity get() {
        List<Estagio> estagios = service.getEstagios();
        return ResponseEntity.ok(estagios.stream().map(EstagioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estagio> estagio = service.getEstagioById(id);
        if (!estagio.isPresent()) {
            return new ResponseEntity("Estágio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estagio.map(EstagioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(EstagioDTO dto) {
        try {
            Estagio estagio = converter(dto);
            estagio = service.salvar(estagio);
            return new ResponseEntity(estagio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, EstagioDTO dto) {
        if (!service.getEstagioById(id).isPresent()) {
            return new ResponseEntity("Estágio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Estagio estagio = converter(dto);
            estagio.setId(id);
            service.salvar(estagio);
            return ResponseEntity.ok(estagio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Estagio> estagio = service.getEstagioById(id);
        if (!estagio.isPresent()) {
            return new ResponseEntity("Estágio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(estagio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Estagio converter(EstagioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estagio estagio = modelMapper.map(dto, Estagio.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                estagio.setAluno(null);
            } else {
                estagio.setAluno(aluno.get());
            }
        }
        if (dto.getIdConcedente() != null) {
            Optional<Concedente> concedente = concedenteService.getConcedenteById(dto.getIdConcedente());
            if (!concedente.isPresent()) {
                estagio.setConcedente(null);
            } else {
                estagio.setConcedente(concedente.get());
            }
        }
        return estagio;
    }
}
