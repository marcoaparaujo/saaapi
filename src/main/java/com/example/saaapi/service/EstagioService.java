package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Estagio;
import com.example.saaapi.model.repository.EstagioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstagioService {

    private EstagioRepository repository;

    public EstagioService(EstagioRepository repository) {
        this.repository = repository;
    }

    public List<Estagio> getEstagios() {
        return repository.findAll();
    }

    public Optional<Estagio> getEstagioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Estagio salvar(Estagio estagio) {
        validar(estagio);
        return repository.save(estagio);
    }

    @Transactional
    public void excluir(Estagio estagio) {
        Objects.requireNonNull(estagio.getId());
        repository.delete(estagio);
    }

    public void validar(Estagio estagio) {
        if (estagio.getDataInicio() == null || estagio.getDataInicio().trim().equals("")) {
            throw new RegraNegocioException("Data Início inválida");
        }
        if (estagio.getDataFim() == null || estagio.getDataFim().trim().equals("")) {
            throw new RegraNegocioException("Data Fim inválida");
        }
        if (estagio.getTipoEstagio() == null || estagio.getTipoEstagio() < 1 || estagio.getTipoEstagio() > 2) {
            throw new RegraNegocioException("Tipo Pessoa inválida");
        }
        if (estagio.getCargaHoraria() == null || estagio.getCargaHoraria() == 0) {
            throw new RegraNegocioException("Carga Horária inválida");
        }
        if (estagio.getPlanoAtividades() == null || estagio.getPlanoAtividades().trim().equals("")) {
            throw new RegraNegocioException("Plano Atividades inválido");
        }
        if (estagio.getConcedente() == null || estagio.getConcedente().getId() == null || estagio.getConcedente().getId() == 0) {
            throw new RegraNegocioException("Concedente inválida");
        }
        if (estagio.getAluno() == null || estagio.getAluno().getId() == null || estagio.getAluno().getId() == 0) {
            throw new RegraNegocioException("Aluno inválido");
        }
    }
}
