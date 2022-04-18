package presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AccountConfirmationBox extends DialogFragment {
    AccountListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Account created successfully").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPositiveClick(AccountConfirmationBox.this);
            }
        });
        return builder.create();
    }

    //Interface to send messages to caller
    public interface AccountListener {
        public void onPositiveClick(DialogFragment dialog);
    }

    //Override on attach method so we can send events to the host, which are sent through the passed listener variable
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (AccountConfirmationBox.AccountListener) context;
    }
}
