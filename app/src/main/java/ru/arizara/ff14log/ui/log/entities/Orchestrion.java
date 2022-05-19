package ru.arizara.ff14log.ui.log.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;
/**
 * Класс логов
 * Описание таблицы в БД
 */
@Entity(foreignKeys = @ForeignKey(entity = CategoryLog.class, parentColumns = "id", childColumns = "categoryID"))
public class Orchestrion {

	/**Поле первичный ключ*/
	@PrimaryKey(autoGenerate = false)
	private int id;
	/** Название мелодии*/
	private String name;
	/** Описание мелодии*/
	private String description;
	/** Патч добавления*/
	private String patch;
	/** Категория мелодии
	 * Игнорируется БД
	 */
	@Ignore
	private CategoryLog category;
	/** id категории*/
	private int categoryID;
	/** Метка, получения мелодии*/
	private boolean check;

	@Ignore
	public Orchestrion(int id, String name, String description, String patch,
					   CategoryLog category, boolean check) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.category = category;
		this.check = check;
	}


	public Orchestrion(int id, String name, String description, String patch, int categoryID, boolean check) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.categoryID = categoryID;
		this.check = check;
	}

	@Ignore
	public Orchestrion(int id, String name, String description, String patch, CategoryLog category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.category = category;
		this.categoryID = category.getId();
		this.check = false;
	}
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPatch() {
		return patch;
	}

	public CategoryLog getCategory() {
		return category;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Orchestrion that = (Orchestrion) o;
		return id == that.id && name.equals(that.name) && description.equals(that.description) && patch.equals(that.patch);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, patch);
	}
}
