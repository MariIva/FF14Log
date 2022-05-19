package ru.arizara.ff14log.ui.log.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
/**
 * Класс логов
 * Описание таблицы в БД
 */
@Entity
public class Patch {
    /**Поле первичный ключ*/
    @PrimaryKey(autoGenerate = false)
    private int id;
    /** Глобальное название патча*/
    private String name;
    /** Метка, загружен патч*/
    private boolean check;

    public Patch() {
    }
    @Ignore
    public Patch(int id, String name, boolean check) {
        this.id = id;
        this.name = name;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
