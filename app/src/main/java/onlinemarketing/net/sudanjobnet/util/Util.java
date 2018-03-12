package onlinemarketing.net.sudanjobnet.util;

/**
 * Created by muawia.ibrahim on 3/9/2016.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import onlinemarketing.net.sudanjobnet.R;


public class Util {



    public static void displayDialog(String title, String msg,
                                     final Context context, final boolean isFinish) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,context.getString(R.string.strOK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });
        if (!((Activity) context).isFinishing())
            alertDialog.show();
    }




    public static Boolean checknetwork(Context mContext) {

        NetworkInfo info = ((ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to
            // disable internet while roaming, just return false
            return true;
        }

        return true;

    }


}