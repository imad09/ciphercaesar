package com.aniskhenissa.ciphercaesar.Models;

public class CaesarDocuementModel {
    private String name,data,converted;
    private int key,shift;
    private long time;

    public CaesarDocuementModel() {
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getConverted() {
        return converted;
    }

    public int getKey() {
        return key;
    }

    public int getShift() {
        return shift;
    }

    public long getTime() {
        return time;
    }
}
