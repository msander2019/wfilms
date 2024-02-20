package ua.kyiv.mesharea.films.utils;

import ua.kyiv.mesharea.films.entity.Lang;

import java.util.Locale;

public class LocaleManager {

    public static final Locale UA_LOCALE = new Locale("ua");
    public static final Locale EN_LOCALE = new Locale("en");

        private static Lang currentLang;

    public static Lang getCurrentLang() {
        return currentLang;
    }

    public static void setCurrentLang(Lang currentLang) {
        LocaleManager.currentLang = currentLang;
    }
}
