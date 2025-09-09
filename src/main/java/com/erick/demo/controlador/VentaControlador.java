package com.erick.demo.controlador;

import com.erick.demo.entidad.Venta;
import com.erick.demo.servicio.VentaServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaControlador {

    @Autowired
    private VentaServicio ventaServicio;

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodas() {
        List<Venta> ventas = ventaServicio.obtenerTodas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        Optional<Venta> venta = ventaServicio.obtenerPorId(id);
        return venta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numeroVenta}")
    public ResponseEntity<Venta> obtenerPorNumero(@PathVariable String numeroVenta) {
        Optional<Venta> venta = ventaServicio.obtenerPorNumeroVenta(numeroVenta);
        return venta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint existente con query parameter
    @GetMapping("/fecha")
    public ResponseEntity<List<Venta>> obtenerPorFecha(@RequestParam LocalDate fecha) {
        List<Venta> ventas = ventaServicio.obtenerVentasPorFecha(fecha);
        return ResponseEntity.ok(ventas);
    }

    // NUEVO: Endpoint con path variable para fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Venta>> obtenerPorFechaPath(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Venta> ventas = ventaServicio.obtenerVentasPorFecha(fecha);
        return ResponseEntity.ok(ventas);
    }

    // NUEVO: Endpoint para contar ventas del día
    @GetMapping("/contar-dia")
    public ResponseEntity<Long> contarVentasDelDia() {
        try {
            Long count = ventaServicio.contarVentasDelDia();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // NUEVO: Endpoint para total de ventas del día
    @GetMapping("/total-dia")
    public ResponseEntity<BigDecimal> obtenerTotalVentasDelDia() {
        try {
            BigDecimal total = ventaServicio.obtenerTotalVentasDelDia();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Venta> crear(@Valid @RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaServicio.procesarVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @Valid @RequestBody Venta venta) {
        try {
            venta.setId(id);
            Venta ventaActualizada = ventaServicio.actualizar(venta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            ventaServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}