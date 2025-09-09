package com.erick.demo.servicio;

import com.erick.demo.entidad.Venta;
import com.erick.demo.entidad.ItemVenta;
import com.erick.demo.entidad.Producto;
import com.erick.demo.repositorio.VentaRepositorio;
import com.erick.demo.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaServicio {

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepositorio.findAll();
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepositorio.findById(id);
    }

    public Optional<Venta> obtenerVentaPorNumero(String numeroVenta) {
        return ventaRepositorio.findByNumeroVenta(numeroVenta);
    }

    public Venta procesarVenta(Venta venta) {
        venta.setNumeroVenta(generarNumeroVenta());
        venta.setFechaVenta(LocalDateTime.now());

        for (ItemVenta item : venta.getItems()) {
            Producto producto = item.getProducto();
            producto.setCantidadStock(producto.getCantidadStock() - item.getCantidad());
            productoRepositorio.save(producto);
        }

        return ventaRepositorio.save(venta);
    }

    public List<Venta> obtenerVentasDelDia() {
        return ventaRepositorio.findVentasDelDia();
    }

    public List<Venta> obtenerVentasPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepositorio.findVentasPorRangoFecha(fechaInicio, fechaFin);
    }

    public Double calcularVentasDelDia() {
        Double total = ventaRepositorio.calcularVentasDelDia();
        return total != null ? total : 0.0;
    }

    public Long contarVentasDelDia() {
        return ventaRepositorio.contarVentasDelDia();
    }

    // NUEVO MÉTODO: Obtener total de ventas del día como BigDecimal
    public BigDecimal obtenerTotalVentasDelDia() {
        BigDecimal total = ventaRepositorio.sumTotalVentasDelDia();
        return total != null ? total : BigDecimal.ZERO;
    }

    // NUEVO MÉTODO: Contar ventas por fecha específica
    public Long contarVentasPorFecha(LocalDate fecha) {
        return ventaRepositorio.countByFecha(fecha);
    }

    // NUEVO MÉTODO: Sumar total de ventas por fecha específica
    public BigDecimal obtenerTotalVentasPorFecha(LocalDate fecha) {
        BigDecimal total = ventaRepositorio.sumTotalByFecha(fecha);
        return total != null ? total : BigDecimal.ZERO;
    }

    private String generarNumeroVenta() {
        return "V-" + System.currentTimeMillis();
    }

    public List<Venta> obtenerTodas() {
        return obtenerTodasLasVentas();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return obtenerVentaPorId(id);
    }

    public Optional<Venta> obtenerPorNumeroVenta(String numeroVenta) {
        return obtenerVentaPorNumero(numeroVenta);
    }

    public List<Venta> obtenerVentasPorFecha(LocalDate fecha) {
        LocalDateTime fechaInicio = fecha.atStartOfDay();
        LocalDateTime fechaFin = fecha.atTime(23, 59, 59);
        return obtenerVentasPorRangoFecha(fechaInicio, fechaFin);
    }

    public Venta actualizar(Venta venta) {
        return ventaRepositorio.save(venta);
    }

    public void eliminar(Long id) {
        ventaRepositorio.deleteById(id);
    }
}