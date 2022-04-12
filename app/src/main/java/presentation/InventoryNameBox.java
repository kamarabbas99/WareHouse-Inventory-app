package presentation;

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

public class InventoryNameBox extends DialogFragment {
    InventoryNameBox.NameListener listener;
    String name;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Gets layout inflater and inflates custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_inventory_name, null);

        EditText text = view.findViewById(R.id.name);

        builder.setView(view)
                //Creates an add button, if clicked the current name in the box is passed to calling activity
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        name = text.getText().toString();
                        listener.onChangeClick(InventoryNameBox.this, name);
                    }
                })
                //If the cancel button is clicked, nothing is sent and the text box closes
                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    public interface NameListener{
        public void onChangeClick(DialogFragment dialog, String name);
    }

    //Override on attach method so we can send events to the host, which are sent through the passed listener variable
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (InventoryNameBox.NameListener) context;
    }
}
