package com.erick.demo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ajustes_inventario")
public class AjusteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @NotNull(message = "El producto es obligatorio")
    private Producto producto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ajuste", nullable = false)
    @NotNull(message = "El tipo de ajuste es obligatorio")
    private TipoAjuste tipoAjuste;

    @Column(name = "cantidad_anterior", nullable = false)
    @Min(value = 0, message = "La cantidad anterior debe ser mayor o igual a 0")
    private Integer cantidadAnterior;

    @Column(name = "cantidad_nueva", nullable = false)
    @Min(value = 0, message = "La cantidad nueva debe ser mayor o igual a 0")
    private Integer cantidadNueva;

    @Column(name = "diferencia", nullable = false)
    private Integer diferencia;

    @Column(name = "motivo", nullable = false, length = 200)
    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "usuario_ajuste", nullable = false, length = 100)
    @NotBlank(message = "El usuario que realiza el ajuste es obligatorio")
    private String usuarioAjuste;

    @Column(name = "fecha_ajuste", nullable = false)
    @NotNull(message = "La fecha de ajuste es obligatoria")
    private LocalDateTime fechaAjuste = LocalDateTime.now();

    // Constructores
    public AjusteInventario() {}

    public AjusteInventario(Producto producto, TipoAjuste tipoAjuste, Integer cantidadAnterior,
                            Integer cantidadNueva, String motivo, String usuarioAjuste) {
        this.producto = producto;
        this.tipoAjuste = tipoAjuste;
        this.cantidadAnterior = cantidadAnterior;
        this.cantidadNueva = cantidadNueva;
        this.diferencia = cantidadNueva - cantidadAnterior;
        this.motivo = motivo;
        this.usuarioAjuste = usuarioAjuste;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public TipoAjuste getTipoAjuste() { return tipoAjuste; }
    public void setTipoAjuste(TipoAjuste tipoAjuste) { this.tipoAjuste = tipoAjuste; }

    public Integer getCantidadAnterior() { return cantidadAnterior; }
    public void setCantidadAnterior(Integer cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
        calcularDiferencia();
    }

    public Integer getCantidadNueva() { return cantidadNueva; }
    public void setCantidadNueva(Integer cantidadNueva) {
        this.cantidadNueva = cantidadNueva;
        calcularDiferencia();
    }

    public Integer getDiferencia() { return diferencia; }
    public void setDiferencia(Integer diferencia) { this.diferencia = diferencia; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getUsuarioAjuste() { return usuarioAjuste; }
    public void setUsuarioAjuste(String usuarioAjuste) { this.usuarioAjuste = usuarioAjuste; }

    public LocalDateTime getFechaAjuste() { return fechaAjuste; }
    public void setFechaAjuste(LocalDateTime fechaAjuste) { this.fechaAjuste = fechaAjuste; }

    private void calcularDiferencia() {
        if (cantidadAnterior != null && cantidadNueva != null) {
            this.diferencia = cantidadNueva - cantidadAnterior;
        }
    }

    public enum TipoAjuste {
        ENTRADA, SALIDA, CORRECCION, MERMA, DEVOLUCION
    }
}
