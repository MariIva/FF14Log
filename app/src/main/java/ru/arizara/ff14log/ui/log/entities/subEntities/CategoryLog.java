package ru.arizara.ff14log.ui.log.entities.subEntities;

public class CategoryLog {
	private int id;
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
