package com.erick.demo.servicio;

import com.erick.demo.entidad.OrdenCompra;
import com.erick.demo.entidad.DetalleOrdenCompra;
import com.erick.demo.entidad.Producto;
import com.erick.demo.repositorio.OrdenCompraRepositorio;
import com.erick.demo.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdenCompraServicio {

    @Autowired
    private OrdenCompraRepositorio ordenCompraRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    // Coincide con obtenerTodas() en el controlador
    public List<OrdenCompra> obtenerTodas() {
        return ordenCompraRepositorio.findAll();
    }

    // Coincide con obtenerPorId(id)
    public Optional<OrdenCompra> obtenerPorId(Long id) {
        return ordenCompraRepositorio.findById(id);
    }

    // Coincide con obtenerPorNumeroOrden(numeroOrden)
    public Optional<OrdenCompra> obtenerPorNumeroOrden(String numeroOrden) {
        return ordenCompraRepositorio.findByNumeroOrden(numeroOrden);
    }

    // Coincide con crear(ordenCompra)
    public OrdenCompra crear(OrdenCompra orden) {
        orden.setNumeroOrden(generarNumeroOrden());
        orden.setFechaOrden(LocalDateTime.now());
        orden.setEstado(OrdenCompra.EstadoOrden.PENDIENTE);
        return ordenCompraRepositorio.save(orden);
    }

    // Coincide con actualizar(ordenCompra)
    public OrdenCompra actualizar(OrdenCompra orden) {
        return ordenCompraRepositorio.save(orden);
    }

    // Coincide con recibirOrden(id)
    public OrdenCompra recibirOrden(Long ordenId) {
        Optional<OrdenCompra> ordenOpt = ordenCompraRepositorio.findById(ordenId);
        if (ordenOpt.isPresent()) {
            OrdenCompra orden = ordenOpt.get();

            for (DetalleOrdenCompra detalle : orden.getDetalles()) {
                Producto producto = detalle.getProducto();
                producto.setCantidadStock(producto.getCantidadStock() + detalle.getCantidad());
                productoRepositorio.save(producto);
            }

            orden.setEstado(OrdenCompra.EstadoOrden.RECIBIDA);
            return ordenCompraRepositorio.save(orden);
        }
        return null;
    }

    // Coincide con obtenerPorProveedor(proveedorId)
    public List<OrdenCompra> obtenerPorProveedor(Long proveedorId) {
        return ordenCompraRepositorio.findByProveedorId(proveedorId);
    }

    public List<OrdenCompra> obtenerOrdenesPendientes() {
        return ordenCompraRepositorio.findOrdenesPendientes();
    }

    public Long contarOrdenesPendientes() {
        return ordenCompraRepositorio.contarOrdenesPendientes();
    }

    public void eliminar(Long id) {
        ordenCompraRepositorio.deleteById(id);
    }

    private String generarNumeroOrden() {
        return "OC-" + System.currentTimeMillis();
    }
}
