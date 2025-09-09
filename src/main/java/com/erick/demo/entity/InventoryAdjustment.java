package com.erick.demo.entity;

import com.erick.demo.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_adjustments")
public class InventoryAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank(message = "El tipo de ajuste es obligatorio")
    @Column(name = "adjustment_type", nullable = false)
    private String adjustmentType; // 'increase', 'decrease', 'correction'

    @NotNull(message = "El cambio de cantidad es obligatorio")
    @Column(name = "quantity_change", nullable = false)
    private Integer quantityChange;

    @NotBlank(message = "La razón del ajuste es obligatoria")
    @Size(max = 200, message = "La razón no puede exceder 200 caracteres")
    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "notes")
    private String notes;

    @NotBlank(message = "El usuario que realizó el ajuste es obligatorio")
    @Size(max = 100, message = "El nombre del usuario no puede exceder 100 caracteres")
    @Column(name = "adjusted_by", nullable = false)
    private String adjustedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructores
    public InventoryAdjustment() {}

    public InventoryAdjustment(Product product, String adjustmentType, Integer quantityChange,
                               String reason, String notes, String adjustedBy) {
        this.product = product;
        this.adjustmentType = adjustmentType;
        this.quantityChange = quantityChange;
        this.reason = reason;
        this.notes = notes;
        this.adjustedBy = adjustedBy;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getAdjustmentType() { return adjustmentType; }
    public void setAdjustmentType(String adjustmentType) { this.adjustmentType = adjustmentType; }

    public Integer getQuantityChange() { return quantityChange; }
    public void setQuantityChange(Integer quantityChange) { this.quantityChange = quantityChange; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getAdjustedBy() { return adjustedBy; }
    public void setAdjustedBy(String adjustedBy) { this.adjustedBy = adjustedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
