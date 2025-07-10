package com.app.inven.DTO;

public class ShelfDTO {
    private Long id;
    private String shelfName;
    private String location;
    private String warehouseName;

    // Constructors
    public ShelfDTO() {}

    public ShelfDTO(Long id, String shelfName, String location, String warehouseName) {
        this.id = id;
        this.shelfName = shelfName;
        this.location = location;
        this.warehouseName = warehouseName;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShelfName() { return shelfName; }
    public void setShelfName(String shelfName) { this.shelfName = shelfName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
}
