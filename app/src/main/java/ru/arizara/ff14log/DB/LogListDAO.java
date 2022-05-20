package ru.arizara.ff14log.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.LogList;

/**
 * Запросы к таблице со списком логов
 */
@Dao
public interface LogListDAO {

    @Insert
    void insertLogList(LogList logList);

    @Update
    void updateLogList(LogList logList);

    /**
     * Запрос для синхронизации с LiveData
     */
    @Query("SELECT * FROM LogList")
    LiveData<List<LogList>> getAll();

    @Query("SELECT * FROM LogList")
    List<LogList> getAllAsList();

    @Query("SELECT * FROM LogList WHERE name = :name")
    LogList getByName(String name);
}
