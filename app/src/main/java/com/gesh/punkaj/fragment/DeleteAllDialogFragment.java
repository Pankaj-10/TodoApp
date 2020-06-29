package com.gesh.punkaj.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeleteAllDialogFragment extends DialogFragment {

    public Boolean isDelete = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setTitle("Delete All")
                .setMessage("Are you sure you want to all todo list?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DialogListener dialogListener = (DialogListener) getActivity();
                        dialogListener.onFinishEditDialog(false);
                        dismiss();


                    }
                })

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DialogListener dialogListener = (DialogListener) getActivity();
                        dialogListener.onFinishEditDialog(true);
                        dismiss();


                    }
                })
                .create();
    }

    public interface DialogListener {
        void onFinishEditDialog(Boolean isDeleted);
    }

}

