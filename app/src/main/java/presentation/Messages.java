package presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.warehouseinventorysystem.R;

//MESSAGES CLASS:
// Support class for UI that can show an error message to the user
// All methods are static, so class does not need to be imported to use
// Each method call must specify an activity to put a dialog box on (usually just pass this), and an optional extra message to be shown

public class Messages {
    //Valid number input error
    public static void integerError(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle(owner.getString(R.string.integerError));
        dialog.setMessage(message);

        dialog.show();
    }

    //If an object cannot be found in the system
    //Usually a database error, probably tell user to restart app
    public static void itemNotFound(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: Item Not Found");
        dialog.setMessage(message);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
            public void onCancel(DialogInterface dialog) {
                owner.finish();
            }
        });

        dialog.show();
    }

    //An object cannot be added
    //Usually a database error, probably tell user to restart app
    //Can specify the specific kind of object in the 'type' field
    public static void itemFailAdd(final Activity owner, String type,String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: " + type + " could not be created");
        dialog.setMessage(message);

        dialog.show();
    }

    //An item cannot be added
    //Usually a database error, probably tell user to restart app
    public static void itemFailEdit(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: Item could not be edited");
        dialog.setMessage(message);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
            public void onCancel(DialogInterface dialog) {
                owner.finish();
            }
        });

        dialog.show();
    }

    public static void incorrectLogin(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: User could not be found");
        dialog.setMessage(message);

        dialog.show();
    }

    public static void matchPassword(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: Passwords do not match");
        dialog.setMessage(message);

        dialog.show();
    }

    public static void invalidClearance(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        dialog.setTitle("You do not have clearance to perform this action");
        dialog.setMessage(message);

        dialog.show();
    }

    //If a name of a given object is already within the system. show this message to the user
    //Can specify the specific kind of object in the 'type' field
    public static void invalidName(final Activity owner, String type, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        dialog.setTitle(type + " is already taken");
        dialog.setMessage(message);

        dialog.show();
    }
}
