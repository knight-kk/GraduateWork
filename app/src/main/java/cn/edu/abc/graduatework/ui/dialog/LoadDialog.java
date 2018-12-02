package cn.edu.abc.graduatework.ui.dialog;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

import cn.edu.abc.graduatework.R;

public class LoadDialog extends AppCompatDialog {


    public LoadDialog(Context context) {
        super(context, R.style.transparentDialog);
    }

    public LoadDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
        setCancelable(false);


    }
}
