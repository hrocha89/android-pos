package com.henrique.colecaodiscos.utils;

import static com.henrique.colecaodiscos.R.string.nao;
import static com.henrique.colecaodiscos.R.string.sim;
import static com.henrique.colecaodiscos.R.string.tituloConfirmar;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


public class DialogUtils {

    public static void confirm(Context context, DialogInterface.OnClickListener listener, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(tituloConfirmar);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(message);

        builder.setPositiveButton(sim, listener);
        builder.setNegativeButton(nao, listener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
