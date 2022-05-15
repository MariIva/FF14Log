package ru.arizara.ff14log.ui.log.DB;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;

@Dao
public interface  OrchestrionDAO {

    @Insert
    void insertOrchestrion(Orchestrion orchestrion);


    @Query("SELECT * FROM Orchestrion")
    List<Orchestrion> getAllOrchestrion();



    @Update
    void updateOrchestrion(Orchestrion orchestrion);
}
