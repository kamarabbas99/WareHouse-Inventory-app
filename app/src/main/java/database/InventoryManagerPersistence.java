package database;

import android.provider.ContactsContract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import objects.IDSO;
import objects.Item;
import objects.Transaction;

public class InventoryManagerPersistence implements IDBLayer
{
    // region $fields

    private final String dbFilePath;
    private DatabaseManager dbManager;
    private TransactionPersistence transactionPersistence;
    private int lowThreshold = -1;

    // endregion

    // region $constructor

    public InventoryManagerPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
        transactionPersistence = DatabaseManager.getTransactionPersistence();
    }

    // endregions

    // region $interfaceOverrides

    /* GET
    PURPOSE:
        Retrieves the item with the given id if found in the InventoryManagers table.
        Looks for the itemID associated with the active Inventory.
    INPUT:
        id              The Item object to look for in the active inventory.
    OUTPUT:
        Returns the obtained Item from the InventoryManagers table.
        Returns a null if the item is not found.
        Throws a Persistence Exception if something went wrong with the query.
     */
    @Override
    public IDSO get(int id)
    {
        // connect to the DB
        try (final Connection connection = connect())
        {
            Item item = null;
            // prepare the query
            final PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM INVENTORYMANAGERS INNER JOIN ITEMS ON INVENTORYMANAGERS.ITEMID = ITEMS.ITEMID " +
                            "WHERE ITEMS.ITEMID = ? AND INVENTORYMANAGERS.INVENTORYID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, Integer.toString(DatabaseManager.getActiveInventory()));
            // execute the query
            final ResultSet resultSet = preparedStatement.executeQuery();
            // translate the result into the Item object if the result set found the item
            if (resultSet.next())
            {
                item = decipherResultSet(resultSet); // may throw SQLException
            }
            // close open connections
            resultSet.close();
            preparedStatement.close();
            // return the newly obtained Item
            return item;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CREATE
    PURPOSE:
        Creates a new item with the values inside the given DSO.
    NOTES:
        Does not check if an item with the given name already exists.
        This item is added to the Items database table and the InventoryManagers table.
        If an item already exists with the same ID as the provided object parameter, then a
        PersistenceException is thrown.
    INPUT:
        object              The Item object to create in the Items table.
    OUTPUT:
        Returns the itemID of the newly created item on success.
        Returns -1 if the provided parameter is not an instance of the Item class.
        Throws a PersistenceException if an item already exists with the same ID.
        Throws a PersistenceException if something went wrong with query.
     */
    @Override
    public int create(IDSO object)
    {
        // a check to verify the provided parameter is an instance of the Item class
        if (!(object instanceof Item))
        {
            return -1;
        }

        try
        {
            Item itemToCreate = (Item) object; // cast object to an Item
            int id = itemToCreate.getID();
            // first check the Items table to see if the item already exists
            ItemPersistence itemDB = DatabaseManager.getItemPersistence();
            Item foundItem = (Item) itemDB.get(object.getID()); // returns null if not found
            // case: item inside the Items table with the same id was not found
            if (foundItem == null) {
                id = itemDB.create(itemToCreate); // create an entry for the item in the Items table
                // log the creation in the Transactions Table
                transactionPersistence.create(new Transaction(DatabaseManager.getActiveAccount(), DatabaseManager.getActiveInventory(), id, "create", 0));
                lowThreshold = itemToCreate.getLowThreshold();
                add(id, itemToCreate.getQuantity()); // may throw PersistenceException
            }
            // case: item with the same id was found
            else
            {
                throw new PersistenceException(new Exception("Item with provide itemID already exists. Cannot create Item."));
            }

            // return the itemID of the newly created item
            return id;
        }
        // catch and throw the exception thrown by the create(), add(), or get() method calls.
        catch (final PersistenceException exception)
        {
            throw exception;
        }
    }

    /* DELETE
    PURPOSE:
        Deletes the entry in the InventoryManagers table.
    INPUT:
        id              The itemID to look for and delete from the InventoryManagers table.
    OUTPUT:
        Throws a PersistenceException if something went wrong with the query.
    */
    @Override
    public void delete(int id) {
        // connect to DB
        try (final Connection connection = connect())
        {
            // prepare the query
            final PreparedStatement inventoryStatement = connection.prepareStatement("DELETE FROM INVENTORYMANAGERS WHERE ITEMID = ? AND INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(id));
            inventoryStatement.setString(2, Integer.toString(DatabaseManager.getActiveInventory()));
            // execute the query
            inventoryStatement.executeUpdate();
            // close open connections
            inventoryStatement.close();
            // log the deletion in the Transactions Table
            transactionPersistence.create(new Transaction(DatabaseManager.getActiveAccount(), DatabaseManager.getActiveInventory(), id, "delete", 0));
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* GETDB
    PURPOSE:
        Retrieves all the items associated with the active inventory.
    OUTPUT:
        Returns an array of all the Items found within the active inventory.
        Returns NULL if not items were found within the active inventory.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO[] getDB() {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<IDSO> itemsList = new ArrayList<IDSO>();
            IDSO[] itemsArray = null; // the returned array
            // prepare the query
            final PreparedStatement inventoryStatement = connection.prepareStatement("SELECT * FROM INVENTORYMANAGERS INNER JOIN ITEMS ON INVENTORYMANAGERS.ITEMID=ITEMS.ITEMID " +
                    "WHERE INVENTORYMANAGERS.INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(DatabaseManager.getActiveInventory()));
            // execute the query
            final ResultSet resultSet = inventoryStatement.executeQuery();
            // cycle through the result and add the items to the list
            while(resultSet.next())
            {
                final Item item = decipherResultSet(resultSet);
                itemsList.add(item);
            }
            // close open connections
            resultSet.close();
            inventoryStatement.close();
            // convert the arraylist to an array if it has any entries
            if (itemsList.size() > 0)
            {
                itemsArray = new IDSO[itemsList.size()];
                itemsList.toArray(itemsArray);
            }
            // return the array with all the items
            return itemsArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CLEARDB
    PURPOSE:
        Completely removes all items from the active inventory.
        This only affects the InventoryManagers table.
    OUTPUT:
        Throws a SQLException if something went wrong with the query.
     */
    @Override
    public void clearDB() {
        try (final Connection connection = connect())
        {
            // prepare the query
            final PreparedStatement inventoryStatement = connection.prepareStatement("DELETE FROM INVENTORYMANAGERS WHERE INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(DatabaseManager.getActiveInventory()));
            // execute the query
            inventoryStatement.executeUpdate();
            // close open connections
            inventoryStatement.close();
            // log the deletion in the Transactions Table
            transactionPersistence.create(new Transaction(DatabaseManager.getActiveAccount(), DatabaseManager.getActiveInventory(), -1, "deleteAll", 0));
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* ADD
    PURPOSE:
        Adds the specified quantity to an item, with specified id, in the active inventory.
    NOTES:
        If the item does not exist in the Items table, then the method will throw a PersistenceException.
    INPUT:
        id              The itemID to look for in the active inventory.
        quantity        The amount to increase the quantity of the item by.
    OUTPUT:
        Returns the updated item object in the InventoryManagers table on success.
        Throws a PersistenceException if item does not exist in the Items table.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO add(int id, int quantity)
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // retrieve the item from InventoryManagers table
            Item itemToAdd = (Item) get(id); // may throw a PersistenceException
            // case A: item does not exist in the InventoryManagers table
            if (itemToAdd == null)
            {
                ItemPersistence itemDB = DatabaseManager.getItemPersistence();
                Item foundItem = (Item) itemDB.get(id);
                // case A: item exists in the Items table
                if (foundItem != null)
                {
                    final PreparedStatement inventoryStatement = connection.prepareStatement("INSERT INTO INVENTORYMANAGERS VALUES (?, ?, ?, ?)");
                    inventoryStatement.setString(1, Integer.toString(id));
                    inventoryStatement.setString(2, Integer.toString(DatabaseManager.getActiveInventory()));
                    inventoryStatement.setString(3, Integer.toString(quantity));
                    if (lowThreshold > 0)
                    {
                        inventoryStatement.setInt(4, lowThreshold);
                        lowThreshold = -1;
                    }
                    else
                    {
                        inventoryStatement.setInt(4, foundItem.getLowThreshold());
                    }
                    // execute the query
                    inventoryStatement.executeUpdate();
                    // close open connections
                    inventoryStatement.close();
                }
                // case B: item does not exist in the Items table
                else
                {
                    throw new PersistenceException(new Exception("Cannot add Item into inventory because it does not currently exist."));
                }
            }
            // case B: item already exists inside the InventoryManagers table
            else
            {
                // store previous quantity
                int previousQuantity = Integer.valueOf(itemToAdd.getQuantity());
                // set new quantity
                String newQuantity = Integer.toString(previousQuantity + quantity);
                // prepare the query
                final PreparedStatement inventoryStatement = connection.prepareStatement("UPDATE INVENTORYMANAGERS SET QUANTITY = ? WHERE ITEMID = ? AND INVENTORYID = ?");
                inventoryStatement.setString(1, newQuantity);
                inventoryStatement.setString(2, Integer.toString(id));
                inventoryStatement.setString(3, Integer.toString(DatabaseManager.getActiveInventory()));
                // execute the query
                inventoryStatement.executeUpdate();
                // close open connections
                inventoryStatement.close();
            }

            itemToAdd = (Item) get(id); // retrieve update Item entry
            // log the addition in the Transactions Table
            transactionPersistence.create(new Transaction(DatabaseManager.getActiveAccount(), DatabaseManager.getActiveInventory(), id, "add", quantity));
            // return updated item
            return itemToAdd;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
        // catch and throw the exception thrown by the get() method call.
        catch (final PersistenceException exception)
        {
            throw exception;
        }
    }

    /* REMOVE
    PURPOSE:
        Removes the specified quantity from the item with the given itemID inside the active inventory.
    NOTES:
        If the item does not exist, the method will throw a PersistenceException.
        If the specified quantity is greater than or equal to the current quantity, then the method will
        remove that item from the DB and return null.
    INPUT:
        id              The itemID to look for in the active inventory.
        quantity        The amount to decrease the quantity of the item by.
    OUTPUT:
        Returns the updated item object in the InventoryManagers table.
        Returns null if the new item quantity is 0 or lower.
        Throws a PersistenceException if the item was not found in the active inventory.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO remove(int id, int quantity) {
        // connect to DB
        try (final Connection connection = connect())
        {

            // retrieve the Item first to get quantity
            Item itemToRemove = (Item) get(id); // may throw a PersistenceException
            // case A: Item does not exist in the InventoryManagers table
            if (itemToRemove == null)
            {
                throw new PersistenceException(new Exception("Cannot remove Item from inventory because it does not currently exist."));
            }
            else
            {
                // store previous quantity
                int previousQuantity = itemToRemove.getQuantity();

                // a check to see if the removal will delete the object
                // case: removal does delete the item
                if (previousQuantity - quantity == 0)
                {
                    delete(id); // may throw a PersistenceException
                    itemToRemove = null;
                    // log the removal in the Transactions Table
                    transactionPersistence.create(new Transaction(DatabaseManager.getActiveAccount(), DatabaseManager.getActiveInventory(), id, "remove", quantity));
                }
                else if (previousQuantity - quantity == 0)
                {
                    throw new PersistenceException(new Exception("The amount specified to remove was too large."));
                }
                // case: removal does not delete the item
                else
                {
                    String newQuantity = Integer.toString(previousQuantity - quantity);
                    // prepare the query
                    final PreparedStatement inventoryStatement = connection.prepareStatement("UPDATE INVENTORYMANAGERS SET QUANTITY = ? WHERE ITEMID = ? AND INVENTORYID = ?");
                    inventoryStatement.setString(1, newQuantity);
                    inventoryStatement.setString(2, Integer.toString(id));
                    inventoryStatement.setString(3, Integer.toString(DatabaseManager.getActiveInventory()));
                    // execute the query
                    inventoryStatement.executeUpdate();
                    // close open connections
                    inventoryStatement.close();
                    // update old reference to item
                    itemToRemove = (Item) get(id);
                    // log the removal in the Transactions Table
                    transactionPersistence.create(new Transaction(DatabaseManager.getActiveAccount(), DatabaseManager.getActiveInventory(), id, "remove", quantity));
                }
            }
            // return updated item from DB
            return itemToRemove;
        }
        catch (SQLException exception)
        {
            throw new PersistenceException(exception);
        }
        // catch and throw the exception thrown by the get() or delete() method call.
        catch (final PersistenceException exception)
        {
            throw exception;
        }
    }

    // endregion

    // region $utility

    /* CONNECT
    PURPOSE:
        Attempts to get a connection to the Database .script file found in dbFilePath.
    OUTPUT:
        Returns the newly established connection to the DB.
        Throws an SQLException is the connection was a failure.
     */
    private Connection connect() throws SQLException
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbFilePath + ";shutdown=true", "SA", "");
    }

    /* DECIPHERRESULTSET
    PURPOSE:
        Reads the ResultSet and translates it into an Item object.
    INPUT:
        resultSet               The ResultSet to get values from.
    OUTPUT:
        Returns an Item object with values retrieved from the DB.
        Throws an exception if the ResultSet does not have one of the named columns.
     */
    private Item decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        String itemID = resultSet.getString("itemID");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String quantity = resultSet.getString("quantity");
        String quantityMetric = resultSet.getString("quantityMetric");
        String lowThreshold = resultSet.getString("lowThreshold");
        return new Item(Integer.parseInt(itemID), name, description, Integer.parseInt(quantity), quantityMetric, Integer.parseInt(lowThreshold));
    }

    // endregion

}
