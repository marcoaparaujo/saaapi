package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.AtividadeComplementar;
import com.example.saaapi.model.repository.AtividadeComplementarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AtividadeComplementarService {
    private AtividadeComplementarRepository repository;

    public AtividadeComplementarService(AtividadeComplementarRepository repository) {
        this.repository = repository;
    }

    public List<AtividadeComplementar> getAtividadesComplementares() {
        return repository.findAll();
    }

    public Optional<AtividadeComplementar> getAtividadeComplementarById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AtividadeComplementar salvar(AtividadeComplementar atividadeComplementar) {
        validar(atividadeComplementar);
        return repository.save(atividadeComplementar);
    }

    @Transactional
    public void excluir(AtividadeComplementar atividadeComplementar) {
        Objects.requireNonNull(atividadeComplementar.getId());
        repository.delete(atividadeComplementar);
    }

    public void validar(AtividadeComplementar atividadeComplementar) {
        if (atividadeComplementar.getAluno() == null || atividadeComplementar.getAluno().getId() == null || atividadeComplementar.getAluno().getId() == 0) {
            throw new RegraNegocioException("Aluno inválido");
        }
        if (atividadeComplementar.getTitulo() == null || atividadeComplementar.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Título inválido");
        }
        if (atividadeComplementar.getEntidadePromotora() == null || atividadeComplementar.getEntidadePromotora().trim().equals("")) {
            throw new RegraNegocioException("Entidade Promotora inválida");
        }
        if (atividadeComplementar.getCargaHoraria() == null || atividadeComplementar.getCargaHoraria() == 0) {
            throw new RegraNegocioException("Carga Horária inválida");
        }
        if (atividadeComplementar.getCategoria() == null || atividadeComplementar.getCategoria().getId() == null || atividadeComplementar.getCategoria().getId() == 0) {
            throw new RegraNegocioException("Categoria inválida");
        }
        if (atividadeComplementar.getDataInicio() == null || atividadeComplementar.getDataInicio().trim().equals("")) {
            throw new RegraNegocioException("Data Início inválida");
        }
    }
}
