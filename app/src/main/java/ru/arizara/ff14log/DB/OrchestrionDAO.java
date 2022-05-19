package ru.arizara.ff14log.DB;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
/**
 * Список запросов к таблице мелодий
 */
@Dao
public interface  OrchestrionDAO {

    @Insert
    void insertOrchestrion(Orchestrion orchestrion);

    @Update
    void updateOrchestrion(Orchestrion orchestrion);



    /**
     * Запрос для синхронизации с LiveData
     */
    @Query("SELECT * FROM Orchestrion")
    LiveData<List<Orchestrion>> getAll();
    @Query("SELECT * FROM Orchestrion")
    List<Orchestrion> getAllList();

}
