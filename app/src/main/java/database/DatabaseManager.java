package database;

// A singleton class to hold the reference to the database file path.
// Required because it needs to be used on startup.
public class DatabaseManager {
    // region $fields
    private String dbFilePath = "WIS";
    private static DatabaseManager Instance;
    // endregion

    // region $constructor
    private DatabaseManager ()
    {

    }
    // endregion

    // region $singletonMethods
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
    // sets the path for the database file
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

    // gets the path for the database file
    public String getDBFilePath()
    {
        return dbFilePath;
    }
    // endregion
}
