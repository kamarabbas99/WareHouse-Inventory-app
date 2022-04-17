package presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.warehouseinventorysystem.R;

public class AlertBox extends DialogFragment {
    AlertListener listener;

    //Creates a AlertDialog with a warning message and yes or no buttons
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.DialogDeleteWarning)
                //If no button is clicked nothing is sent and the text dialog exits
                .setNegativeButton("No", null)
                //If yes button is clicked, message is sent to calling class via the listener
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        listener.onPositiveClick(AlertBox.this);
                    }
                });

        //Returns a created box
        return builder.create();
    }

    //An activity will create an instance of this  listener so the dialog box can communicate with the calling activity
    //Implement this to receive callbacks
    public interface AlertListener{
        public void onPositiveClick(DialogFragment dialog);
    }

    //Override on attach method so we can send events to the host, which are sent through the passed listener variable
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (AlertListener) context;
    }
}
