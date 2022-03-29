package presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.warehouseinventorysystem.R;

public class AmountBox extends DialogFragment {
    AmountBox.AmountListener listener;
    int amount;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Gets layout inflater and inflates custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_amount, null);

        //Gets id of text field in dialog, to pull amount input from
        EditText text = view.findViewById(R.id.amount);

        builder.setView(view)
            //Adds add button, if clicked the current amount in the amount text box is sent to the calling method
            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){
                    //Attempts to get the number from the amount field and pass it to the activity
                    try {
                        amount = Integer.parseInt(text.getText().toString());
                        listener.onChangeClick(AmountBox.this, amount);
                    }

                    //If the data in amount is not a number, an error is thrown
                    catch(NumberFormatException e) {
                        Messages.integerError((Activity) listener, "Please enter a valid number for the amount");
                    }
                }
            })
            //If the cancel button is clicked, nothing is sent and the text box closes
            .setNegativeButton("Cancel",null);

        //Returns a created box
        return builder.create();
    }

    public interface AmountListener{
        public void onChangeClick(DialogFragment dialog, int amount);
    }

    //Override on attach method so we can send events to the host, which are sent through the passed listener variable
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (AmountBox.AmountListener) context;
    }
}
