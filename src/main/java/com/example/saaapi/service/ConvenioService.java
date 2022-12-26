package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Convenio;
import com.example.saaapi.model.repository.ConvenioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConvenioService {

    private ConvenioRepository repository;

    public ConvenioService(ConvenioRepository repository) {
        this.repository = repository;
    }

    public List<Convenio> getConvenios() {
        return repository.findAll();
    }

    public Optional<Convenio> getConvenioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Convenio salvar(Convenio convenio) {
        validar(convenio);
        return repository.save(convenio);
    }

    @Transactional
    public void excluir(Convenio convenio) {
        Objects.requireNonNull(convenio.getId());
        repository.delete(convenio);
    }

    public void validar(Convenio convenio) {
        if (convenio.getDataInicio() == null || convenio.getDataInicio().trim().equals("")) {
            throw new RegraNegocioException("Data Início inválida");
        }
        if (convenio.getConcedente() == null || convenio.getConcedente().getId() == null || convenio.getConcedente().getId() == 0) {
            throw new RegraNegocioException("Concedente inválida");
        }
    }
}
