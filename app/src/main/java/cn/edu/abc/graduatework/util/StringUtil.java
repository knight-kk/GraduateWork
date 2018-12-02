package cn.edu.abc.graduatework.util;


import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        } else {
            return isEmpty(charSequence.toString());
        }
    }

    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        } else {
            return string.equals("");
        }
    }

    public static boolean isEmail(String email) {
        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return Pattern.compile(pattern).matcher(email).matches();
    }

}
