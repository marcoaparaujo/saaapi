package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.*;
import com.example.saaapi.model.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoService {

    private AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> getAlunos() {
        return repository.findAll();
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Aluno salvar(Aluno aluno) {
        validar(aluno);
        return repository.save(aluno);
    }

    @Transactional
    public void excluir(Aluno aluno) {
        Objects.requireNonNull(aluno.getId());
        repository.delete(aluno);
    }

    public void validar(Aluno aluno) {
        if (aluno.getMatricula() == null || aluno.getMatricula() == 0) {
            throw new RegraNegocioException("Matrícula inválida");
        }
        if (aluno.getNome() == null || aluno.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (aluno.getCpf() == null || aluno.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (aluno.getEmail() == null || aluno.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (aluno.getCurso() == null || aluno.getCurso().getId() == null || aluno.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}
