package com.erick.demo.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 50, message = "El código de barras no puede exceder 50 caracteres")
    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonIgnore // ← EVITA RECURSIÓN
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    @JsonIgnore // ← EVITA RECURSIÓN
    private Proveedor proveedor;

    @DecimalMin(value = "0.0", message = "El precio de costo debe ser mayor o igual a 0")
    @Column(name = "precio_costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCosto = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "El precio de venta debe ser mayor o igual to 0")
    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta = BigDecimal.ZERO;

    @Min(value = 0, message = "La cantidad en stock debe ser mayor o igual a 0")
    @Column(name = "cantidad_stock", nullable = false)
    private Integer cantidadStock = 0;

    @Min(value = 0, message = "El stock mínimo debe ser mayor o igual a 0")
    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 0;

    @Size(max = 100, message = "La ubicación no puede exceder 100 caracteres")
    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "activo")
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // ← EVITA RECURSIÓN
    private List<ItemVenta> itemsVenta;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // ← EVITA RECURSIÓN
    private List<AjusteInventario> ajustesInventario;

    // Constructores
    public Producto() {}

    public Producto(String nombre, String descripcion, String codigoBarras, Categoria categoria, Proveedor proveedor,
                    BigDecimal precioCosto, BigDecimal precioVenta, Integer cantidadStock, Integer stockMinimo, String ubicacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.ubicacion = ubicacion;
    }

    // Métodos de utilidad
    public boolean tieneStockBajo() {
        return cantidadStock <= stockMinimo;
    }

    public BigDecimal obtenerGanancia() {
        return precioVenta.subtract(precioCosto);
    }

    public BigDecimal obtenerMargenGanancia() {
        if (precioVenta.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return obtenerGanancia().divide(precioVenta, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public BigDecimal getPrecioCosto() { return precioCosto; }
    public void setPrecioCosto(BigDecimal precioCosto) { this.precioCosto = precioCosto; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public Integer getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(Integer cantidadStock) { this.cantidadStock = cantidadStock; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public List<ItemVenta> getItemsVenta() { return itemsVenta; }
    public void setItemsVenta(List<ItemVenta> itemsVenta) { this.itemsVenta = itemsVenta; }

    public List<AjusteInventario> getAjustesInventario() { return ajustesInventario; }
    public void setAjustesInventario(List<AjusteInventario> ajustesInventario) { this.ajustesInventario = ajustesInventario; }
}