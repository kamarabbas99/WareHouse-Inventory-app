package objects;

import java.util.Date;

public class Inventory implements IDSO {

    private int id;
    private String name;
    private Date dateCreated;

    // endregion

    // region $constructors

    public Inventory(){
        id = -1;
        name = "default";
        dateCreated = new Date();
    }

    public Inventory(int id, String name) throws NullPointerException {
        this.id = id;
        if (name == null)
        {
            throw new NullPointerException("Cannot create an account without a username.");
        }
        else
        {
            this.name = name;
        }
        dateCreated = new Date();
    }

    // endregion

    // region $interfaceMethods

    @Override
    public int getID(){
        return id;
    }

    // endregion

    // region $getters

    public String getName(){
        return name;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    // endregion

    // region $verification



    public boolean equals(Inventory otherInventory){
        return otherInventory.getID() == this.id;
    }

}
