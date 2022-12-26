package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Coordenador;
import com.example.saaapi.model.entity.Estagio;
import com.example.saaapi.model.repository.CoordenadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CoordenadorService {

    private CoordenadorRepository repository;

    public CoordenadorService(CoordenadorRepository repository) {
        this.repository = repository;
    }

    public List<Coordenador> getCoordenadores() {
        return repository.findAll();
    }

    public Optional<Coordenador> getCoordenadorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Coordenador salvar(Coordenador coordenador) {
        validar(coordenador);
        return repository.save(coordenador);
    }

    @Transactional
    public void excluir(Coordenador coordenador) {
        Objects.requireNonNull(coordenador.getId());
        repository.delete(coordenador);
    }

    public void validar(Coordenador coordenador) {
        if (coordenador.getNome() == null || coordenador.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (coordenador.getEmail() == null || coordenador.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (coordenador.getCurso() == null || coordenador.getCurso().getId() == null || coordenador.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}
