package database;

/*
A singleton class to hold the reference to the database file path.
Required because it needs to be used on startup.
Also used to get track of important values like the active account and inventory.
*/
public class DatabaseManager {

    // region $fields

    private String dbFilePath = "WIS";
    private int activeInventory = 1; // set default active inventory to the games inventory
    private int activeAccount = 1; // set default active account to first in list
    private static DatabaseManager Instance;

    // endregion

    // region $constructor

    private DatabaseManager ()
    {
    }

    // endregion

    // region $singletonMethods

    /*
    Retrieves the existing instance or creates a new instance if one does not already exist.
     */
    public static DatabaseManager getInstance()
    {
        if (Instance == null)
        {
            Instance = new DatabaseManager();
        }
        return Instance;
    }

    // endregion

    // region $gettersAndSetters

    public String getDBFilePath()
    {
        return dbFilePath;
    }

    public void setDBFilePath(final String path)
    {
        try
        {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        dbFilePath = path;
    }

    public int getActiveInventory() { return activeInventory; }

    public void setActiveInventory(int inventoryID) { activeInventory = inventoryID; }

    public int getActiveAccount() { return activeAccount; }

    public void setActiveAccount(int accountID) { activeAccount = accountID; }

    // endregion
}
