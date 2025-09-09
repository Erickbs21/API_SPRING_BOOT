package com.erick.demo.servicio;


import com.erick.demo.entidad.Proveedor;
import com.erick.demo.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepositorio.findByActivoTrue();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepositorio.findByIdAndActivoTrue(id);
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepositorio.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        Optional<Proveedor> proveedor = proveedorRepositorio.findById(id);
        if (proveedor.isPresent()) {
            Proveedor prov = proveedor.get();
            prov.setActivo(false);
            proveedorRepositorio.save(prov);
        }
    }

    public List<Proveedor> buscarProveedoresPorNombre(String nombre) {
        return proveedorRepositorio.findByNombreContainingIgnoreCaseAndActivoTrue(nombre);
    }
}