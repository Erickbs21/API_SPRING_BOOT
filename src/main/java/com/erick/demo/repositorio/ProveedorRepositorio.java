package com.erick.demo.repositorio;

import com.erick.demo.entidad.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByActivoTrue();

    Optional<Proveedor> findByIdAndActivoTrue(Long id);

    List<Proveedor> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);

    Optional<Proveedor> findByEmailAndActivoTrue(String email);
}