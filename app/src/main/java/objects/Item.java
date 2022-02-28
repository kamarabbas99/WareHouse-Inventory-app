package objects;

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
    public int getID() {
        return id;
    }

    // endregion

    // region $gettersAndSetters

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getQuantityMetric(){
        return quantityMetric;
    }

    public int getLowThreshold(){
        return lowThreshold;
    }

    // endregion

    // region $publicMethods

    public boolean isEmpty(){
        return quantity < 1;
    }

    public boolean isLow(){
        return quantity <= lowThreshold;
    }

    // endregion
}
