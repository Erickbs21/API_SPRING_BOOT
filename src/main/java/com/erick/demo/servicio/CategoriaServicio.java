package com.erick.demo.servicio;

import com.erick.demo.entidad.Categoria;
import com.erick.demo.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepositorio.findByActivaTrue();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepositorio.findById(id);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        Optional<Categoria> categoria = categoriaRepositorio.findById(id);
        if (categoria.isPresent()) {
            categoria.get().setActiva(false);
            categoriaRepositorio.save(categoria.get());
        }
    }

    public List<Categoria> buscarCategoriasPorNombre(String nombre) {
        return categoriaRepositorio.buscarPorNombre(nombre);
    }

    public Long contarCategoriasActivas() {
        return categoriaRepositorio.contarCategoriasActivas();
    }
}
