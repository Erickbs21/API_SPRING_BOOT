package com.erick.demo.servicio;

import com.erick.demo.entidad.AjusteInventario;
import com.erick.demo.entidad.Producto;
import com.erick.demo.repositorio.AjusteInventarioRepositorio;
import com.erick.demo.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AjusteInventarioServicio {

    @Autowired
    private AjusteInventarioRepositorio ajusteInventarioRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public List<AjusteInventario> obtenerTodosLosAjustes() {
        return ajusteInventarioRepositorio.findAllOrderByFechaDesc();
    }

    public Optional<AjusteInventario> obtenerAjustePorId(Long id) {
        return ajusteInventarioRepositorio.findById(id);
    }

    public List<AjusteInventario> obtenerTodos() {
        return ajusteInventarioRepositorio.findAllOrderByFechaDesc();
    }

    public Optional<AjusteInventario> obtenerPorId(Long id) {
        return ajusteInventarioRepositorio.findById(id);
    }

    public List<AjusteInventario> obtenerPorProducto(Long productoId) {
        return ajusteInventarioRepositorio.findByProductoId(productoId);
    }

    public List<AjusteInventario> obtenerHistorialPorProducto(Long productoId) {
        return ajusteInventarioRepositorio.findByProductoId(productoId);
    }

    public AjusteInventario procesarAjuste(AjusteInventario ajuste) {
        Optional<Producto> productoOpt = productoRepositorio.findById(ajuste.getProducto().getId());
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Integer stockAnterior = producto.getCantidadStock();

            producto.setCantidadStock(ajuste.getCantidadNueva());
            productoRepositorio.save(producto);

            ajuste.setCantidadAnterior(stockAnterior);
            ajuste.setFechaAjuste(LocalDateTime.now());

            return ajusteInventarioRepositorio.save(ajuste);
        }
        return null;
    }

    public AjusteInventario crearAjuste(Long productoId, AjusteInventario.TipoAjuste tipoAjuste, Integer cantidad, String motivo, String usuario) {
        Optional<Producto> productoOpt = productoRepositorio.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Integer stockAnterior = producto.getCantidadStock();
            Integer stockNuevo;

            if (tipoAjuste == AjusteInventario.TipoAjuste.ENTRADA) {
                stockNuevo = stockAnterior + cantidad;
            } else {
                stockNuevo = stockAnterior - cantidad;
            }

            producto.setCantidadStock(stockNuevo);
            productoRepositorio.save(producto);

            AjusteInventario ajuste = new AjusteInventario();
            ajuste.setProducto(producto);
            ajuste.setTipoAjuste(tipoAjuste);
            ajuste.setCantidadAnterior(stockAnterior);
            ajuste.setCantidadNueva(stockNuevo);
            ajuste.setMotivo(motivo);
            ajuste.setUsuarioAjuste(usuario);
            ajuste.setFechaAjuste(LocalDateTime.now());

            return ajusteInventarioRepositorio.save(ajuste);
        }
        return null;
    }

    public List<AjusteInventario> obtenerAjustesPorTipo(AjusteInventario.TipoAjuste tipoAjuste) {
        return ajusteInventarioRepositorio.findByTipoAjuste(String.valueOf(tipoAjuste));
    }

    public List<AjusteInventario> obtenerAjustesPorProducto(Long productoId) {
        return ajusteInventarioRepositorio.findByProductoId(productoId);
    }

    public List<AjusteInventario> obtenerAjustesPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ajusteInventarioRepositorio.findAjustesPorRangoFecha(fechaInicio, fechaFin);
    }
}
