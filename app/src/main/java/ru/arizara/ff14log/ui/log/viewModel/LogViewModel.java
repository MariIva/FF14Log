package ru.arizara.ff14log.ui.log.viewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.R;
import ru.arizara.ff14log.repository.LogsRepo;
import ru.arizara.ff14log.ui.log.entities.LogList;

import androidx.lifecycle.Observer;

public class LogViewModel extends AndroidViewModel {

    private LogsRepo repo;

    private LiveData<List<LogList>> rvLogs;

    public LogViewModel(@NonNull Application application) {
        super(application);
        repo = new LogsRepo(application);
        rvLogs = repo.getAll();
    }

    public LiveData<List<LogList>> getData() {
        return rvLogs;
    }

    public void insert(LogList logList) { repo.insert(logList); }



}