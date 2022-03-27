package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.IDSO;
import objects.Item;

public class InventoryManagerPersistence implements IDBLayer
{
    // region $fields

    private final String dbFilePath;
    private DatabaseManager dbManager;

    // endregion

    // region $constructor

    public InventoryManagerPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
    }

    // endregions

    // region $interfaceOverrides

    /* GET
    PURPOSE:
        Retrieves the item with the given id if found in the database.
        Looks for the itemID in the active Inventory.
    INPUT:
        id              The Item object to look for in the active inventory.
    OUTPUT:
        Returns the obtained Item from the DB.
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
                    ("SELECT * FROM INVENTORYMANAGERS INNER JOIN ITEMS ON INVENTORYMANAGERS.ITEMID=ITEMS.ITEMID " +
                            "WHERE ITEMS.ITEMID = ? AND INVENTORYMANAGERS.INVENTORYID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, Integer.toString(dbManager.getActiveInventory()));
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
        If an item already exists with the same ID as the provided object parameter, then that ID is returned
        and the quantity is updated.
    INPUT:
        object              The Item object to create in the Items table.
    OUTPUT:
        Returns the itemID of the newly created item.
        Returns -1 if the provided parameter is not an instance of the Item class.
        Throws a PersistenceException if something went wrong with query.
     */
    @Override
    public int create(IDSO object)
    {
        Item itemToCreate;

        // a check to verify the provided parameter is an instance of the Item class
        if (object instanceof Item)
        {
            itemToCreate = (Item) object;
        }
        else
        {
            return -1;
        }

        // connect to the DB
        try (final Connection connection = connect())
        {
            int id = itemToCreate.getID(); // the returned value
            Item foundItem;
            // a check to see if an item with the given ID already exists
            // case: item with the same id wasn't found
            if ((foundItem = (Item) get(itemToCreate.getID())) == null) {
                // retrieve a new ID to give to the item.
                id = createNewID();
                // prepare the query
                final PreparedStatement itemStatement = connection.prepareStatement("INSERT INTO ITEMS VALUES (?, ?, ?, ?)");
                // fill out the query variables
                itemStatement.setString(1, Integer.toString(id));
                itemStatement.setString(2, itemToCreate.getName());
                itemStatement.setString(3, itemToCreate.getDescription());
                itemStatement.setString(4, itemToCreate.getQuantityMetric());
                // execute the query
                itemStatement.executeUpdate();
                // close open connections
                itemStatement.close();

                // add the item to the InventoryManagers table
                add(id, itemToCreate.getQuantity()); // may throw PersistenceException

                // TODO: update transaction table (not currently time sensitive)
            }
            // case: item with the same id was found
            else if (foundItem != null)
            {
                add(foundItem.getID(), itemToCreate.getQuantity()); // may throw PersistenceException
            }

            // return the itemID of the newly created item
            return id;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
        // catch and throw the exception thrown by the get() and/or add() method call.
        catch (final PersistenceException exception)
        {
            throw exception;
        }
    }

    /* DELETE
    PURPOSE:
        Deletes the entry in the InventoryManagers table and NOT the Items table.
        This is to prevent the deletion of transaction history.
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
            inventoryStatement.setString(2, Integer.toString(dbManager.getActiveInventory()));
            // execute the query
            inventoryStatement.executeUpdate();
            // close open connections
            inventoryStatement.close();

            // TODO: update transaction table (not currently time sensitive)
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
            inventoryStatement.setString(1, Integer.toString(dbManager.getActiveInventory()));
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
            inventoryStatement.setString(1, Integer.toString(dbManager.getActiveInventory()));
            // execute the query
            inventoryStatement.executeUpdate();
            // close open connections
            inventoryStatement.close();

            // TODO: update transaction table (not currently time sensitive)
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
        If the item does not exist the method will throw a PersistenceException.
    INPUT:
        id              The itemID to look for in the active inventory.
        quantity        The amount to increase the quantity of the item by.
    OUTPUT:
        Returns the updated item object in the DB.
        Returns a null if the item object was not found in the DB.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO add(int id, int quantity)
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // retrieve the item from DB
            Item itemToAdd = (Item) get(id); // may throw a PersistenceException
            if (itemToAdd != null) {
                // store previous quantity
                int previousQuantity = Integer.valueOf(itemToAdd.getQuantity());
                // set new quantity
                String newQuantity = Integer.toString(previousQuantity + quantity);
                // prepare the query
                final PreparedStatement inventoryStatement = connection.prepareStatement("UPDATE INVENTORYMANAGERS SET QUANTITY = ? WHERE ITEMID = ? AND INVENTORYID = ?");
                inventoryStatement.setString(1, newQuantity);
                inventoryStatement.setString(2, Integer.toString(id));
                inventoryStatement.setString(3, Integer.toString(dbManager.getActiveInventory()));
                // execute the query
                inventoryStatement.executeUpdate();
                // close open connections
                inventoryStatement.close();
                // update old reference to item
                itemToAdd = (Item) get(id);
            }
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
        Returns the updated item object in the DB.
        Returns null if the new item quantity is 0 or lower.
        Returns null if the item was not found.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO remove(int id, int quantity) {
        // connect to DB
        try (final Connection connection = connect())
        {
            // retrieve the Item first to get quantity
            Item itemToRemove = (Item) get(id); // may throw a PersistenceException
            if (itemToRemove != null) {
                // store previous quantity
                int previousQuantity = Integer.valueOf(itemToRemove.getQuantity());

                // a check to see if the removal will delete the object
                // case: removal does delete the item
                if (previousQuantity - quantity == 0)
                {
                    delete(id); // may throw a PersistenceException
                    itemToRemove = null;
                }
                else if (previousQuantity - quantity == 0)
                {
                    throw new PersistenceException(new Exception("The amount specified to remove was too large."));
                }
                // case: removal does not delete the item
                else {
                    String newQuantity = Integer.toString(previousQuantity - quantity);
                    // prepare the query
                    final PreparedStatement inventoryStatement = connection.prepareStatement("UPDATE INVENTORYMANAGERS SET QUANTITY = ? WHERE ITEMID = ? AND INVENTORYID = ?");
                    inventoryStatement.setString(1, newQuantity);
                    inventoryStatement.setString(2, Integer.toString(id));
                    inventoryStatement.setString(3, Integer.toString(dbManager.getActiveInventory()));
                    // execute the query
                    inventoryStatement.executeUpdate();
                    // close open connections
                    inventoryStatement.close();
                    // update old reference to item
                    itemToRemove = (Item) get(id);
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
        return new Item(Integer.valueOf(itemID), name, description, Integer.valueOf(quantity), quantityMetric, Integer.valueOf(lowThreshold));
    }

    /* CREATENEWID
    PURPOSE:
        Retrieves the largest item id and increments it by one.
    OUTPUT:
        Returns a new itemID that is unique.
        Throws a SQLException if something went wrong with the query.
     */
    private int createNewID () throws SQLException
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            int id;
            // prepare the query
            final Statement statement = connection.createStatement();
            // execute the query
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE ITEMID = (SELECT MAX(ITEMID) FROM ITEMS)");
            // translate the query result into an integer
            id = Integer.valueOf(resultSet.getString("itemID"));
            // close open connections
            resultSet.close();
            statement.close();
            // return the new incremented id
            return id + 1;
        }
    }

    // endregion

}
