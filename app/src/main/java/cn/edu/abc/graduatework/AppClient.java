package cn.edu.abc.graduatework;


import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

import cn.edu.abc.graduatework.entity.icon.AliBaBaModule;

public class AppClient extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule()).with(new AliBaBaModule());
    }
}
