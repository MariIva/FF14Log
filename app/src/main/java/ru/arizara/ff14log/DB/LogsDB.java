package ru.arizara.ff14log.DB;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.Patch;
import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;
import ru.arizara.ff14log.ui.log.rest.OrchestrionAPIVolley;
/**
 * Хранение ссылки на БД
 * Отправка и обработка запросов к БД
 */
public class LogsDB {

    /** Объект БД*/
    private static AppDB database;

    /**
     *
     */
    public LogsDB(Context context) {
        if(database==null) {
            database = Room.databaseBuilder(context, AppDB.class, "database").build();
        }
    }

    /**
     *
     */
    public static AppDB getDB(){
        return database;
    }

    /**
     *
     */
    public static List<Orchestrion> getAllOrchestrion(){
        return database.orchestrionDAO().getAllList();
    }

    /**
     *
     */
    public static LiveData<List<LogList>> getAllLogs(){
           return database.logListDAO().getAll();
    }

    /**
     *
     */
    public static List<LogList> getAllAsList(){
        return database.logListDAO().getAllAsList();
    }

    /**
     *
     */
    public static LiveData<List<Patch>> getAllPatch(){
        return database.patchDAO().getAll();
    }

    /**
     *
     */
    public static List<Patch> getByCheck(){
        return database.patchDAO().getByCheck(true);
    }

    /**
     *
     */
    public static void addPatch(List<Patch> patches) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Patch patch : patches) {
                    database.patchDAO().insertPatch(patch);
                }
            }
        });
        thread.start();
    }

    /**
     *
     */
    public static void addLogs(LogList logList) {
         database.logListDAO().insertLogList(logList);
    }
    /**
     *
     */
    public static void addCategory(CategoryLog log) {
        CategoryLog categoryLog = database.categoryDAO().getById(log.getId());
        if (categoryLog == null)
            database.categoryDAO().insertCategory(log);
    }
    /**
     *
     */
    public static void addOrchestrion(List<Orchestrion> orchestrions) {

      /*  Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {*/
                for (Orchestrion orchestrion : orchestrions) {
                    try {
                        addCategory(orchestrion.getCategory());
                        database.orchestrionDAO().insertOrchestrion(orchestrion);
                    }
                    catch (Exception s){
                        Log.e("addOrchestrion", s.toString());
                    }
                }/*
            }
        });
        thread.start();*/
    }

    /**
     *
     */
    public static void loadData(Application application,  List<LogList> logLists){

        //List<Patch> patchesBD = getAllPatch();
        //todo
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<LogList> logBD = getAllAsList();
                for (LogList logList : logLists) {
                    if(!logBD.contains(logList)){

                        List<Orchestrion> orchestrionList = new ArrayList<>();
                        new OrchestrionAPIVolley(
                                application.getApplicationContext())
                                .getAllOrchestrion(orchestrionList);

                        addLogs(logList);
                        try {
                            while (orchestrionList.size() == 0) {
                                Thread.sleep(100);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        addOrchestrion(orchestrionList);

                    }
                }

            }
        });
        thread.start();


    }
}
