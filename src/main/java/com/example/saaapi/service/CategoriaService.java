package com.example.saaapi.service;

import com.example.saaapi.exception.RegraNegocioException;
import com.example.saaapi.model.entity.Categoria;
import com.example.saaapi.model.repository.AlunoRepository;
import com.example.saaapi.model.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoriaService {

    private CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> getCategorias() {
        return repository.findAll();
    }

    public Optional<Categoria> getCategoriaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        validar(categoria);
        return repository.save(categoria);
    }

    @Transactional
    public void excluir(Categoria categoria) {
        Objects.requireNonNull(categoria.getId());
        repository.delete(categoria);
    }

    public void validar(Categoria categoria) {
        if (categoria.getDescricao() == null || categoria.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválida");
        }
        if (categoria.getCargaHorariaMinima() == null || categoria.getCargaHorariaMinima() == 0) {
            throw new RegraNegocioException("Carga Horária Mínima inválida");
        }
        if (categoria.getCargaHorariaMaxima() == null || categoria.getCargaHorariaMaxima() == 0) {
            throw new RegraNegocioException("Carga Horária Máxima inválida");
        }
        if (categoria.getQuantidadeMinimaCategoria() == null || categoria.getQuantidadeMinimaCategoria() == 0) {
            throw new RegraNegocioException("Quantidade Mínima Catetoria inválida");
        }
    }
}
