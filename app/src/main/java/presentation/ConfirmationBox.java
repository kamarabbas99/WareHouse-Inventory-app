package presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.warehouseinventorysystem.R;

//This class just shows a simple text box with a confirmation that the item was added to the database
public class ConfirmationBox extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ItemAddedMessage).setPositiveButton("Continue", null);
        return builder.create();
    }
}
