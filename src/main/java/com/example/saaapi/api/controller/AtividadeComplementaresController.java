package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.AlunoDTO;

import com.example.saaapi.api.dto.AtividadeComplementarDTO;
import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Aluno;
import com.example.saaapi.model.entity.AtividadeComplementar;
import com.example.saaapi.model.entity.Categoria;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.service.AlunoService;
import com.example.saaapi.service.AtividadeComplementarService;
import com.example.saaapi.service.CategoriaService;
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
@RequestMapping("/api/v1/atividadescomplementares")
@RequiredArgsConstructor
public class AtividadeComplementaresController {

    private final AtividadeComplementarService service;
    private final AlunoService alunoService;
    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity get() {
        List<AtividadeComplementar> atividadeComplementares = service.getAtividadesComplementares();
        return ResponseEntity.ok(atividadeComplementares.stream().map(AtividadeComplementarDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AtividadeComplementar> atividadeComplementar = service.getAtividadeComplementarById(id);
        if (!atividadeComplementar.isPresent()) {
            return new ResponseEntity("Atividade Complementar não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(atividadeComplementar.map(AtividadeComplementarDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(AtividadeComplementarDTO dto) {
        try {
            AtividadeComplementar atividadeComplementar = converter(dto);
            atividadeComplementar = service.salvar(atividadeComplementar);
            return new ResponseEntity(atividadeComplementar, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, AtividadeComplementarDTO dto) {
        if (!service.getAtividadeComplementarById(id).isPresent()) {
            return new ResponseEntity("Atividade Complementar não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            AtividadeComplementar atividadeComplementar = converter(dto);
            atividadeComplementar.setId(id);
            service.salvar(atividadeComplementar);
            return ResponseEntity.ok(atividadeComplementar);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AtividadeComplementar> atividadeComplementar = service.getAtividadeComplementarById(id);
        if (!atividadeComplementar.isPresent()) {
            return new ResponseEntity("Atividade Complementar não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(atividadeComplementar.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AtividadeComplementar converter(AtividadeComplementarDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AtividadeComplementar atividadeComplementar = modelMapper.map(dto, AtividadeComplementar.class);
        if (dto.getIdAluno() != null) {
            Optional<Aluno> aluno = alunoService.getAlunoById(dto.getIdAluno());
            if (!aluno.isPresent()) {
                atividadeComplementar.setAluno(null);
            } else {
                atividadeComplementar.setAluno(aluno.get());
            }
        }
        if (dto.getIdCategoria() != null) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(dto.getIdCategoria());
            if (!categoria.isPresent()) {
                atividadeComplementar.setCategoria(null);
            } else {
                atividadeComplementar.setCategoria(categoria.get());
            }
        }
        return atividadeComplementar;
    }
}
