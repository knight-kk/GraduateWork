package cn.edu.abc.graduatework.entity.icon;

import com.joanzapata.iconify.Icon;


public enum AliBaBaIcons implements Icon {
    hot_icon('\ue647'),
    prize_icon('\uea23');

    char character;

    AliBaBaIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
