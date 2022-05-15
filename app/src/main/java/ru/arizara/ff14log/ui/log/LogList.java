package ru.arizara.ff14log.ui.log;

public class LogList {
    private byte id;
    private String name;


    public LogList(byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public byte getId() {
        return id;
    }
}
