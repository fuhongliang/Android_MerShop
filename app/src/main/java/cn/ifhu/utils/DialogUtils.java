package cn.ifhu.utils;

import android.support.v4.app.FragmentManager;

import cn.ifhu.dialog.nicedialog.ConfirmDialog;

public class DialogUtils {

    private DialogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showConfirmDialog(String title, String message, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message);
        confirmDialog.setMargin(60);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }


}