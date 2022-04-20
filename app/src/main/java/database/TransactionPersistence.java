package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;

import objects.IDSO;
import objects.Transaction;

public class TransactionPersistence implements IDBLayer {

    // region $fields

    private final String dbFilePath;
    private DatabaseManager dbManager;

    // endregion

    // region $constructor

    public TransactionPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
    }

    // endregions

    // region $interfaceOverrides

    /* GET
    PURPOSE:
        Retrieves the transaction with the given transactionID if found in the Transactions table.
    INPUT:
        id              The transaction object to look for in the Transactions table.
    OUTPUT:
        Returns the obtained Transaction from the Transactions table.
        Returns a null if the Transaction is not found.
        Throws a Persistence Exception if something went wrong with the query.
     */
    @Override
    public IDSO get(int id)
    {
        // connect to the DB
        try (final Connection connection = connect())
        {
            Transaction transaction = null;
            // prepare the query
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTIONS WHERE TRANSACTIONID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            // execute the query
            final ResultSet resultSet = preparedStatement.executeQuery();
            // translate the result into the Item object if the result set found the item
            if (resultSet.next())
            {
                transaction = decipherResultSet(resultSet); // may throw SQLException
            }
            // close open connections
            resultSet.close();
            preparedStatement.close();
            // return the newly obtained Item
            return transaction;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CREATE
    PURPOSE:
        Creates a new Transaction with the values inside the given DSO.
    NOTES:
        This Transaction is added to the Transactions table.
    INPUT:
        object              The Transaction object to create in the Transactions table.
    OUTPUT:
        Returns the transactionID of the newly created item.
        Returns -1 if the provided parameter is not an instance of the Transaction class.
        Throws a PersistenceException if something went wrong with query.
     */
    @Override
    public int create(IDSO object)
    {
        Transaction transactionToCreate;

        // a check to verify the provided parameter is an instance of the Item class
        if (object instanceof Transaction)
        {
            transactionToCreate = (Transaction) object;
        }
        else
        {
            return -1;
        }

        // connect to the DB
        try (final Connection connection = connect())
        {
            // retrieve a new ID to give to the item.
            int id = createNewID();
            // prepare the query
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO TRANSACTIONS VALUES (?, ?, ?, ?, ?, ?, ?)");
            // fill out the query variables
            statement.setInt(1, id);
            statement.setInt(2, transactionToCreate.getAccountID());
            statement.setInt(3, transactionToCreate.getInventoryID());
            statement.setInt(4, transactionToCreate.getItemID());
            statement.setString(5, transactionToCreate.getTransactionType());
            statement.setInt(6, transactionToCreate.getQuantity());
            statement.setString(7, transactionToCreate.getDateCreated().toString());
            // execute the query
            statement.executeUpdate();
            // close open connections
            statement.close();
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
        (Theoretically) Deletes the entry in the Transaction table.
    NOTES:
        Not implemented to prevent the deletion of transaction history.
    INPUT:
        id              The transactionID to look for and delete from the Transactions table.
    OUTPUT:
        Throws a PersistenceException if something went wrong with the query.
    */
    @Override
    public void delete(int id) throws PersistenceException
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // prepare the query
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM TRANSACTIONS WHERE TRANSACTIONID = ?");
            statement.setInt(1, id);
            // execute the query
            statement.executeUpdate();
            // close open connections
            statement.close();
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* GETDB
    PURPOSE:
        Retrieves all the Transactions in the Transactions table.
    OUTPUT:
        Returns an array of all the Transactions found within the Transactions table.
        Returns NULL if no Transactions were found.
        Throws a PersistenceException if something went wrong with the query.
     */
    @Override
    public IDSO[] getDB()
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<IDSO> transactionsList = new ArrayList<IDSO>();
            IDSO[] transactionsArray = null; // the returned array
            // prepare the query
            final Statement statement = connection.createStatement();
            // execute the query
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM TRANSACTIONS");
            // cycle through the result and add the transactions to the list
            while(resultSet.next())
            {
                final Transaction transaction = decipherResultSet(resultSet);
                transactionsList.add(transaction);
            }
            // close open connections
            resultSet.close();
            statement.close();

            // convert the arraylist to an array if it has any entries
            if (transactionsList.size() > 0)
            {
                transactionsArray = new IDSO[transactionsList.size()];
                transactionsList.toArray(transactionsArray);
            }
            // return the array with all the transactions
            return transactionsArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* CLEARDB
    PURPOSE:
        (Theoretically) Completely removes all transactions from the Transactions table.
    NOTES:
        Not implemented to prevent the deletion of transaction history.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public void clearDB() throws PersistenceException
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            // prepare the query
            final Statement statement = connection.createStatement();
            // execute the query
            final ResultSet resultSet = statement.executeQuery("DELETE FROM TRANSACTIONS");
            // close open connections
            resultSet.close();
            statement.close();
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
//        Exception exception = new Exception("Cannot delete the entire Transactions table.");
//        throw new PersistenceException(exception);
    }

    /* ADD
    PURPOSE:
        Does not make sense for the Transactions table.
    NOTES:
        This method is not implemented since you the Transactions table does not have it's quantity column modified.
    INPUT:
        id              The transactionsID to look for.
        quantity        (Theoretically) The amount to increase the quantity of the transaction by.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public IDSO add(int id, int quantity) throws PersistenceException
    {
        Exception exception = new Exception("Transactions can only be created and not added.");
        throw new PersistenceException(exception);
    }

    /* REMOVE
    PURPOSE:
        Does not make sense for the Transactions table.
    NOTES:
        This method is not implemented since you can modify the Transactions table.
    INPUT:
        id              The transactionsID to look for in the active inventory.
        quantity        (Theoretically) The amount to decrease the quantity of the transaction by.
    OUTPUT:
        Always throws a PersistenceException.
     */
    @Override
    public IDSO remove(int id, int quantity) throws PersistenceException
    {
        Exception exception = new Exception("Transactions cannot be removed.");
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
    private Transaction decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        int transactionID = resultSet.getInt("TRANSACTIONID");
        int accountID = resultSet.getInt("ACCOUNTID");
        int inventoryID = resultSet.getInt("INVENTORYID");
        int itemID = resultSet.getInt("ITEMID");
        String transactionType = resultSet.getString("TRANSACTIONTYPE");
        int quantity = resultSet.getInt("QUANTITY");
        Timestamp dateCreated = resultSet.getTimestamp("DATECREATED");
        return new Transaction(transactionID, accountID, inventoryID, itemID, transactionType, quantity, dateCreated);
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
            final ResultSet resultSet = statement.executeQuery("SELECT MAX(TRANSACTIONID) AS MAXID FROM TRANSACTIONS");
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

    // endregion

    // region $publicMethods

    /* GETACCOUNTTRANSACTIONS
    PURPOSE:
        Retrieves all the Transactions in the Transactions table associated with the given accountID.
    OUTPUT:
        Returns an array of all the Transactions found within the Transactions table.
        Returns NULL if no Transactions were found.
        Throws a PersistenceException if something went wrong with the query.
     */
    public Transaction[] getAccountTransactions(int accountID)
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
            Transaction[] transactionsArray = null; // the returned array
            // prepare the query
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM TRANSACTIONS WHERE ACCOUNTID = ?");
            // fill out the query variables
            statement.setInt(1, accountID);
            // execute the query
            final ResultSet resultSet = statement.executeQuery();
            // cycle through the result and add the transactions to the list
            while(resultSet.next())
            {
                final Transaction transaction = decipherResultSet(resultSet);
                transactionsList.add(transaction);
            }
            // close open connections
            resultSet.close();
            statement.close();

            // convert the arraylist to an array if it has any entries
            if (transactionsList.size() > 0)
            {
                transactionsArray = new Transaction[transactionsList.size()];
                transactionsList.toArray(transactionsArray);
            }
            // return the array with all the transactions
            return transactionsArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* GETITEMTRANSACTIONS
    PURPOSE:
        Retrieves all the Transactions in the Transactions table associated with the given itemID.
    OUTPUT:
        Returns an array of all the Transactions found within the Transactions table.
        Returns NULL if no Transactions were found.
        Throws a PersistenceException if something went wrong with the query.
     */
    public Transaction[] getItemTransactions(int itemID)
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
            Transaction[] transactionsArray = null; // the returned array
            // prepare the query
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM TRANSACTIONS WHERE ITEMID = ?");
            // fill out the query variables
            statement.setInt(1, itemID);
            // execute the query
            final ResultSet resultSet = statement.executeQuery();
            // cycle through the result and add the transactions to the list
            while(resultSet.next())
            {
                final Transaction transaction = decipherResultSet(resultSet);
                transactionsList.add(transaction);
            }
            // close open connections
            resultSet.close();
            statement.close();

            // convert the arraylist to an array if it has any entries
            if (transactionsList.size() > 0)
            {
                transactionsArray = new Transaction[transactionsList.size()];
                transactionsList.toArray(transactionsArray);
            }
            // return the array with all the transactions
            return transactionsArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    /* GETINVENTORYTRANSACTIONS
    PURPOSE:
        Retrieves all the Transactions in the Transactions table associated with the given inventoryID.
    OUTPUT:
        Returns an array of all the Transactions found within the Transactions table.
        Returns NULL if no Transactions were found.
        Throws a PersistenceException if something went wrong with the query.
     */
    public Transaction[] getInventoryTransactions(int inventoryID)
    {
        // connect to DB
        try (final Connection connection = connect())
        {
            // initialize collections
            ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
            Transaction[] transactionsArray = null; // the returned array
            // prepare the query
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM TRANSACTIONS WHERE INVENTORYID = ?");
            // fill out the query variables
            statement.setInt(1, inventoryID);
            // execute the query
            final ResultSet resultSet = statement.executeQuery();
            // cycle through the result and add the transactions to the list
            while(resultSet.next())
            {
                final Transaction transaction = decipherResultSet(resultSet);
                transactionsList.add(transaction);
            }
            // close open connections
            resultSet.close();
            statement.close();

            // convert the arraylist to an array if it has any entries
            if (transactionsList.size() > 0)
            {
                transactionsArray = new Transaction[transactionsList.size()];
                transactionsList.toArray(transactionsArray);
            }
            // return the array with all the transactions
            return transactionsArray;
        }
        catch (final SQLException exception)
        {
            throw new PersistenceException(exception);
        }
    }

    // endregion
}
