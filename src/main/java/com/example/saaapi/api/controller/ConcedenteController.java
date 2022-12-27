package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.ConcedenteDTO;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Concedente;
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
@RequestMapping("/api/v1/concedentes")
@RequiredArgsConstructor
public class ConcedenteController {

    private final ConcedenteService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Concedente> concedentes = service.getConcedentes();
        return ResponseEntity.ok(concedentes.stream().map(ConcedenteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Concedente> concedente = service.getConcedenteById(id);
        if (!concedente.isPresent()) {
            return new ResponseEntity("Concedente não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(concedente.map(ConcedenteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ConcedenteDTO dto) {
        try {
            Concedente concedente = converter(dto);
            concedente = service.salvar(concedente);
            return new ResponseEntity(concedente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ConcedenteDTO dto) {
        if (!service.getConcedenteById(id).isPresent()) {
            return new ResponseEntity("Concedente não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Concedente concedente = converter(dto);
            concedente.setId(id);
            service.salvar(concedente);
            return ResponseEntity.ok(concedente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Concedente> concedente = service.getConcedenteById(id);
        if (!concedente.isPresent()) {
            return new ResponseEntity("Concedente não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(concedente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Concedente converter(ConcedenteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Concedente concedente = modelMapper.map(dto, Concedente.class);
        return concedente;
    }
}
