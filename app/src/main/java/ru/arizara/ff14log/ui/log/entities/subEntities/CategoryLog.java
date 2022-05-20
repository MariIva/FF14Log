package ru.arizara.ff14log.ui.log.entities.subEntities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Класс категорий
 * Описание таблицы в БД
 */
@Entity
public class CategoryLog {
	/**Поле первичный ключ*/
	@PrimaryKey(autoGenerate = false)
	private int id;

	/** Название категории*/
	private String name;

	private String icon;

	public CategoryLog(int id, String name, String icon) {
		this.id = id;
		this.name = name;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}
}