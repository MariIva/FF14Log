package ru.arizara.ff14log.ui.log.fragments;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.arizara.ff14log.R;
//import ru.arizara.ff14log.databinding.FragmentLogBinding;
import ru.arizara.ff14log.databinding.OrchestrionsListFragmentBinding;
//import ru.arizara.ff14log.ui.log.adapters.LogAdapter;
//import ru.arizara.ff14log.ui.log.LogList;
//import ru.arizara.ff14log.ui.log.viewModel.LogViewModel;
import ru.arizara.ff14log.ui.log.adapters.OrchestrionsAdapter;
import ru.arizara.ff14log.ui.log.entities.Patch;
import ru.arizara.ff14log.ui.log.entities.subEntities.OrchestrionWithCategory;
import ru.arizara.ff14log.ui.log.viewModel.OrchestrionsListViewModel;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;

/**
 * Фрагмент для списка мелодий
 */
public class OrchestrionsListFragment extends Fragment {

    private OrchestrionsListFragmentBinding binding;
    private OrchestrionsListViewModel mViewModel;

    private List<OrchestrionWithCategory> orchestrionList;
    private String[] patches;
    private boolean[] checkedPatches;

    private RecyclerView recyclerView;
    private OrchestrionsAdapter adapter;

    private EditText editText;
    private ImageButton button;

    private List<String> patchNum;

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

        mViewModel.getDataList().observe(getViewLifecycleOwner(), new Observer<List<OrchestrionWithCategory>>() {
            @Override
            public void onChanged(@Nullable List<OrchestrionWithCategory> list) {
                adapter.addOrchestrion(list);
            }
        });

        mViewModel.getDataListOr().observe(getViewLifecycleOwner(), new Observer<List<OrchestrionWithCategory>>() {
            @Override
            public void onChanged(@Nullable List<OrchestrionWithCategory> list) {
            }
        });
        mViewModel.getDataPatches().observe(getViewLifecycleOwner(), new Observer<List<Patch>>() {
            @Override
            public void onChanged(List<Patch> list) {
                patches = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    patches[list.size()-i-1] = list.get(i).getName();
                }

                patchNum = new ArrayList<>(Arrays.asList(patches));
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
                                patchNum = new ArrayList<>();
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
                mViewModel.searchItemByName(s.toString(), patchNum);
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


}