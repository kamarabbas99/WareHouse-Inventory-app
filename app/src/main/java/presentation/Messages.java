package presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.warehouseinventorysystem.R;

public class Messages {
    public static void integerError(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle(owner.getString(R.string.integerError));
        dialog.setMessage(message);

        dialog.show();
    }

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

    public static void itemFailAdd(final Activity owner, String type,String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: " + type + " could not be created");
        dialog.setMessage(message);

        dialog.show();
    }

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

    public static void invalidName(final Activity owner, String type, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        dialog.setTitle(type + " is already taken");
        dialog.setMessage(message);

        dialog.show();
    }
}
