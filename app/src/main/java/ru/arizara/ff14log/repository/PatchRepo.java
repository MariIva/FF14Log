package ru.arizara.ff14log.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.arizara.ff14log.DB.AppDB;
import ru.arizara.ff14log.DB.LogListDAO;
import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.DB.PatchDAO;
import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Patch;

/**
 * Синхронизация таблицы с
 */
public class PatchRepo {

    /** Доступ к запросам таблицы логов*/
    private PatchDAO patchDAO;

    /** Список логов*/
    private LiveData<List<Patch>> mAll;

    /**
     *
     */
    public PatchRepo(Application application) {
        AppDB db = LogsDB.getDB();
        patchDAO = db.patchDAO();
        mAll = patchDAO.getAll();
    }

    /**
     *
     */
    public LiveData<List<Patch>> getAll() {
        return mAll;
    }

    /**
     *
     */
    public void insert (Patch patch) {
        new insertAsyncTask(patchDAO).execute(patch);
    }

    /**
     *
     */
    private static class insertAsyncTask extends AsyncTask<Patch, Void, Void> {

        private PatchDAO mAsyncTaskDao;

        insertAsyncTask(PatchDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Patch... params) {
            mAsyncTaskDao.insertPatch(params[0]);
            return null;
        }
    }
}

