package cn.ifhu.dialog.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.ifhu.R;

public class ConfirmDialog extends BaseNiceDialog {
    public ButtonOnclick buttonOnclick;
    public String title;
    public String message;

    public static ConfirmDialog newInstance(String title, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setButtonOnclick(ButtonOnclick buttonOnclick) {
        this.buttonOnclick = buttonOnclick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString("title");
        message = bundle.getString("message");
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_layout;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
        holder.setText(R.id.title, title);
        holder.setText(R.id.message, message);

        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (buttonOnclick != null){
                    buttonOnclick.cancel();
                }
            }
        });

        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (buttonOnclick != null){
                    buttonOnclick.ok();
                }
            }
        });
    }


    public interface ButtonOnclick{
        void cancel();
        void ok();
    }
}