package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Coordenador;
import com.example.saaapi.model.entity.SupervisorAtividadesComplementares;
import com.example.saaapi.model.repository.SupervisorAtividadesComplementaresRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupervisorAtividadesComplementaresService {
    private SupervisorAtividadesComplementaresRepository repository;

    public SupervisorAtividadesComplementaresService(SupervisorAtividadesComplementaresRepository repository) {
        this.repository = repository;
    }

    public List<SupervisorAtividadesComplementares> getSupervisoresAtividadesComplementares() {
        return repository.findAll();
    }

    public Optional<SupervisorAtividadesComplementares> getSupervisorAtividadesComplementaresById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public SupervisorAtividadesComplementares salvar(SupervisorAtividadesComplementares supervisorAtividadesComplementares) {
        validar(supervisorAtividadesComplementares);
        return repository.save(supervisorAtividadesComplementares);
    }

    @Transactional
    public void excluir(SupervisorAtividadesComplementares supervisorAtividadesComplementares) {
        Objects.requireNonNull(supervisorAtividadesComplementares.getId());
        repository.delete(supervisorAtividadesComplementares);
    }

    public void validar(SupervisorAtividadesComplementares supervisorAtividadesComplementares) {
        if (supervisorAtividadesComplementares.getNome() == null || supervisorAtividadesComplementares.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (supervisorAtividadesComplementares.getEmail() == null || supervisorAtividadesComplementares.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (supervisorAtividadesComplementares.getCurso() == null || supervisorAtividadesComplementares.getCurso().getId() == null || supervisorAtividadesComplementares.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}
