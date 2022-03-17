package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        return DriverManager.getConnection("jdbc:hsqldb:file" + dbFilePath + ";shutdown=true", "SA", "");
    }

    private Item decipherResultSet(final ResultSet resultSet) throws SQLException
    {
        String itemID = resultSet.getString("itemID");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String quantity = resultSet.getString("quantity");
        String quantityMetric = resultSet.getString("quantityMetric");
        String lowThreshold = resultSet.getString("lowThreshold");
        int id = Integer.valueOf(itemID);
        int quantityInt = Integer.valueOf(quantity);
        int lowThresholdInt = Integer.valueOf(lowThreshold);
        return new Item(id, name, description, quantityInt, quantityMetric, lowThresholdInt);

    }
    // endregion

    // $region interfaceMethods
    @Override
    public IDSO get(int id) {
        return null;
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
