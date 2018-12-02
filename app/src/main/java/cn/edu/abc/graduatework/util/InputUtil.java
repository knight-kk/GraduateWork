package cn.edu.abc.graduatework.util;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.abc.graduatework.R;

public class InputUtil {


    /**
     * 监听输入框，改变按钮是否可用
     *
     * @param textView  按钮
     * @param editTexts 被监听的输入框
     */
    public static void ChangeButtonStatue(final TextView textView, final EditText... editTexts) {
        for (final EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean isEnable = true;
                    for (EditText text : editTexts) {
                        if (text.getText().toString().equals("")) {
                            isEnable = false;
                            break;
                        }
                    }
                    if (isEnable) {
                        textView.setBackgroundResource(R.drawable.bg_btn_orange);
                        textView.setEnabled(true);
                    } else {
                        textView.setBackgroundResource(R.drawable.bg_btn_gray);
                        textView.setEnabled(false);
                    }


                }
            });
        }

    }
}
