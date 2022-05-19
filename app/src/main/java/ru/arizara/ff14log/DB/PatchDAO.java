package ru.arizara.ff14log.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.Patch;
/**
 * Список запросов к таблице с патчами
 */
@Dao
public interface PatchDAO {

    @Insert
    void insertPatch(Patch patch);

    @Query("SELECT * FROM Patch")
    LiveData<List<Patch>> getAll();

    @Query("SELECT * FROM Patch WHERE `check` = :check")
    List<Patch> getByCheck(boolean check);
}
