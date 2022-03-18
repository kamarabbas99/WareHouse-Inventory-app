package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.*;

public class ItemPersistence implements IDBLayer{

    // $region fields
    private final String dbFilePath;
    private DatabaseManager dbManager;
    // endregion

    // $region constructor
    public ItemPersistence(final String dbFilePath)
    {
        this.dbFilePath = dbFilePath;
        dbManager = DatabaseManager.getInstance();
    }
    // endregion

    // $region dbSpecific
    private Connection connect() throws SQLException
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbFilePath + ";shutdown=true", "SA", "");
    }

    // reads the ResultSet and translates it into an Item object
    private Item decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        Item item = null;
        if (resultSet.next())
        {
            // fill with data
            String itemID = resultSet.getString("itemID");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String quantity = resultSet.getString("quantity");
            String quantityMetric = resultSet.getString("quantityMetric");
            String lowThreshold = resultSet.getString("lowThreshold");
            item = new Item(Integer.valueOf(itemID), name, description, Integer.valueOf(quantity), quantityMetric, Integer.valueOf(lowThreshold));
        }
        else
        {
            // there is no data from the query
            throw new SQLException("Query did not find any results.");
        }
        return item;
    }
    // endregion

    // $region interfaceMethods
    // retrieves the item with the given id.
    @Override
    public IDSO get(int id)
    {
        IDSO item = null;
        try (final Connection connection = connect())
        {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM INVENTORYMANAGERS INNER JOIN ITEMS ON INVENTORYMANAGERS.ITEMID=ITEMS.ITEMID WHERE ITEMS.ITEMID = ? AND INVENTORYMANAGERS.INVENTORYID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, Integer.toString(dbManager.getActiveInventory()));
            final ResultSet resultSet = preparedStatement.executeQuery();
            item = decipherResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return item;
    }

    // creates a new item with the values inside the given DSO.
    // This item is added the ITEMS database table and NOT the INVENTORYMANAGERS table.
    // Therefore, this will not be reflected in the stock view.
    @Override
    public int create(IDSO object)
    {
        Item itemToCreate = null;
        int id = -1;
        try
        {
            itemToCreate = (Item) object;
        }
        catch (ClassCastException exception)
        {
            exception.printStackTrace();
        }

        try (final Connection connection = connect())
        {
            // updates item table
            final PreparedStatement itemStatement = connection.prepareStatement("INSERT INTO ITEMS VALUES (?, ?, ?, ?)");
            id = createNewID();
            String itemID = "";
            if (id > 0){
                itemID = Integer.toString(id);
            }
            itemStatement.setString(1, itemID);
            itemStatement.setString(2, itemToCreate.getName());
            itemStatement.setString(3, itemToCreate.getDescription());
            itemStatement.setString(4, itemToCreate.getQuantityMetric());
            itemStatement.executeUpdate();
            itemStatement.close();
            //TODO update transaction table
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    // Deletes the entry in the INVENTORYMANAGERS table and not the ITEMS table.
    // This is to prevent the deletion of transaction history.
    @Override
    public void delete(int id) {
        try (final Connection connection = connect())
        {
            // updates the inventorymanager table
            final PreparedStatement inventoryStatement = connection.prepareStatement("DELETE FROM INVENTORYMANAGERS WHERE ITEMID = ? AND INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(id));
            inventoryStatement.setString(2, Integer.toString(dbManager.getActiveInventory()));
            inventoryStatement.executeUpdate();
            inventoryStatement.close();
            //TODO update transaction table
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    // retrieves an inventory from the INVENTORYMANAGERS table
    @Override
    public IDSO[] getDB() {
        ArrayList<IDSO> items = new ArrayList<IDSO>();
        IDSO[] returnArray = null;

        try (final Connection connection = connect())
        {
            final PreparedStatement inventoryStatement = connection.prepareStatement("SELECT * FROM INVENTORYMANAGERS INNER JOIN ITEMS ON INVENTORYMANAGERS.ITEMID=ITEMS.ITEMID WHERE INVENTORYMANAGERS.INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(dbManager.getActiveInventory()));
            final ResultSet resultSet = inventoryStatement.executeQuery();
            while(resultSet.next())
            {
                final Item item = decipherResultSet(resultSet);
                items.add(item);
            }
            resultSet.close();
            inventoryStatement.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        returnArray = new IDSO[items.size()];
        items.toArray(returnArray);

        return returnArray;
    }

    @Override
    public void clearDB() {
        try (final Connection connection = connect())
        {
            // updates the inventorymanager table
            final PreparedStatement inventoryStatement = connection.prepareStatement("DELETE FROM INVENTORYMANAGERS WHERE INVENTORYID = ?");
            inventoryStatement.setString(1, Integer.toString(dbManager.getActiveInventory()));
            inventoryStatement.executeUpdate();
            inventoryStatement.close();
            //TODO update transaction table
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean verifyID(int id) {
        return false;
    }

    @Override
    public IDSO add(int id, int quantity)
    {
        Item itemToAdd = null;
        try (final Connection connection = connect())
        {
            // retrieve the Item first to get value.
            itemToAdd = (Item) get(id);
            int previousQuantity = Integer.valueOf(itemToAdd.getQuantity());
            String newQuantity = Integer.toString(previousQuantity + quantity);
            final PreparedStatement inventoryStatement = connection.prepareStatement("UPDATE INVENTORYMANAGERS SET QUANTITY = ? WHERE ITEMID = ? AND INVENTORYID = ?");

            inventoryStatement.setString(1, newQuantity);
            inventoryStatement.setString(2, Integer.toString(id));
            inventoryStatement.setString(3, Integer.toString(dbManager.getActiveInventory()));

            inventoryStatement.executeUpdate();
            inventoryStatement.close();

            // update old reference to item
            itemToAdd = (Item) get(id);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return itemToAdd;
    }

    @Override
    public IDSO remove(int id, int quantity) {
        Item itemToRemove = null;
        try (final Connection connection = connect())
        {
            // retrieve the Item first to get value.
            itemToRemove = (Item) get(id);
            int previousQuantity = Integer.valueOf(itemToRemove.getQuantity());
            String newQuantity;
            // make sure new value isn't negative
            if (previousQuantity - quantity < 0)
            {
                newQuantity = Integer.toString(previousQuantity);
                // TODO: throw new exception
            }
            else
            {
                newQuantity = Integer.toString(previousQuantity - quantity);
            }
            final PreparedStatement inventoryStatement = connection.prepareStatement("UPDATE INVENTORYMANAGERS SET QUANTITY = ? WHERE ITEMID = ? AND INVENTORYID = ?");

            inventoryStatement.setString(1, newQuantity);
            inventoryStatement.setString(2, Integer.toString(id));
            inventoryStatement.setString(3, Integer.toString(dbManager.getActiveInventory()));

            inventoryStatement.executeUpdate();
            inventoryStatement.close();

            // update old reference to item
            itemToRemove = (Item) get(id);
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return itemToRemove;
    }
    // endregion

    // $region updating
    // retrieves the largest item id and increments it by one
    private int createNewID()
    {
        int id = -1;
        try (final Connection connection = connect())
        {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE ITEMID = (SELECT MAX(ITEMID) FROM ITEMS)");
            if (resultSet.next())
            {
                String itemID = resultSet.getString("itemID");
                id = Integer.valueOf(itemID);
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return id + 1;
    }
    // endregion

}
