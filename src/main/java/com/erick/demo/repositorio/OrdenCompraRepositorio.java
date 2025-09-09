package com.erick.demo.repositorio;

import com.erick.demo.entidad.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepositorio extends JpaRepository<OrdenCompra, Long> {

    Optional<OrdenCompra> findByNumeroOrden(String numeroOrden);

    List<OrdenCompra> findByProveedorId(Long proveedorId);

    List<OrdenCompra> findByEstado(String estado);

    @Query("SELECT o FROM OrdenCompra o WHERE o.estado = 'PENDIENTE' ORDER BY o.fechaOrden DESC")
    List<OrdenCompra> findOrdenesPendientes();

    @Query("SELECT COUNT(o) FROM OrdenCompra o WHERE o.estado = 'PENDIENTE'")
    Long contarOrdenesPendientes();
}
