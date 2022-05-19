package ru.arizara.ff14log.ui.log.entities.subEntities;

import androidx.room.Entity;
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
	
	public CategoryLog(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
