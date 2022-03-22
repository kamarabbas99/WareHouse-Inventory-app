package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.*;

public class AccountPersistance implements IDBLayer{

    private final String dbFilePath;
    private DatabaseManager dbManager;

    // endregion

    // region $constructor

    public AccountPersistance(final String dbFilePath)
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
            Account acc = null;
            // prepare the query
            final PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            // execute the query
            final ResultSet resultSet = preparedStatement.executeQuery();
            // translate the result into the Item object if the result set found the item
            if (resultSet.next())
            {
                acc = decipherResultSet(resultSet); // may throw SQLException
            }
            // close open connections
            resultSet.close();
            preparedStatement.close();
            // return the newly obtained Item
            return acc;
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
        and the quantity is NOT updated.
    INPUT:
        object              The Item object to create in the Items table.
    OUTPUT:
        Returns the itemID of the newly created item.
        Returns -1 if the provided parameter is not an instance of the Item class.\
        Throws a PersistenceException if something went wrong with query.
     */
    @Override
    public int create(IDSO object)
    {
        Account accToCreate;

        // a check to verify the provided parameter is an instance of the Item class
        if (object instanceof Account)
        {
            accToCreate = (Account) object;
        }
        else
        {
            return -1;
        }

        // connect to the DB
        try (final Connection connection = connect())
        {
            int id = accToCreate.getID(); // the returned value
            // a check to see if an item with the given ID already exists
            // case: item with the same id wasn't found
            if (((Account) get(accToCreate.getID())) == null) {
                // retrieve a new ID to give to the item.
                id = createNewID();
                // prepare the query
                final PreparedStatement itemStatement = connection.prepareStatement("INSERT INTO ACCOUNTS VALUES (?, ?, ?, ?, ?)");
                // fill out the query variables
                itemStatement.setString(1, Integer.toString(id));
                itemStatement.setString(2, accToCreate.getUsername());
                itemStatement.setString(3, "123456");
                itemStatement.setString(4, accToCreate.getCompany());
                itemStatement.setString(5, String.valueOf(accToCreate.getDateCreated()));
                // execute the query
                itemStatement.executeUpdate();
                // close open connections
                itemStatement.close();

            }

            // return the ACCOUNTID of the newly created item
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
            final PreparedStatement inventoryStatement = connection.prepareStatement("DELETE FROM ACCOUNTS WHERE ACCOUNTID = ?");
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
        Retrieves all the accounts.
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
            ArrayList<IDSO> accountsList = new ArrayList<IDSO>();
            IDSO[] accountsArray = null; // the returned array
            // prepare the query
            final PreparedStatement inventoryStatement = connection.prepareStatement("SELECT * FROM ACCOUNTS");
            // execute the query
            final ResultSet resultSet = inventoryStatement.executeQuery();
            // cycle through the result and add the items to the list
            while(resultSet.next())
            {
                final Account item = decipherResultSet(resultSet);
                accountsList.add(item);
            }
            // close open connections
            resultSet.close();
            inventoryStatement.close();
            // convert the arraylist to an array if it has any entries
            if (accountsList.size() > 0)
            {
                accountsArray = new IDSO[accountsList.size()];
                accountsList.toArray(accountsArray);
            }
            // return the array with all the items
            return accountsArray;
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
            final PreparedStatement accountStatement = connection.prepareStatement("DELETE FROM ACCOUNTS WHERE 1=1");
            accountStatement.setString(1, Integer.toString(dbManager.getActiveInventory()));
            // execute the query
            accountStatement.executeUpdate();
            // close open connections
            accountStatement.close();

        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    // Unsure about this methods' purpose (mcquarrc)
    @Override
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
        Reads the ResultSet and translates it into an Item object.
    INPUT:
        resultSet               The ResultSet to get values from.
    OUTPUT:
        Returns an Item object with values retrieved from the DB.
        Throws an exception if the ResultSet does not have one of the named columns.
     */
    private Account decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        String accountID = resultSet.getString("ACCOUNTID");
        String username = resultSet.getString("USERNAME");
        String pass = resultSet.getString("ACCOUNTPASSWORD");
        String company = resultSet.getString("COMPANY");
        return new Account(Integer.valueOf(accountID), username, pass, company);
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
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = (SELECT MAX(ACCOUNTID) FROM ACCOUNTS)");
            // translate the query result into an integer
            id = Integer.valueOf(resultSet.getString("ACCOUNTID"));
            // close open connections
            resultSet.close();
            statement.close();
            // return the new incremented id
            return id + 1;
        }
    }


}
