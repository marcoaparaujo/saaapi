package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.SupervisorEstagio;
import com.example.saaapi.model.repository.SupervisorEstagioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupervisorEstagioService {
    private SupervisorEstagioRepository repository;

    public SupervisorEstagioService(SupervisorEstagioRepository repository) {
        this.repository = repository;
    }

    public List<SupervisorEstagio> getSupervisoresEstagio() {
        return repository.findAll();
    }

    public Optional<SupervisorEstagio> getSupervisorEstagioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public SupervisorEstagio salvar(SupervisorEstagio supervisorEstagio) {
        validar(supervisorEstagio);
        return repository.save(supervisorEstagio);
    }

    @Transactional
    public void excluir(SupervisorEstagio supervisorEstagio) {
        Objects.requireNonNull(supervisorEstagio.getId());
        repository.delete(supervisorEstagio);
    }

    public void validar(SupervisorEstagio supervisorEstagio) {
        if (supervisorEstagio.getNome() == null || supervisorEstagio.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (supervisorEstagio.getEmail() == null || supervisorEstagio.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (supervisorEstagio.getCurso() == null || supervisorEstagio.getCurso().getId() == null || supervisorEstagio.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}
