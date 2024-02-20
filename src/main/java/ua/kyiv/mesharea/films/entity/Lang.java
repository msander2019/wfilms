package ua.kyiv.mesharea.films.entity;

import java.util.Locale;

public class Lang {

    private int index;
    private String code;
    private String name;
    private Locale locale;

    public Lang(int index, String code, String name, Locale locale) {
        this.index = index;
        this.code = code;
        this.name = name;
        this.locale = locale;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return name;
    }
}
