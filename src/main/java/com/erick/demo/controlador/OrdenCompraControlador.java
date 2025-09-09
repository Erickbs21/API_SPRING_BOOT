package com.erick.demo.controlador;


import com.erick.demo.entidad.OrdenCompra;
import com.erick.demo.servicio.OrdenCompraServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes-compra")
@CrossOrigin(origins = "*")
public class OrdenCompraControlador {

    @Autowired
    private OrdenCompraServicio ordenCompraServicio;

    @GetMapping
    public ResponseEntity<List<OrdenCompra>> obtenerTodas() {
        List<OrdenCompra> ordenes = ordenCompraServicio.obtenerTodas();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> obtenerPorId(@PathVariable Long id) {
        Optional<OrdenCompra> orden = ordenCompraServicio.obtenerPorId(id);
        return orden.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numeroOrden}")
    public ResponseEntity<OrdenCompra> obtenerPorNumero(@PathVariable String numeroOrden) {
        Optional<OrdenCompra> orden = ordenCompraServicio.obtenerPorNumeroOrden(numeroOrden);
        return orden.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<OrdenCompra>> obtenerPorProveedor(@PathVariable Long proveedorId) {
        List<OrdenCompra> ordenes = ordenCompraServicio.obtenerPorProveedor(proveedorId);
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping
    public ResponseEntity<OrdenCompra> crear(@Valid @RequestBody OrdenCompra ordenCompra) {
        try {
            OrdenCompra nuevaOrden = ordenCompraServicio.crear(ordenCompra);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompra> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenCompra ordenCompra) {
        try {
            ordenCompra.setId(id);
            OrdenCompra ordenActualizada = ordenCompraServicio.actualizar(ordenCompra);
            return ResponseEntity.ok(ordenActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/recibir")
    public ResponseEntity<OrdenCompra> recibirOrden(@PathVariable Long id) {
        try {
            OrdenCompra ordenRecibida = ordenCompraServicio.recibirOrden(id);
            return ResponseEntity.ok(ordenRecibida);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            ordenCompraServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
