package com.app.inven.pojo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shelves")
@Getter
@Setter
@NoArgsConstructor
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelf_id")
    private Long shelfId;

    @Column(name = "shelf_name", nullable = false)
    private String shelfName;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "current_occupancy", nullable = false)
    private Integer currentOccupancy;
    
    @Column(name = "location", nullable = false)
    private String location;
    @ManyToOne(optional = false)
    
    @JoinColumn(name = "warehouse_id", nullable = false)
    @JsonBackReference
    private Warehouse warehouse;

    @Column(name = "is_temperature_controlled", nullable = false)
    private Boolean isTemperatureControlled;

    @Column(name = "last_inspection_date")
    private LocalDate lastInspectionDate;
    
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "shelf",fetch = FetchType.EAGER)
    private List<Stock> stocks;


    
}
