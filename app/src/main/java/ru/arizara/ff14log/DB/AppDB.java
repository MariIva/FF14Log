package ru.arizara.ff14log.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.Patch;
import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;

/**
Описание локальная БД
 */
@Database(entities = {
        Orchestrion.class,
        CategoryLog.class,
        LogList.class,
        Patch.class},
        version = 1)
public abstract class AppDB extends RoomDatabase {

    /** Доступ к запросам таблицы мелодий*/
    public abstract OrchestrionDAO orchestrionDAO();

    /** Доступ к запросам таблицы категорий*/
    public abstract CategoryDAO categoryDAO();

    /** Доступ к запросам таблицы логов*/
    public abstract LogListDAO logListDAO();

    /** Доступ к запросам таблицы патчей*/
    public abstract PatchDAO patchDAO();
}

