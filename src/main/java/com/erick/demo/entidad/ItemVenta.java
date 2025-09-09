package com.erick.demo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "items_venta")
public class ItemVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @NotNull(message = "La venta es obligatoria")
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @NotNull(message = "El producto es obligatorio")
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.0", message = "El precio unitario debe ser mayor o igual a 0")
    private BigDecimal precioUnitario;

    @Column(name = "descuento", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "El descuento debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", message = "El descuento no puede ser mayor a 100%")
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
    private BigDecimal subtotal;

    // Constructores
    public ItemVenta() {}

    public ItemVenta(Venta venta, Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
        calcularSubtotal();
    }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    private void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            if (descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal montoDescuento = total.multiply(descuento).divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
                total = total.subtract(montoDescuento);
            }
            this.subtotal = total;
        }
    }
}
