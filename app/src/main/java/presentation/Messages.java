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

    public static void itemFailAdd(final Activity owner, String message){
        AlertDialog dialog = new AlertDialog.Builder(owner).create();

        //Sets message parameters
        dialog.setTitle("Error: Item could not be created");
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
}
