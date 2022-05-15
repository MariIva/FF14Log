package ru.arizara.ff14log.ui.log.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

    private List<Orchestrion> orchestrionList;
    private String[] patches;
    private boolean[] checkedPatches;

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

        mViewModel.getDataList().observe(getViewLifecycleOwner(), new Observer<List<Orchestrion>>() {
            @Override
            public void onChanged(@Nullable List<Orchestrion> list) {
                orchestrionList.clear();
                orchestrionList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
        mViewModel.getDataPatches().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] list) {
                patches = list;
            }
        });
        mViewModel.getDataCheckedPatches().observe(getViewLifecycleOwner(), new Observer<boolean[]>() {
            @Override
            public void onChanged(@Nullable boolean[] list) {
                checkedPatches = list;
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Choose patches")
                        .setMultiChoiceItems(patches,checkedPatches,
                                new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                mViewModel.checkPatches(which,isChecked);
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<String> patchNum = new ArrayList<>();
                                for (int i1 = 0; i1 < checkedPatches.length; i1++) {
                                    if(checkedPatches[i1]){
                                        patchNum.add(checkedPatches.length-i1+1+".");
                                    }
                                }
                                mViewModel.searchItemByPatch(patchNum);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.decline), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.searchItemByName(s.toString());
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