package ru.arizara.ff14log.ui.log.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Класс логов
 * Описание таблицы в БД
 */
@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class LogList {
    /**Поле первичный ключ*/
    @PrimaryKey(autoGenerate = true)
    private int id;
    /** Имя лога*/
    private String name;
    /** Найденное количество объектов лога*/
    private int current;
    /** клочество объектов лога*/
    private int count;

    public LogList(int id, String name, int current, int count) {
        this.id = id;
        this.name = name;
        this.current = current;
        this.count = count;
    }

    @Ignore
    public LogList(String name, int current, int count) {
        this.name = name;
        this.current = current;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrent() {
        return current;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogList logList = (LogList) o;
        return name.equals(logList.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
