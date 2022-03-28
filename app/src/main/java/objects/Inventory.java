package objects;

import java.util.Date;

public class Inventory implements IDSO {

    private int id;
    private String name;
    private String company;
    private Date dateCreated;

    // endregion

    // region $constructors

    public Inventory(){
        id = -1;
        name = "default";
        company = "default";
        dateCreated = new Date();
    }

    public Inventory(int id, String name, String company) throws NullPointerException {
        this.id = id;
        if (name == null)
        {
            throw new NullPointerException("Cannot create an account without a username.");
        }
        else
        {
            this.name = name;
        }
        this.company = (company == null) ? "default" : company;
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

    public String getCompany(){
        return company;
    }
    // endregion

    // region $verification



    public boolean equals(Inventory otherInventory){
        return otherInventory.getID() == this.id;
    }

}
