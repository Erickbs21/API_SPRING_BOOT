package com.erick.demo.controlador;

import com.erick.demo.entidad.AjusteInventario;
import com.erick.demo.servicio.AjusteInventarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ajustes-inventario")
@CrossOrigin(origins = "*")
public class AjusteInventarioControlador {

    @Autowired
    private AjusteInventarioServicio ajusteInventarioServicio;

    @GetMapping
    public ResponseEntity<List<AjusteInventario>> obtenerTodos() {
        List<AjusteInventario> ajustes = ajusteInventarioServicio.obtenerTodosLosAjustes();
        return ResponseEntity.ok(ajustes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AjusteInventario> obtenerPorId(@PathVariable Long id) {
        Optional<AjusteInventario> ajuste = ajusteInventarioServicio.obtenerPorId(id);
        return ajuste.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<AjusteInventario>> obtenerPorProducto(@PathVariable Long productoId) {
        List<AjusteInventario> ajustes = ajusteInventarioServicio.obtenerPorProducto(productoId);
        return ResponseEntity.ok(ajustes);
    }

    @PostMapping
    public ResponseEntity<AjusteInventario> crear(@Valid @RequestBody AjusteInventario ajusteInventario) {
        try {
            AjusteInventario nuevoAjuste = ajusteInventarioServicio.procesarAjuste(ajusteInventario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAjuste);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/historial/producto/{productoId}")
    public ResponseEntity<List<AjusteInventario>> obtenerHistorialProducto(@PathVariable Long productoId) {
        List<AjusteInventario> historial = ajusteInventarioServicio.obtenerHistorialPorProducto(productoId);
        return ResponseEntity.ok(historial);
    }
}
