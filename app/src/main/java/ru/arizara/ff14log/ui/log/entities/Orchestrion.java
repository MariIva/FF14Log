package ru.arizara.ff14log.ui.log.entities;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;
import ru.arizara.ff14log.ui.log.entities.subEntities.x_Type;

@Entity
public class Orchestrion {
	@PrimaryKey(autoGenerate = false)
	private int id;
	private String name;
	private String description;
	private String patch;
/*	private Integer item_id;
	private String owned;
	private String number;
	private String icon;*/
	private CategoryLog category;

	private boolean check;

	public Orchestrion(int id, String name, String description, String patch,
					   CategoryLog category, boolean check) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.category = category;
		this.check = check;
	}

	public Orchestrion(int id, String name, String description, String patch, CategoryLog category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.category = category;
		this.check = false;
	}
/*public Orchestrion(int id, String name, String description, String patch, int item_id,
					   String owned, String number, String icon, CategoryLog category,
					   boolean check) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.item_id = item_id;
		this.owned = owned;
		this.number = number;
		this.icon = icon;
		this.category = category;
		this.check = check;
	}

	public Orchestrion(int id, String name, String description, String patch, int item_id,
					   String owned, String number, String icon, CategoryLog category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.patch = patch;
		this.item_id = item_id;
		this.owned = owned;
		this.number = number;
		this.icon = icon;
		this.category = category;
		this.check = false;
	}*/
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

	/*public int getItem_id() {
		return item_id;
	}

	public String getOwned() {
		return owned;
	}

	public String getNumber() {
		return number;
	}

	public String getIcon() {
		return icon;
	}*/

	public CategoryLog getCategory() {
		return category;
	}
}
