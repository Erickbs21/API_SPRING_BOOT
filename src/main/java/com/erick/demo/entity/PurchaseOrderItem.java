package com.erick.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "La cantidad ordenada es obligatoria")
    @Min(value = 1, message = "La cantidad ordenada debe ser mayor a 0")
    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;

    @Min(value = 0, message = "La cantidad recibida debe ser mayor o igual a 0")
    @Column(name = "quantity_received")
    private Integer quantityReceived = 0;

    @NotNull(message = "El costo unitario es obligatorio")
    @DecimalMin(value = "0.0", message = "El costo unitario debe ser mayor o igual a 0")
    @Column(name = "unit_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructores
    public PurchaseOrderItem() {}

    public PurchaseOrderItem(PurchaseOrder purchaseOrder, Product product, Integer quantityOrdered, BigDecimal unitCost) {
        this.purchaseOrder = purchaseOrder;
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.unitCost = unitCost;
        this.subtotal = unitCost.multiply(new BigDecimal(quantityOrdered));
    }

    // Métodos de utilidad
    public void calculateSubtotal() {
        this.subtotal = unitCost.multiply(new BigDecimal(quantityOrdered));
    }

    public boolean isFullyReceived() {
        return quantityReceived.equals(quantityOrdered);
    }

    public Integer getPendingQuantity() {
        return quantityOrdered - quantityReceived;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
        calculateSubtotal();
    }

    public Integer getQuantityReceived() { return quantityReceived; }
    public void setQuantityReceived(Integer quantityReceived) { this.quantityReceived = quantityReceived; }

    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
        calculateSubtotal();
    }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
