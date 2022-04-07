package database;

/*
A singleton class to hold the reference to the database file path.
Required because it needs to be used on startup.
Also used to get track of important values like the active account and inventory.
*/
public class DatabaseManager {

    // region $fields

    private String dbFilePath = "WIS";
    private static int activeInventory = 1; // set default active inventory to the games inventory
    private static int activeAccount = 1; // set default active account to first in list
    private static DatabaseManager Instance;
    private static ItemPersistence itemPersistence = null;
    private static InventoryManagerPersistence inventoryManagerPersistence = null;
    private static InventoryPersistence inventoryPersistence = null;
    private static AccountPersistence accountPersistence = null;

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

    /*
    Retrieves and (if needed) creates an ItemPersistence.
     */
    public static ItemPersistence getItemPersistence()
    {
        if (itemPersistence == null)
        {
            itemPersistence = new ItemPersistence(DatabaseManager.Instance.getDBFilePath());
        }
        return itemPersistence;
    }

    /*
    Retrieves and (if needed) creates an InventoryManagerPersistence.
     */
    public static InventoryManagerPersistence getInventoryManagerPersistence()
    {
        if (inventoryManagerPersistence == null)
        {
            inventoryManagerPersistence = new InventoryManagerPersistence(DatabaseManager.Instance.getDBFilePath());
        }
        return inventoryManagerPersistence;
    }

    /*
    Retrieves and (if needed) creates an InventoryPersistence.
     */
    public static InventoryPersistence getInventoryPersistence()
    {
        if (inventoryPersistence == null)
        {
            inventoryPersistence = new InventoryPersistence(DatabaseManager.Instance.getDBFilePath());
        }
        return inventoryPersistence;
    }

    /*
    Retrieves and (if needed) creates an InventoryManagerPersistence.
     */
    public static AccountPersistence getAccountPersistence()
    {
        if (accountPersistence == null)
        {
            accountPersistence = new AccountPersistence(DatabaseManager.Instance.getDBFilePath());
        }
        return accountPersistence;
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

    public static int getActiveInventory() { return activeInventory; }

    public static void setActiveInventory(int inventoryID) { activeInventory = inventoryID; }

    public static int getActiveAccount() { return activeAccount; }

    public static void setActiveAccount(int accountID) { activeAccount = accountID; }

    // endregion
}
