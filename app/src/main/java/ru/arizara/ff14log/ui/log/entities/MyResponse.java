package ru.arizara.ff14log.ui.log.entities;
import java.util.List;

public class MyResponse<T> {
	private Object query;
	private int count;
	private List<T> results;

	public MyResponse(Object query, int count, List<T> results) {
		this.query = query;
		this.count = count;
		this.results = results;
	}

	public Object getQuery() {
		return query;
	}

	public int getCount() {
		return count;
	}

	public List<T> getResults() {
		return results;
	}
}
