package com.erick.demo.controlador;


import com.erick.demo.entidad.Proveedor;
import com.erick.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = proveedorServicio.obtenerTodosLosProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorServicio.obtenerProveedorPorId(id);
        return proveedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@Valid @RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorServicio.guardarProveedor(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor) {
        Optional<Proveedor> proveedorExistente = proveedorServicio.obtenerProveedorPorId(id);
        if (proveedorExistente.isPresent()) {
            proveedor.setId(id);
            Proveedor proveedorActualizado = proveedorServicio.guardarProveedor(proveedor);
            return ResponseEntity.ok(proveedorActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorServicio.obtenerProveedorPorId(id);
        if (proveedor.isPresent()) {
            proveedorServicio.eliminarProveedor(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Proveedor>> buscarProveedores(@RequestParam String nombre) {
        List<Proveedor> proveedores = proveedorServicio.buscarProveedoresPorNombre(nombre);
        return ResponseEntity.ok(proveedores);
    }
}