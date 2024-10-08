package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Curso;
import com.example.saaapi.model.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CursoService {

    private CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<Curso> getCursos() {
        return repository.findAll();
    }

    public Optional<Curso> getCursoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Curso salvar(Curso curso) {
        validar(curso);
        return repository.save(curso);
    }

    @Transactional
    public void excluir(Curso curso) {
        Objects.requireNonNull(curso.getId());
        repository.delete(curso);
    }

    public void validar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (curso.getPossuiEstagioObrigatorio() == null) {
            throw new RegraNegocioException("Possui Estágio Obrigatório inválido");
        }
        if (curso.getPossuiEstagioNaoObrigatorio() == null) {
            throw new RegraNegocioException("Possui Estágio Não Obrigatório inválido");
        }
    }
}
