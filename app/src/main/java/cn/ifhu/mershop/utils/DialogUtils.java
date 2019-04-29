package cn.ifhu.mershop.utils;

import android.support.v4.app.FragmentManager;

import cn.ifhu.mershop.dialog.nicedialog.BuyDiscountDialog;
import cn.ifhu.mershop.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.mershop.dialog.nicedialog.ConfirmInputDialog;

public class DialogUtils {

    private DialogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showBuyDiscountDialog(FragmentManager manager, BuyDiscountDialog.ButtonOnclick buttonOnclick) {
        BuyDiscountDialog confirmDialog = BuyDiscountDialog.newInstance();
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showInputConfirmDialog(String title, String message, FragmentManager manager, ConfirmInputDialog.ButtonOnclick buttonOnclick) {
        ConfirmInputDialog confirmDialog = ConfirmInputDialog.newInstance(title, message);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showConfirmDialog(String title, String message, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showConfirmDialog(String title, String message, String cancel, String ok, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message,cancel,ok);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

}