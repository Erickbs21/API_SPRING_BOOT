package com.erick.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @DecimalMin(value = "0.0", message = "El monto de impuestos debe ser mayor o igual a 0")
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // 'efectivo', 'tarjeta', 'transferencia'

    @NotNull(message = "El monto recibido es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto recibido debe ser mayor o igual a 0")
    @Column(name = "payment_received", nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentReceived;

    @DecimalMin(value = "0.0", message = "El cambio debe ser mayor o igual a 0")
    @Column(name = "change_given", precision = 10, scale = 2)
    private BigDecimal changeGiven = BigDecimal.ZERO;

    @Column(name = "status")
    private String status = "completed"; // 'completed', 'cancelled', 'refunded'

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleItem> saleItems;

    // Constructores
    public Sale() {}

    public Sale(Customer customer, BigDecimal totalAmount, BigDecimal taxAmount, String paymentMethod,
                BigDecimal paymentReceived, BigDecimal changeGiven) {
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.paymentMethod = paymentMethod;
        this.paymentReceived = paymentReceived;
        this.changeGiven = changeGiven;
    }

    // Métodos de utilidad
    public BigDecimal getSubtotal() {
        return totalAmount.subtract(taxAmount);
    }

    public int getTotalItems() {
        return saleItems != null ? saleItems.stream().mapToInt(SaleItem::getQuantity).sum() : 0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getPaymentReceived() { return paymentReceived; }
    public void setPaymentReceived(BigDecimal paymentReceived) { this.paymentReceived = paymentReceived; }

    public BigDecimal getChangeGiven() { return changeGiven; }
    public void setChangeGiven(BigDecimal changeGiven) { this.changeGiven = changeGiven; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<SaleItem> getSaleItems() { return saleItems; }
    public void setSaleItems(List<SaleItem> saleItems) { this.saleItems = saleItems; }
}
