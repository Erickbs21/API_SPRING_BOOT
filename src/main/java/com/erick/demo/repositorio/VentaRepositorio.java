package com.erick.demo.repositorio;

import com.erick.demo.entidad.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {

    Optional<Venta> findByNumeroVenta(String numeroVenta);

    @Query("SELECT v FROM Venta v WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin ORDER BY v.fechaVenta DESC")
    List<Venta> findVentasPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("SELECT v FROM Venta v WHERE CAST(v.fechaVenta AS date) = CURRENT_DATE ORDER BY v.fechaVenta DESC")
    List<Venta> findVentasDelDia();

    @Query("SELECT COALESCE(SUM(v.total), 0.0) FROM Venta v WHERE CAST(v.fechaVenta AS date) = CURRENT_DATE")
    Double calcularVentasDelDia();

    @Query("SELECT COUNT(v) FROM Venta v WHERE CAST(v.fechaVenta AS date) = CURRENT_DATE")
    Long contarVentasDelDia();

    // NUEVOS MÉTODOS PARA LOS ENDPOINTS QUE FALTABAN

    // Contar ventas por fecha específica
    @Query("SELECT COUNT(v) FROM Venta v WHERE CAST(v.fechaVenta AS date) = :fecha")
    Long countByFecha(@Param("fecha") LocalDate fecha);

    // Sumar total de ventas por fecha específica
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE CAST(v.fechaVenta AS date) = :fecha")
    BigDecimal sumTotalByFecha(@Param("fecha") LocalDate fecha);

    // Sumar total de ventas del día (BigDecimal)
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE CAST(v.fechaVenta AS date) = CURRENT_DATE")
    BigDecimal sumTotalVentasDelDia();

    // Buscar ventas por fecha específica
    @Query("SELECT v FROM Venta v WHERE CAST(v.fechaVenta AS date) = :fecha ORDER BY v.fechaVenta DESC")
    List<Venta> findByFecha(@Param("fecha") LocalDate fecha);
}