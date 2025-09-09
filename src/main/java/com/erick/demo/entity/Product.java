package com.erick.demo.entity;

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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Size(max = 50, message = "El código de barras no puede exceder 50 caracteres")
    @Column(name = "barcode", unique = true)
    private String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @DecimalMin(value = "0.0", message = "El precio de costo debe ser mayor o igual a 0")
    @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "El precio de venta debe ser mayor o igual a 0")
    @Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice = BigDecimal.ZERO;

    @Min(value = 0, message = "La cantidad en stock debe ser mayor o igual a 0")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Min(value = 0, message = "El stock mínimo debe ser mayor o igual a 0")
    @Column(name = "min_stock", nullable = false)
    private Integer minStock = 0;

    @Size(max = 100, message = "La ubicación no puede exceder 100 caracteres")
    @Column(name = "location")
    private String location;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleItem> saleItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<com.erick.demo.entity.InventoryAdjustment> inventoryAdjustments;

    // Constructores
    public Product() {}

    public Product(String name, String description, String barcode, Category category, Supplier supplier,
                   BigDecimal costPrice, BigDecimal salePrice, Integer stockQuantity, Integer minStock, String location) {
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.category = category;
        this.supplier = supplier;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
        this.minStock = minStock;
        this.location = location;
    }

    // Métodos de utilidad
    public boolean isLowStock() {
        return stockQuantity <= minStock;
    }

    public BigDecimal getProfit() {
        return salePrice.subtract(costPrice);
    }

    public BigDecimal getProfitMargin() {
        if (salePrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getProfit().divide(salePrice, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }

    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<SaleItem> getSaleItems() { return saleItems; }
    public void setSaleItems(List<SaleItem> saleItems) { this.saleItems = saleItems; }

    public List<com.erick.demo.entity.InventoryAdjustment> getInventoryAdjustments() { return inventoryAdjustments; }
    public void setInventoryAdjustments(List<com.erick.demo.entity.InventoryAdjustment> inventoryAdjustments) { this.inventoryAdjustments = inventoryAdjustments; }
}
