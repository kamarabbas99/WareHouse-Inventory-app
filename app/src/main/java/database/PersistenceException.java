package database;

import java.sql.SQLOutput;

/*
Exceptions that are thrown from the database layer.
Required because the database classes already try and catch SQLExceptions but
need a way to send the message up to the other layers.
Code provided by instructor:
    https://code.cs.umanitoba.ca/winter-2022-a02/sample-project/-/blob/master/app/src/main/java/comp3350/srsys/persistence/hsqldb/PersistenceException.java
 */
public class PersistenceException extends RuntimeException
{
    public PersistenceException(final Exception cause)
    {
        super(cause);
     }

}
