package ru.arizara.ff14log;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.databinding.ActivityMainBinding;
import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Patch;
import ru.arizara.ff14log.ui.log.rest.ImageAPIVolley;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SharedPreferences prefs = null;
    private boolean settingFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LogsDB(this);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //new ImageAPIVolley(getApplicationContext()).getImageOrchestrion();
                /*LiveData<List<LogList>> p = LogsDB.getAllLogs();
                List<LogList> d = p.getValue();*/
                //List<Orchestrion> p = LogsDB.getAllOrchestrion();
                //LogsDB.addLogs(new LogList(getResources().getString(R.string.orchestrions),0,0));
                //patches = LogsDB.getAllLogsg();
                //patches = LogsDB.getAllLogsg();
            }
        }).start();


        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_log, R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        // движение фрагментов (закрытие настроек)
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.e("onDestinationChanged",destination.getLabel().toString() );
                switch (destination.getLabel().toString()){
                    case "Setting":
                        settingFlag = true;
                        break;
                    case "Logs":
                        if(settingFlag){
                            settingFlag = false;

/*
                            List<Patch> patches = new ArrayList<>();
                            patches.add(new Patch(6, getResources().getString(R.string.endwalker), prefs.getBoolean("endwalker", false)));
                            patches.add(new Patch(5, getResources().getString(R.string.shadowbringers), prefs.getBoolean("shadowbringers", false)));
                            patches.add(new Patch(4, getResources().getString(R.string.stormblood), prefs.getBoolean("stormblood", false)));
                            patches.add(new Patch(3, getResources().getString(R.string.heavensward), prefs.getBoolean("heavensward", false)));
                            patches.add(new Patch(2, getResources().getString(R.string.reborn), prefs.getBoolean("reborn", false)));
*/
                            // включение логов
                            List<LogList> logLists = new ArrayList<>();
                            if (prefs.getBoolean("orch", false)) {
                                logLists.add(new LogList(getResources().getString(R.string.orchestrions),0,0));
                            }
                            //обновление БД

                            //todo возможно вернуть для progress bar
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    LogsDB.loadData(getApplication(), logLists);
                                }
                            });
                            thread.start();
                            /*new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    LogsDB.addLogs(new LogList(getResources().getString(R.string.orchestrions),0,0));

                                }
                            }).start();*/
                        }
                        break;
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // при первом запуске заполняется таблица патчей
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).apply();
            //todo создание БД
            List<Patch> patches = new ArrayList<>();
            patches.add(new Patch(6, getResources().getString(R.string.endwalker), false));
            patches.add(new Patch(5, getResources().getString(R.string.shadowbringers), false));
            patches.add(new Patch(4, getResources().getString(R.string.stormblood), false));
            patches.add(new Patch(3, getResources().getString(R.string.heavensward), false));
            patches.add(new Patch(2, getResources().getString(R.string.reborn), false));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    LogsDB.addListPatch(patches);
                }
            });
            thread.start();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}