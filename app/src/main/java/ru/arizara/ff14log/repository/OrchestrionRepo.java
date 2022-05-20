package ru.arizara.ff14log.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.arizara.ff14log.DB.AppDB;
import ru.arizara.ff14log.DB.LogListDAO;
import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.DB.OrchestrionDAO;
import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.subEntities.OrchestrionWithCategory;

/**
 * Синхронизация таблицы с мелодиями и LiveData (список мелодий)
 */
public class OrchestrionRepo {
    /** Доступ к запросам таблицы логов*/
    private OrchestrionDAO orchestrionDAO;

    /** Список логов*/
    private LiveData<List<OrchestrionWithCategory>> mAll;

    /**
     *
     */
    public OrchestrionRepo(Application application) {
        AppDB db = LogsDB.getDB();
        orchestrionDAO = db.orchestrionDAO();
        mAll = orchestrionDAO.getAll();
    }
    /**
     *
     */
    public LiveData<List<OrchestrionWithCategory>> getAll() {
        return mAll;
    }


   /* public List<Orchestrion> getAllList() {
        return orchestrionDAO.getAllList();
    }*/
    /**
     *
     */
    public void update (Orchestrion orchestrion) {
        new OrchestrionRepo.updateAsyncTask(orchestrionDAO).execute(orchestrion);
    }

    /**
     *
     */
    private static class updateAsyncTask extends AsyncTask<Orchestrion, Void, Void> {

        private OrchestrionDAO mAsyncTaskDao;

        updateAsyncTask(OrchestrionDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Orchestrion... params) {
            mAsyncTaskDao.updateOrchestrion(params[0]);
            return null;
        }
    }
}
