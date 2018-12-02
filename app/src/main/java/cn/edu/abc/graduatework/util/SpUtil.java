package cn.edu.abc.graduatework.util;


import android.content.Context;
import android.content.SharedPreferences;

import cn.edu.abc.graduatework.constant.SpConstant;

public class SpUtil {

    public static SharedPreferences getSharedPreferences(Context context){
      return context.getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
    }

    public static String getUserId(Context context){
       return  getSharedPreferences(context).getString(SpConstant.USER_ID,"");
    }
}
