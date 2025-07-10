package com.app.inven.pojo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "product_category", nullable = false)
    private String productCategory;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "product_description", length = 500)
    private String productDescription;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
	@JsonIgnore  // Prevents infinite loop when serializing Product -> Supplier
    private Supplier supplier;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)// (inverse side).
    								//Stock (the owning side) must have @ManyToOne @JoinColumn(name = "product_id").
	//indicates that the stocks field is managed by the Stock entity (no column is created here).
	@JsonIgnore  // Breaks the circular reference
    private List<Stock> stocks;

    
    

 
}
