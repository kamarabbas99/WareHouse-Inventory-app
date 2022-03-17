package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

import objects.*;

public class ItemPersistence implements IDBLayer{

    // $region fields
    private final String dbFilePath;
    // endregion

    // $region constructor
    public ItemPersistence(final String dbFilePath){
        this.dbFilePath = dbFilePath;
    }
    // endregion

    // $region dbSpecific
    private Connection connect() throws SQLException
    {
        System.out.println("Trying to connect to DB...");
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
            String quantityMetric = resultSet.getString("quantityMetric");
            item = new Item(Integer.valueOf(itemID), name, description, 10, quantityMetric, 0);
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
    @Override
    public IDSO get(int id) {
        IDSO item = null;
        try (final Connection connection = connect())
        {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ITEMS WHERE ITEMS.ITEMID = ?");
            preparedStatement.setString(1, Integer.toString(id));
            final ResultSet resultSet = preparedStatement.executeQuery();
            item = decipherResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException exception)
        {
            System.out.println("Error with db");
            exception.printStackTrace();
        }
        return item;
    }

    @Override
    public int create(IDSO object) {
        return 0;
    }

    @Override
    public IDSO delete(int id) {
        return null;
    }

    @Override
    public IDSO[] getDB() {
        return new IDSO[0];
    }

    @Override
    public void clearDB() {

    }

    @Override
    public boolean verifyID(int id) {
        return false;
    }

    @Override
    public IDSO addItem(int id, int qty) {
        return null;
    }

    @Override
    public IDSO removeItem(int id, int qty) {
        return null;
    }
    // endregion

}
