package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Concedente;
import com.example.saaapi.model.repository.ConcedenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConcedenteService {

    private ConcedenteRepository repository;

    public ConcedenteService(ConcedenteRepository repository) {
        this.repository = repository;
    }

    public List<Concedente> getConcedentes() {
        return repository.findAll();
    }

    public Optional<Concedente> getConcedenteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Concedente salvar(Concedente concedente) {
        validar(concedente);
        return repository.save(concedente);
    }

    @Transactional
    public void excluir(Concedente concedente) {
        Objects.requireNonNull(concedente.getId());
        repository.delete(concedente);
    }

    public void validar(Concedente concedente) {
        if (concedente.getNome() == null || concedente.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (concedente.getEmail() == null || concedente.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (concedente.getDataInicio() == null || concedente.getDataInicio().trim().equals("")) {
            throw new RegraNegocioException("Data Início inválida");
        }
        if (concedente.getTipoPessoa() == null || concedente.getTipoPessoa() < 1 || concedente.getTipoPessoa() > 2) {
            throw new RegraNegocioException("Tipo Pessoa inválida");
        }
        if (concedente.getNumeroDocumento() == null || concedente.getNumeroDocumento().trim().equals("")) {
            throw new RegraNegocioException("Número Documento inválido");
        }
        if (concedente.getNomeRepresentante() == null || concedente.getNomeRepresentante().trim().equals("")) {
            throw new RegraNegocioException("Nome Representante inválido");
        }
    }
}
