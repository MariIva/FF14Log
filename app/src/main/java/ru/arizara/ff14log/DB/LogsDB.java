package ru.arizara.ff14log.DB;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.Patch;
import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;
import ru.arizara.ff14log.ui.log.entities.subEntities.OrchestrionWithCategory;
import ru.arizara.ff14log.ui.log.rest.ImageAPIVolley;
import ru.arizara.ff14log.ui.log.rest.OrchestrionAPIVolley;
/**
 * Хранение ссылки на БД
 * Отправка и обработка запросов к БД
 */
public class LogsDB {

    /** Объект БД*/
    private static AppDB database;

    /**
     * Создание/отклытие БД
     * @param context - контекст приложения
     */
    public LogsDB(Context context) {
        if(database==null) {
            database = Room.databaseBuilder(context, AppDB.class, "database").build();
        }
    }

    /**
     * Получение ссылки на БД
     * @return ссылка на БД
     */
    public static AppDB getDB(){
        return database;
    }

    /**
     * Получение из БД списка мелодий
     * @return весь список мелодий из БД
     */
    public static List<OrchestrionWithCategory> getAllOrchestrion(){
        return database.orchestrionDAO().getAllList();
    }

   /**
    *
    */
    public static LogList getLogByName(String name){
           return database.logListDAO().getByName(name);
    }

    /**
     * Получение из БД списка логов
     * @return список всех логов из БД
     */
    public static List<LogList> getAllAsList(){
        return database.logListDAO().getAllAsList();
    }

    /**
     *
     */
    public static void updateOrchestrion(Orchestrion orchestrion){
        database.orchestrionDAO().updateOrchestrion(orchestrion);
    }

    /**
     *
     */
    public static void updateLog(LogList logList){
        database.logListDAO().updateLogList(logList);
    }

   /*
    public static List<Patch> getByCheck(){
        return database.patchDAO().getByCheck(true);
    }*/

    /**
     * Добавление в БД списка патчей
     * @param patches - список патчей
     */
    public static void addListPatch(List<Patch> patches) {
        for (Patch patch : patches) {
            database.patchDAO().insertPatch(patch);
        }
    }

    /**
     * Добавление в БД лога
     * @param logList - лог, загружаемый в БД
     */
    public static void addLogs(LogList logList) {
         database.logListDAO().insertLogList(logList);
    }

    /**
     * Добавление в БД категории, если ее нет
     * @param log - категория, загружаетмая в БД
     */
    public static void addCategory(CategoryLog log) {
        CategoryLog categoryLog = database.categoryDAO().getById(log.getId());
        if (categoryLog == null)
            database.categoryDAO().insertCategory(log);
    }

    /**
     * Добавление в БД списка мелодий
     * @param orchestrions - список мелодий, загружаетмых в БД
     */
    public static void addListOrchestrion(List<Orchestrion> orchestrions) {
        for (Orchestrion orchestrion : orchestrions) {
            //try {
                addCategory(orchestrion.getCategory());
                database.orchestrionDAO().insertOrchestrion(orchestrion);
           /* } catch (Exception s) {
                Log.e("addOrchestrion", s.toString());
            }*/
        }
    }

    /**
     * Загрузка недостающих данных с сервера и запись из в БД
     * @param application - объект приложения для доспута к ресурсам
     * @param logLists - список логов, добавляемых в БД
     */
    public static void loadData(Application application, List<LogList> logLists) {
        List<LogList> logBD = getAllAsList();
        for (LogList logList : logLists) {
            if (!logBD.contains(logList)) {

                if (logList.getName().equals(application.getResources().getString(R.string.orchestrions))) {
                    List<Orchestrion> orchestrionList = new ArrayList<>();

                    try {// todo отсутвие дооступа к серверу
                        new OrchestrionAPIVolley(
                                application.getApplicationContext())
                                .getAllOrchestrion(orchestrionList);
                        while (orchestrionList.size() == 0) {
                            Thread.sleep(100);
                        }
                        TreeSet<String> iconCat = new TreeSet<>();
                        for (Orchestrion orchestrion : orchestrionList) {
                            iconCat.add(orchestrion.getCategory().getIcon());
                        }
                        for (String s1 : iconCat) {
                            if(s1!=null) {
                                new ImageAPIVolley(
                                        application.getApplicationContext())
                                        .getImageOrchestrion(s1.split("\\.")[0]);
                            }
                        }
                        // todo ожидание загрузки
                        new ImageAPIVolley(
                                application.getApplicationContext())
                                .getImageOrchestrion(orchestrionList.get(0).getIcon().split("\\.")[0]);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logList.setCount(orchestrionList.size());
                    logList.setIcon(orchestrionList.get(0).getIcon());
                    addLogs(logList);
                    addListOrchestrion(orchestrionList);
                }
            }
        }


    }
}
