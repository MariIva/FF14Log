package ru.arizara.ff14log.ui.log.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.arizara.ff14log.R;
//import ru.arizara.ff14log.databinding.FragmentLogBinding;
import ru.arizara.ff14log.databinding.OrchestrionsListFragmentBinding;
//import ru.arizara.ff14log.ui.log.adapters.LogAdapter;
//import ru.arizara.ff14log.ui.log.LogList;
//import ru.arizara.ff14log.ui.log.viewModel.LogViewModel;
import ru.arizara.ff14log.ui.log.adapters.OrchestrionsAdapter;
import ru.arizara.ff14log.ui.log.viewModel.OrchestrionsListViewModel;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;

public class OrchestrionsListFragment extends Fragment {

    private OrchestrionsListViewModel mViewModel;
    private List<Orchestrion> orchestrionList;
    private OrchestrionsListFragmentBinding binding;
    private  RecyclerView recyclerView;
    private OrchestrionsAdapter adapter;

    public static OrchestrionsListFragment newInstance() {
        return new OrchestrionsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.orchestrions_list_fragment, container, false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar()
                .setTitle(getActivity().getApplication().getString(R.string.orchestrions));

        mViewModel = new ViewModelProvider(this).get(OrchestrionsListViewModel.class);

        binding = OrchestrionsListFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        orchestrionList = new ArrayList<>();


        recyclerView = (RecyclerView) root.findViewById(R.id.rv_orchestrions);
        // создаем адаптер
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new OrchestrionsAdapter(getContext(), orchestrionList);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Orchestrion>>() {
            @Override
            public void onChanged(@Nullable List<Orchestrion> list) {
                orchestrionList.clear();
                orchestrionList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });


        return root;

        //return view;
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrchestrionsListViewModel.class);
        // TODO: Use the ViewModel
    }
*/
}