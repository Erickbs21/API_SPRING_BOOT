package com.erick.demo.repositorio;

import com.erick.demo.entidad.AjusteInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AjusteInventarioRepositorio extends JpaRepository<AjusteInventario, Long> {

    List<AjusteInventario> findByProductoId(Long productoId);

    @Query("SELECT a FROM AjusteInventario a WHERE a.fechaAjuste BETWEEN :fechaInicio AND :fechaFin ORDER BY a.fechaAjuste DESC")
    List<AjusteInventario> findAjustesPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("SELECT a FROM AjusteInventario a WHERE a.tipoAjuste = :tipo ORDER BY a.fechaAjuste DESC")
    List<AjusteInventario> findByTipoAjuste(String tipo);

    @Query("SELECT a FROM AjusteInventario a ORDER BY a.fechaAjuste DESC")
    List<AjusteInventario> findAllOrderByFechaDesc();
}
