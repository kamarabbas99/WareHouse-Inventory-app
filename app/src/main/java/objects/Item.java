package objects;

/*
This class contains information related to items that are stored in the database.
It is a Domain Specific Object that will be passed through the layers of our system.

All manipulation of this classes fields are done through it's constructor.
i.e. If you want to change the quantity of an Item, you must create a new Item.
 */
public class Item implements IDSO {
    // region $fields
    private int id;
    private String name;
    private String description;
    private int quantity;
    private String quantityMetric;
    private int lowThreshold;

    // endregion

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

    public Item(String name, String description, int quantity, String quantityMetric, int lowThreshold){
        id = -1;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.quantityMetric = quantityMetric;
        this.lowThreshold = lowThreshold;
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

    // region $getter

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
