package cn.edu.abc.graduatework.entity.icon;


import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

public class AliBaBaModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return AliBaBaIcons.values();
    }
}
