package objects;

import android.text.SpanWatcher;

import java.util.Date;
import java.sql.Timestamp;

/*
This class contains information related to accounts that are stored in the database.
It is a Domain Specific Object that will be passed through the layers of our system.

An account will determine what a user can or cannot do with our system.
e.g. can the create a new item, delete an item or add some amount to an item.
 */
public class Account implements IDSO {
    // region $fields

    private int id;
    private String username;
    private String password;
    private int privilege;
    private Timestamp dateCreated;

    // endregion

    // region $constructors

    public Account(){
        id = -1;
        username = "default";
        password = "123";
        privilege = 1;
        dateCreated = new Timestamp(new Date().getTime());
    }

    public Account(int id, String username, String password, int privilege) throws NullPointerException {
        this.id = id;
        if (username == null)
        {
            throw new NullPointerException("Cannot create an account without a username.");
        }
        else
        {
            this.username = username;
        }
        if (password == null)
        {
            throw new NullPointerException("Cannot create an account without a password.");
        }
        else
        {
            this.password = password;
        }
        this.privilege = privilege;
        dateCreated = new Timestamp(new Date().getTime());
    }

    public Account(int id, String username, String password, int privilege, Timestamp dateCreated) throws NullPointerException {
        this.id = id;
        if (username == null)
        {
            throw new NullPointerException("Cannot create an account without a username.");
        }
        else
        {
            this.username = username;
        }
        if (password == null)
        {
            throw new NullPointerException("Cannot create an account without a password.");
        }
        else
        {
            this.password = password;
        }
        this.privilege = privilege;
        if (dateCreated == null)
        {
            this.dateCreated = new Timestamp(new Date().getTime());
        }
        else
        {
            this.dateCreated = dateCreated;
        }
    }

    // endregion

    // region $interfaceMethods

    @Override
    public int getID(){
        return id;
    }

    // endregion

    // region $getters

    public String getUsername(){
        return username;
    }

    public String getPassword() { return password; }

    public Date getDateCreated(){
        return dateCreated;
    }

    public Boolean isAdmin(){
        return (privilege == 0);
    }

    public int getPrivilege(){
        return privilege;
    }
    // endregion

    // region $verification

    // returns true if either the passwords match or the current password is null
    public boolean verifyPassword(String password){
        boolean isCorrect = false;
        // a null entry will always fail
        if (password != null)
        {
            if (this.password != null)
            {
                isCorrect = this.password.equals(password);
            }
        }

        return isCorrect;
    }

    public boolean changePassword(String oldPassword, String newPassword){
        boolean isSuccess = false;

        if (newPassword != null && verifyPassword(oldPassword))
        {
            password = newPassword;
            isSuccess = true;
        }

        return isSuccess;
    }

    public boolean equals(Account otherAccount){
        return otherAccount.getID() == this.id;
    }

    // endregion
}
