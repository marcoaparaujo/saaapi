package com.example.saaapi.api.controller;

import com.example.saaapi.api.dto.CursoDTO;
import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Aluno;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.model.entity.Professor;
import com.example.saaapi.service.CursoService;
import com.example.saaapi.service.ProfessorService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
@Api("API de Cursos")
public class CursoController {

    private final CursoService service;
    private final ProfessorService professorService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity get() {
        List<Curso> cursos = service.getCursos();
        return ResponseEntity.ok(cursos.stream().map(CursoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um curso")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Curso encontrado"),
            @ApiResponse(code = 404, message = "Curso não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do curso") Long id) {
        Optional<Curso> curso = service.getCursoById(id);
        if (!curso.isPresent()) {
            return new ResponseEntity("Curso não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(curso.map(CursoDTO::create));
    }


    @PostMapping()
    @ApiOperation("Salva um novo curso")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Curso salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o curso")
    })
    public ResponseEntity post(CursoDTO dto) {
        try {
            Curso curso = converter(dto);
            curso = service.salvar(curso);
            return new ResponseEntity(curso, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CursoDTO dto) {
        if (!service.getCursoById(id).isPresent()) {
            return new ResponseEntity("Curso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Curso curso = converter(dto);
            curso.setId(id);
            service.salvar(curso);
            return ResponseEntity.ok(curso);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Curso> curso = service.getCursoById(id);
        if (!curso.isPresent()) {
            return new ResponseEntity("Curso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(curso.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Curso converter(CursoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Curso curso = modelMapper.map(dto, Curso.class);
        if (dto.getIdCoordenador() != null) {
            Optional<Professor> coordenador = professorService.getProfessorById(dto.getIdCoordenador());
            if (!coordenador.isPresent()) {
                curso.setCoordenador(null);
            } else {
                curso.setCoordenador(coordenador.get());
            }
        }
        if (dto.getIdSupervisorEstagio() != null) {
            Optional<Professor> supervisorEstagio = professorService.getProfessorById(dto.getIdSupervisorEstagio());
            if (!supervisorEstagio.isPresent()) {
                curso.setSupervisorEstagio(null);
            } else {
                curso.setSupervisorEstagio(supervisorEstagio.get());
            }
        }
        if (dto.getIdSupervisorAtividadesComplementares() != null) {
            Optional<Professor> supervisorAtividadesComplementares = professorService.getProfessorById(dto.getIdSupervisorAtividadesComplementares());
            if (!supervisorAtividadesComplementares.isPresent()) {
                curso.setSupervisorAtividadesComplementares(null);
            } else {
                curso.setSupervisorAtividadesComplementares(supervisorAtividadesComplementares.get());
            }
        }
        return curso;
    }
}