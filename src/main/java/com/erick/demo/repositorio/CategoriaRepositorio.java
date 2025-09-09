package com.erick.demo.repositorio;

import com.erick.demo.entidad.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    List<Categoria> findByActivaTrue();

    Optional<Categoria> findByIdAndActivaTrue(Long id);

    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND c.activa = true")
    List<Categoria> buscarPorNombre(String nombre);

    @Query("SELECT COUNT(c) FROM Categoria c WHERE c.activa = true")
    Long contarCategoriasActivas();
}
