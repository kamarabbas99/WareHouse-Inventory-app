package presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.warehouseinventorysystem.R;

public class AccountConfirmationBox extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Account created successfully").setPositiveButton("Continue", null);
        return builder.create();
    }
}
