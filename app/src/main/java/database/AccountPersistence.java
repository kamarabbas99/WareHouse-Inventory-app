package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;

import objects.*;

public class AccountPersistence implements IDBLayer{

    private final String dbFilePath;
    private DatabaseManager dbManager;
    private TransactionPersistence transactionPersistence;

    // endregion

    // region $constructor

    public AccountPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
        transactionPersistence = DatabaseManager.getTransactionPersistence();
    }


    @Override
    public IDSO get(int id)
    {
        // connect to the DB
        try (final Connection connection = connect())
        {
            Account acc = null;
            // prepare the query
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNTID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            // execute the query
            final ResultSet resultSet = preparedStatement.executeQuery();
            // translate the result into the account object if the result set found the account
            if (resultSet.next())
            {
                acc = decipherResultSet(resultSet); // may throw SQLException
            }
            // close open connections
            resultSet.close();
            preparedStatement.close();
            // return the newly obtained account
            return acc;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CREATE
    PURPOSE:
        Creates a new account with the values inside the given DSO.
    INPUT:
        The account object to create in the account table.
    OUTPUT:
        Returns the ACCOUNTID of the newly created account.
        Returns -1 if the provided parameter is not an instance of the Account class.
        Returns -2 if the account already exists.
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
            Account foundAccount;
            // a check to see if an account with the given ID or username already exists
            if ((foundAccount = (Account) get(id)) == null)
            {
                foundAccount = (Account) get(accToCreate.getUsername());
            }

            // case A: account with the same id or username wasn't found
            if (foundAccount == null) {
                // retrieve a new ID to give to the account.
                id = createNewID();
                // prepare the query
                final PreparedStatement accStatement = connection.prepareStatement("INSERT INTO ACCOUNTS VALUES (?, ?, ?, ?, ?)");
                // fill out the query variables
                accStatement.setString(1, Integer.toString(id));
                accStatement.setString(2, accToCreate.getUsername());
                accStatement.setString(3, accToCreate.getPassword());
                accStatement.setString(4, Integer.toString(accToCreate.getPrivilege()));
                accStatement.setString(5,accToCreate.getDateCreated().toString());
                // execute the query
                accStatement.executeUpdate();
                // close open connections
                accStatement.close();
                // log the creation in the Transactions Table
                transactionPersistence.create(new Transaction(id, -1, -1, "create", 0));
            }
            // case B: account already exists
            else
            {
                id = -2;
            }

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
        (Theoretically) Deletes the entry in the Accounts table.
    NOTES:
        Not implemented to prevent the deletion of transaction history.
    INPUT:
        id              The accountID to look for and delete from the Accounts table.
    OUTPUT:
        Throws a PersistenceException if something went wrong with the query.
    */
    @Override
    public void delete(int id) {
        try(final Connection connection = connect())
        {
            // prepare the query
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM ACCOUNTS WHERE ACCOUNTID = ?");
            statement.setInt(1, id);
            // execute the query
            statement.executeUpdate();
            // close open connections
            statement.close();
            // log the deletion in the Transactions Table
            transactionPersistence.create(new Transaction(id, -1, -1, "delete", 0));
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* ADD
    PURPOSE:
        Does not make sense for the Accounts table.
    NOTES:
        This method is not implemented since you the Accounts table does not have a quantity column.
    INPUT:
        id              The accountID to look for.
        quantity        The amount to increase the quantity of the account by.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public IDSO add(int id, int quantity)
    {
        Exception exception = new Exception("Cannot add a quantity to the Accounts table since it does not have a quantity column.");
        throw new PersistenceException(exception);
    }

    /* REMOVE
    PURPOSE:
        Does not make sense for the Accounts table.
    NOTES:
        This method is not implemented since you the Accounts table does not have a quantity column.
    INPUT:
        id              The accountID to look for in the active inventory.
        quantity        The amount to decrease the quantity of the account by.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public IDSO remove(int id, int quantity)
    {
        Exception exception = new Exception("Cannot remove a quantity to the Accounts table since it does not have a quantity column.");
        throw new PersistenceException(exception);
    }

    /* GETDB
    PURPOSE:
        Retrieves all the accounts.
    OUTPUT:
        Returns an array of all the accounts.
        Returns NULL if no accounts were found.
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
            // cycle through the result and add the accounts to the list
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
            // return the array with all the accounts
            return accountsArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CLEARDB
    PURPOSE:
        (Theoretically) Completely removes all accounts from the Accounts table.
    NOTES:
        Not implemented to prevent the deletion of transaction history.
    OUTPUT:
        Throws a SQLException if something went wrong with the query.
     */
    @Override
    public void clearDB() throws PersistenceException
    {
        Exception exception = new Exception("Cannot delete the entire Accounts table.");
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
        Reads the ResultSet and translates it into an account object.
    INPUT:
        resultSet               The ResultSet to get values from.
    OUTPUT:
        Returns an account object with values retrieved from the DB.
        Throws an exception if the ResultSet does not have one of the named columns.
     */
    private Account decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        String accountID = resultSet.getString("ACCOUNTID");
        String username = resultSet.getString("USERNAME");
        String pass = resultSet.getString("ACCOUNTPASSWORD");
        String privilege = resultSet.getString("PRIVILEGE");
        Timestamp dateCreated = resultSet.getTimestamp("DATECREATED");
        return new Account(Integer.parseInt(accountID), username, pass, Integer.parseInt(privilege), dateCreated);
    }

    /* CREATENEWID
    PURPOSE:
        Retrieves the largest account id and increments it by one.
    OUTPUT:
        Returns a new ACCOUNT that is unique.
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
            final ResultSet resultSet = statement.executeQuery("SELECT MAX(ACCOUNTID) AS MAXID FROM ACCOUNTS");
            // translate the query result into an integer
            if (resultSet.next())
            {
                id = resultSet.getInt("maxID");
            }
            // close open connections
            resultSet.close();
            statement.close();
            // return the new incremented id
            return id + 1;
        }
    }

    // region $public
    private IDSO get(String name)
    {
        // connect to the DB
        try (final Connection connection = connect())
        {
            Account acc = null;
            // prepare the query
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCOUNTS WHERE USERNAME = ?");
            preparedStatement.setString(1, name);
            // execute the query
            final ResultSet resultSet = preparedStatement.executeQuery();
            // translate the result into the account object if the result set found the account
            if (resultSet.next())
            {
                acc = decipherResultSet(resultSet); // may throw SQLException
            }
            // close open connections
            resultSet.close();
            preparedStatement.close();
            // return the newly obtained account
            return acc;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }
    // endregion
}
