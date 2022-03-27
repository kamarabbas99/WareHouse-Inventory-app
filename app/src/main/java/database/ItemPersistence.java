package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.*;

/* ITEMPERSISTENCE
The intent of this class is to interact with Items table.
Modifies the above table with the public methods.
 */
public class ItemPersistence implements IDBLayer{

    // region $fields

    private final String dbFilePath;
    private DatabaseManager dbManager;

    // endregion

    // region $constructor

    public ItemPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
    }

    // endregions

    // region $interfaceOverrides

    /* GET
    PURPOSE:
        Retrieves the item with the given id if found in the Items table.
    INPUT:
        id              The Item object to look for in the Items table.
    OUTPUT:
        Returns the obtained Item from the Items table.
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
                    ("SELECT * FROM ITEMS WHERE ITEMID = ?");
            preparedStatement.setString(1, Integer.toString(id));
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
        This item is added to the Items table.
        If an item already exists with the same ID as the provided object parameter, then that ID is returned.
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

                // TODO: update transactions table (not currently time sensitive)
            }
            // case: item with the same id was found (do nothing)

            // return the itemID of the newly created item
            return id;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
        // catch and throw the exception thrown by the add method call to update the Transactions table.
        catch (final PersistenceException exception)
        {
            throw exception;
        }
    }

    /* DELETE
    PURPOSE:
        (Theoretically) Deletes the entry in the Items table.
    NOTES:
        Not implemented to prevent the deletion of transaction history.
    INPUT:
        id              The itemID to look for and delete from the InventoryManagers table.
    OUTPUT:
        Throws a PersistenceException if something went wrong with the query.
    */
    @Override
    public void delete(int id) throws PersistenceException
    {
        Exception exception = new Exception("Cannot delete an Item from the Items table.");
        throw new PersistenceException(exception);
    }

    /* GETDB
    PURPOSE:
        Retrieves all the items in the Items table.
    OUTPUT:
        Returns an array of all the Items found within the Items table.
        Returns NULL if not items were found.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO[] getDB()
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<IDSO> itemsList = new ArrayList<IDSO>();
            IDSO[] itemsArray = null; // the returned array
            // prepare the query
            final Statement inventoryStatement = connection.createStatement();
            // execute the query
            final ResultSet resultSet = inventoryStatement.executeQuery("SELECT * FROM ITEMS");
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
        (Theoretically) Completely removes all items from the Items table.
    NOTES:
        Not implemented to prevent the deletion of transaction history.
    OUTPUT:
        Throws a SQLException if something went wrong with the query.
     */
    @Override
    public void clearDB() throws PersistenceException
    {
        Exception exception = new Exception("Cannot delete the entire Items table.");
        throw new PersistenceException(exception);
    }

    /* ADD
    PURPOSE:
        Does not make sense for the Items table.
    NOTES:
        This method is not implemented since you the Items table does not have a quantity column.
    INPUT:
        id              The itemID to look for.
        quantity        The amount to increase the quantity of the item by.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public IDSO add(int id, int quantity) throws PersistenceException
    {
        Exception exception = new Exception("Cannot add a quantity to the Items table since it does not have a quantity column.");
        throw new PersistenceException(exception);
    }

    /* REMOVE
    PURPOSE:
        Does not make sense for the Items table.
    NOTES:
        This method is not implemented since you the Items table does not have a quantity column.
    INPUT:
        id              The itemID to look for in the active inventory.
        quantity        The amount to decrease the quantity of the item by.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public IDSO remove(int id, int quantity) throws PersistenceException
    {
        Exception exception = new Exception("Cannot remove a quantity to the Items table since it does not have a quantity column.");
        throw new PersistenceException(exception);
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
        Returns an Item object with values retrieved from the Items table.
        Throws an exception if the ResultSet does not have one of the named columns.
     */
    private Item decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        String itemID = resultSet.getString("itemID");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String quantityMetric = resultSet.getString("quantityMetric");
        return new Item(Integer.parseInt(itemID), name, description, 0, quantityMetric, 0);
    }

    /* CREATENEWID
    PURPOSE:
        Retrieves the largest item id found in the Items table and increments it by one.
    OUTPUT:
        Returns a new itemID that is unique.
        Throws a SQLException if something went wrong with the query.
     */
    private int createNewID () throws SQLException
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            int id = -1;
            // prepare the query
            final Statement statement = connection.createStatement();
            // execute the query
            final ResultSet resultSet = statement.executeQuery("SELECT MAX(ITEMID) AS MAXID FROM ITEMS");
            // translate the query result into an integer
            if (resultSet.next())
            {
                id = Integer.valueOf(resultSet.getString("maxID"));
            }
            // close open connections
            resultSet.close();
            statement.close();
            // return the new incremented id
            return id + 1;
        }
    }

    // endregion

}
