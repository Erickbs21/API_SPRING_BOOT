package com.erick.demo.repositorio;

import com.erick.demo.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrue();

    Optional<Producto> findByCodigoBarras(String codigoBarras);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Producto> buscarPorNombre(String nombre);

    @Query("SELECT p FROM Producto p WHERE p.cantidadStock < 10")
    List<Producto> findProductosConStockBajo();

    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.activo = true")
    Long contarProductosActivos();
}
