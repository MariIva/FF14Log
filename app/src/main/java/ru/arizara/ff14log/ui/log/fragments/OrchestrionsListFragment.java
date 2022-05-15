package ru.arizara.ff14log.ui.log.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

    private OrchestrionsListFragmentBinding binding;
    private OrchestrionsListViewModel mViewModel;
    private LiveData<List<Orchestrion>> liveData;

    private List<Orchestrion> orchestrionList;

    private RecyclerView recyclerView;
    private OrchestrionsAdapter adapter;

    private EditText editText;
    private ImageButton button;

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
        editText = ((TextInputLayout) root.findViewById(R.id.tl_find)).getEditText();
        button = (ImageButton) root.findViewById(R.id.imgbtn_filter);

        initRecyclerView();

        liveData =  mViewModel.getData();
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Orchestrion>>() {
            @Override
            public void onChanged(@Nullable List<Orchestrion> list) {
                orchestrionList.clear();
                orchestrionList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.searchItem(s.toString());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        return root;
    }

    private void initRecyclerView(){
        // создаем адаптер
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new OrchestrionsAdapter(getContext(), orchestrionList);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
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