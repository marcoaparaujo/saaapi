package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.ConvenioDTO;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Convenio;
import com.example.saaapi.model.entity.Concedente;
import com.example.saaapi.service.ConvenioService;
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
@RequestMapping("/api/v1/convenios")
@RequiredArgsConstructor
public class ConvenioController {

    private final ConvenioService service;
    private final ConcedenteService concedenteService;

    @GetMapping()
    public ResponseEntity get() {
        List<Convenio> convenios = service.getConvenios();
        return ResponseEntity.ok(convenios.stream().map(ConvenioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Convenio> convenio = service.getConvenioById(id);
        if (!convenio.isPresent()) {
            return new ResponseEntity("Convênio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(convenio.map(ConvenioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ConvenioDTO dto) {
        try {
            Convenio convenio = converter(dto);
            convenio = service.salvar(convenio);
            return new ResponseEntity(convenio, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ConvenioDTO dto) {
        if (!service.getConvenioById(id).isPresent()) {
            return new ResponseEntity("Convênio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Convenio convenio = converter(dto);
            convenio.setId(id);
            service.salvar(convenio);
            return ResponseEntity.ok(convenio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Convenio> convenio = service.getConvenioById(id);
        if (!convenio.isPresent()) {
            return new ResponseEntity("Convênio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(convenio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Convenio converter(ConvenioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Convenio convenio = modelMapper.map(dto, Convenio.class);
        if (dto.getIdConcedente() != null) {
            Optional<Concedente> concedente = concedenteService.getConcedenteById(dto.getIdConcedente());
            if (!concedente.isPresent()) {
                convenio.setConcedente(null);
            } else {
                convenio.setConcedente(concedente.get());
            }
        }
        return convenio;
    }
}
