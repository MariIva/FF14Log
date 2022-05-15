package ru.arizara.ff14log.ui.log.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.databinding.FragmentLogBinding;
import ru.arizara.ff14log.ui.log.adapters.LogAdapter;
import ru.arizara.ff14log.ui.log.LogList;
import ru.arizara.ff14log.ui.log.viewModel.LogViewModel;

public class LogFragment extends Fragment {

    private LogViewModel logViewModel;
    private List<LogList> logLists;
    private FragmentLogBinding binding;
    //private  RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logViewModel = new ViewModelProvider(this).get(LogViewModel.class);

        binding = FragmentLogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        logLists = new ArrayList<>();

        //logLists.add(new LogList("qwadsefrdg"));

        //final TextView textView = binding.textHome;

         //root = inflater.inflate(R.layout.fragment_log,container, false);
        //logLiveData = LogViewModel.getInstance(getContext());


        logViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<LogList>>() {
            @Override
            public void onChanged(@Nullable List<LogList> list) {
                logLists.clear();
                logLists.addAll(list);

            }
        });

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_log);
        // создаем адаптер
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));
        LogAdapter adapter = new LogAdapter(getContext(), logLists);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);




        return root;
    }
}