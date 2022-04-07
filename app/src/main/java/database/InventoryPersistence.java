
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.*;

public class InventoryPersistence implements IDBLayer{

    private final String dbFilePath;
    private DatabaseManager dbManager;

    // endregion

    // region $constructor

    public InventoryPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
    }

    @Override
    public IDSO get(int id)
    {
        // connect to the DB
        try (final Connection connection = connect())
        {
            Inventory inv = null;
            // prepare the query
            final PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM INVENTORIES WHERE INVENTORYID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            // execute the query
            final ResultSet resultSet = preparedStatement.executeQuery();
            // translate the result into the Inventory object if the result set found the Inventory
            if (resultSet.next())
            {
                inv = decipherResultSet(resultSet); // may throw SQLException
            }
            // close open connections
            resultSet.close();
            preparedStatement.close();
            // return the newly obtained Inventory
            return inv;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CREATE
    PURPOSE:
        Creates a new Inventory with the values inside the given DSO.

    INPUT:
        The Inventory object to create in the account table.
    OUTPUT:
        Returns the INVENTORYID of the newly created Inventory.
        Returns -1 if the provided parameter is not an instance of the Inventory class.\
        Throws a PersistenceException if something went wrong with query.
     */
    @Override
    public int create(IDSO object)
    {
        Inventory invToCreate;

        // a check to verify the provided parameter is an instance of the Inventory class
        if (object instanceof Inventory)
        {
            invToCreate = (Inventory) object;
        }
        else
        {
            return -1;
        }

        // connect to the DB
        try (final Connection connection = connect())
        {
            int id = invToCreate.getID(); // the returned value
            // a check to see if an Inventory with the given ID already exists
            // case: Inventory with the same id wasn't found
            if (((Inventory) get(invToCreate.getID())) == null) {
                // retrieve a new ID to give to the Inventory.
                id = createNewID();
                // prepare the query
                final PreparedStatement invStatement = connection.prepareStatement("INSERT INTO INVENTORIES VALUES (?, ?, ?, ?)");
                // fill out the query variables
                invStatement.setString(1, Integer.toString(id));
                invStatement.setString(2, invToCreate.getName());
                invStatement.setString(4, invToCreate.getCompany());
                invStatement.setString(5, String.valueOf(invToCreate.getDateCreated()));
                // execute the query
                invStatement.executeUpdate();
                // close open connections
                invStatement.close();

            }

            // return the INVENTORYID of the newly created Inventory
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
        Deletes the entry in the Inventories table.

    INPUT:
        The INVENTORYID to look for and delete from the INVENTORIES table.
    OUTPUT:
        Throws a PersistenceException if something went wrong with the query.
    */
    @Override
    public void delete(int id) {
        // connect to DB
        try (final Connection connection = connect())
        {
            // prepare the query
            final PreparedStatement inventoryStatement = connection.prepareStatement("DELETE FROM INVENTORIES WHERE INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(id) );
            // execute the query
            inventoryStatement.executeUpdate();
            // close open connections
            inventoryStatement.close();

        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    @Override
    public IDSO add(int id, int quantity)
    {return null;
    }

    @Override
    public IDSO remove(int id, int quantity) {
        return null;
    }

    /* GETDB
    PURPOSE:
        Retrieves all the inventories.
    OUTPUT:
        Returns an array of all the inventories.
        Returns NULL if no inventories were found.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO[] getDB() {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<IDSO> invList = new ArrayList<IDSO>();
            IDSO[] invArray = null; // the returned array
            // prepare the query
            final PreparedStatement inventoryStatement = connection.prepareStatement("SELECT * FROM INVENTORIES");
            // execute the query
            final ResultSet resultSet = inventoryStatement.executeQuery();
            // cycle through the result and add the inventories to the list
            while(resultSet.next())
            {
                final Inventory inv = decipherResultSet(resultSet);
                invList.add(inv);
            }
            // close open connections
            resultSet.close();
            inventoryStatement.close();
            // convert the arraylist to an array if it has any entries
            if (invList.size() > 0)
            {
                invArray = new IDSO[invList.size()];
                invList.toArray(invArray);
            }
            // return the array with all the inventories
            return invArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CLEARDB
    PURPOSE:
        Completely removes all INVENTORIES.
        This only affects the INVENTORIES table.
    OUTPUT:
        Throws a SQLException if something went wrong with the query.
     */
    @Override
    public void clearDB() {
        try (final Connection connection = connect())
        {
            // prepare the query
            final PreparedStatement invStatement = connection.prepareStatement("DELETE FROM INVENTORIES WHERE 1=1");
            // execute the query
            invStatement.executeUpdate();
            // close open connections
            invStatement.close();

        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    // Unsure about this methods' purpose (mcquarrc)
    public boolean verifyID(int id) {
        return false;
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
        Reads the ResultSet and translates it into an Inventory object.
    INPUT:
        resultSet The ResultSet to get values from.
    OUTPUT:
        Returns an Inventory object with values retrieved from the DB.
        Throws an exception if the ResultSet does not have one of the named columns.
     */
    private Inventory decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        String invID = resultSet.getString("INVENTORYID");
        String name = resultSet.getString("NAME");
        String company = resultSet.getString("COMPANY");
        return new Inventory(Integer.valueOf(invID), name, company);
    }

    /* CREATENEWID
    PURPOSE:
        Retrieves the largest Inventory id and increments it by one.
    OUTPUT:
        Returns a new Inventory that is unique.
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
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM INVENTORIES WHERE INVENTORYID = (SELECT MAX(INVENTORYID) FROM INVENTORIES)");
            // translate the query result into an integer
            id = Integer.valueOf(resultSet.getString("INVENTORYID"));
            // close open connections
            resultSet.close();
            statement.close();
            // return the new incremented id
            return id + 1;
        }
    }


}

