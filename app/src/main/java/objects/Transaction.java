package objects;

import java.util.Date;
import java.sql.Timestamp;
import java.util.*;

public class Transaction implements IDSO
{
    // region $fields

    private int transactionID;
    private int accountID;
    private int inventoryID;
    private int itemID;
    private String transactionType;
    private int quantity;
    private Timestamp dateCreated;

    // endregion

    // region $constructors

    public Transaction()
    {
        transactionID = -1;
        accountID = -1;
        inventoryID = -1;
        itemID = -1;
        quantity = -1;
        transactionType = "default";
        dateCreated = new Timestamp(new Date().getTime());
    }

    public Transaction(int accountID, int inventoryID, int itemID, String transactionType, int quantity) throws NullPointerException
    {
        this.transactionID = -1;
        this.accountID = accountID;
        this.inventoryID = inventoryID;
        this.itemID = itemID;
        if (transactionType == null)
        {
            throw new NullPointerException("Cannot create a transaction without a type.");
        }
        else
        {
            this.transactionType = transactionType;
        }
        this.quantity = quantity;
        this.dateCreated = new Timestamp(new Date().getTime());
    }

    public Transaction(int transactionID, int accountID, int inventoryID, int itemID, String transactionType, int quantity, Timestamp dateCreated) throws NullPointerException
    {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.inventoryID = inventoryID;
        this.itemID = itemID;
        if (transactionType == null)
        {
            throw new NullPointerException("Cannot create a transaction without a type.");
        }
        else
        {
            this.transactionType = transactionType;
        }
        this.quantity = quantity;
        this.dateCreated = dateCreated;
    }

    // endregion

    // region $interface

    @Override
    public int getID() {
        return transactionID;
    }

    // endregion

    // region $getters

    public int getTransactionID() {
        return transactionID;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public int getItemID() {
        return itemID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getQuantity() {
        return quantity;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    // endregion

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("transactionID:");
        stringBuilder.append(transactionID);
        stringBuilder.append(",accountID:");
        stringBuilder.append(accountID);
        stringBuilder.append(",inventoryID:");
        stringBuilder.append(inventoryID);
        stringBuilder.append(",itemID:");
        stringBuilder.append(itemID);
        stringBuilder.append(",transactionType:");
        stringBuilder.append(transactionType);
        stringBuilder.append(",quantity:");
        stringBuilder.append(quantity);
        stringBuilder.append(",dateCreated:");
        stringBuilder.append(dateCreated);

        return stringBuilder.toString();
    }
}
