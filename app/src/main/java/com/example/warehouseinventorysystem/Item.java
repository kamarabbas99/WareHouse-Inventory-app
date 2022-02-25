package com.example.warehouseinventorysystem;

public class Item implements IDSO {
    // region
    private int id;
    private String name;
    private String description;
    private int quantity;
    private String quantityMetric;
    private int lowThreshold;

    // region $constructors

    // null constructor that sets default values
    public Item(){
        id = -1;
        name = null;
        description = null;
        quantity = -1;
        quantityMetric = null;
        lowThreshold = -1;
    }

    public Item(int id, String name, String description, int quantity, String quantityMetric, int lowThreshold){
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.quantityMetric = quantityMetric;
        this.lowThreshold = lowThreshold;
    }

    // endregion

    // region $interfaceMethods

    @Override
    public int GetID() {
        return id;
    }

    // endregion

    // region $gettersAndSetters

    public String GetName(){
        return name;
    }

    public String GetDescription(){
        return description;
    }

    public int GetQuantity(){
        return quantity;
    }

    public String GetQuantityMetric(){
        return quantityMetric;
    }

    public int GetLowThreshold(){
        return lowThreshold;
    }

    // endregion

    // region $publicMethods

    public boolean IsEmpty(){
        return quantity < 1;
    }

    public boolean IsLow(){
        return quantity <= lowThreshold;
    }

    // endregion
}
