package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Vaga;
import com.example.saaapi.model.repository.VagaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VagaService {
    private VagaRepository repository;

    public VagaService(VagaRepository repository) {
        this.repository = repository;
    }

    public List<Vaga> getVagas() {
        return repository.findAll();
    }

    public Optional<Vaga> getVagaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Vaga salvar(Vaga vaga) {
        validar(vaga);
        return repository.save(vaga);
    }

    @Transactional
    public void excluir(Vaga vaga) {
        Objects.requireNonNull(vaga.getId());
        repository.delete(vaga);
    }

    public void validar(Vaga vaga) {
        if (vaga.getTituloVaga() == null || vaga.getTituloVaga().trim().equals("")) {
            throw new RegraNegocioException("Título Vaga inválido");
        }
        if (vaga.getCargaHorariaSemanal() == null || vaga.getCargaHorariaSemanal() == 0) {
            throw new RegraNegocioException("Carga Horária Semanal inválida");
        }
        if (vaga.getTurno() == null || vaga.getTurno().trim().equals("")) {
            throw new RegraNegocioException("Turno inválido");
        }
        if (vaga.getDataInicioInscricao() == null || vaga.getDataInicioInscricao().trim().equals("")) {
            throw new RegraNegocioException("Data Início Inscrição inválida");
        }
        if (vaga.getDataFimInscricao() == null || vaga.getDataFimInscricao().trim().equals("")) {
            throw new RegraNegocioException("Data Fim Inscrição inválida");
        }
        if (vaga.getConcedente() == null || vaga.getConcedente().getId() == null || vaga.getConcedente().getId() == 0) {
            throw new RegraNegocioException("Concedente inválida");
        }
        if (vaga.getCurso() == null || vaga.getCurso().getId() == null || vaga.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}
