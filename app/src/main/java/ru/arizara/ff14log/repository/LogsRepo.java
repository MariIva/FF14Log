package ru.arizara.ff14log.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.arizara.ff14log.DB.AppDB;
import ru.arizara.ff14log.DB.LogListDAO;
import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.ui.log.entities.LogList;
/**
 * Синхронизация таблицы с логами и LiveData (список логов)
 */
public class LogsRepo {

    /** Доступ к запросам таблицы логов*/
    private LogListDAO logListDAO;

    /** Список логов*/
    private LiveData<List<LogList>> mAll;

    /**
     *
     */
    public LogsRepo(Application application) {
        AppDB db = LogsDB.getDB();
        logListDAO = db.logListDAO();
        mAll = logListDAO.getAll();
    }

    /**
     *
     */
    public LiveData<List<LogList>> getAll() {
        return mAll;
    }

    /**
     *
     */
    public void insert (LogList logList) {
        new insertAsyncTask(logListDAO).execute(logList);
    }

    /**
     *
     */
    private static class insertAsyncTask extends AsyncTask<LogList, Void, Void> {

        private LogListDAO mAsyncTaskDao;

        insertAsyncTask(LogListDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LogList... params) {
            mAsyncTaskDao.insertLogList(params[0]);
            return null;
        }
    }
}

