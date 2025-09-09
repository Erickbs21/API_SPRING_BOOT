package com.erick.demo.controlador;

import com.erick.demo.entidad.Producto;
import com.erick.demo.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoServicio.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }
    /* --- get para id */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoServicio.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /* --- get para eñ codigo de barras */
    @GetMapping("/codigo-barras/{codigoBarras}")
    public ResponseEntity<Producto> obtenerProductoPorCodigoBarras(@PathVariable String codigoBarras) {
        Optional<Producto> producto = productoServicio.obtenerProductoPorCodigoBarras(codigoBarras);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /* --- get para id */
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoServicio.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoServicio.obtenerProductoPorId(id);
        if (productoExistente.isPresent()) {
            producto.setId(id);
            Producto productoActualizado = productoServicio.guardarProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Optional<Producto> producto = productoServicio.obtenerProductoPorId(id);
        if (producto.isPresent()) {
            productoServicio.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerProductosConStockBajo() {
        List<Producto> productos = productoServicio.obtenerProductosConStockBajo();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam String nombre) {
        List<Producto> productos = productoServicio.buscarProductosPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<Producto> productos = productoServicio.obtenerProductosPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id,
                                                @RequestParam Integer cantidad,
                                                @RequestParam(required = false) String motivo) {
        productoServicio.actualizarStock(id, cantidad, motivo);
        return ResponseEntity.ok().build();
    }
}
