package ru.arizara.ff14log.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;

/**
 * Запросы для таблицы категорий
*/
@Dao
public interface CategoryDAO {

    @Insert
    void insertCategory(CategoryLog log);

    @Query("SELECT * FROM CategoryLog")
    List<CategoryLog> getAll();

    @Query("SELECT * FROM CategoryLog WHERE id = :id")
    CategoryLog getById(int id);
}
