package com.erick.demo.servicio;

import com.erick.demo.entidad.Producto;
import com.erick.demo.entidad.AjusteInventario;
import com.erick.demo.repositorio.ProductoRepositorio;
import com.erick.demo.repositorio.AjusteInventarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private AjusteInventarioRepositorio ajusteInventarioRepositorio;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepositorio.findByActivoTrue();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepositorio.findById(id);
    }

    public Optional<Producto> obtenerProductoPorCodigoBarras(String codigoBarras) {
        return productoRepositorio.findByCodigoBarras(codigoBarras);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public void eliminarProducto(Long id) {
        Optional<Producto> producto = productoRepositorio.findById(id);
        if (producto.isPresent()) {
            producto.get().setActivo(false);
            productoRepositorio.save(producto.get());
        }
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepositorio.buscarPorNombre(nombre);
    }

    public List<Producto> obtenerProductosConStockBajo() {
        return productoRepositorio.findProductosConStockBajo();
    }

    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepositorio.findByCategoriaIdAndActivoTrue(categoriaId);
    }

    public void actualizarStock(Long productoId, Integer cantidad, String motivo) {
        Optional<Producto> productoOpt = productoRepositorio.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Integer stockAnterior = producto.getCantidadStock();
            Integer stockNuevo = stockAnterior + cantidad;

            producto.setCantidadStock(stockNuevo);
            productoRepositorio.save(producto);

            AjusteInventario ajuste = new AjusteInventario();
            ajuste.setProducto(producto);
            ajuste.setTipoAjuste(cantidad > 0 ? AjusteInventario.TipoAjuste.ENTRADA : AjusteInventario.TipoAjuste.SALIDA);
            ajuste.setCantidadAnterior(stockAnterior);
            ajuste.setCantidadNueva(stockNuevo);
            ajuste.setMotivo(motivo != null ? motivo : "Ajuste manual");
            ajuste.setUsuarioAjuste("Sistema");
            ajuste.setFechaAjuste(LocalDateTime.now());
            ajusteInventarioRepositorio.save(ajuste);
        }
    }

    public Long contarProductosActivos() {
        return productoRepositorio.contarProductosActivos();
    }
}
