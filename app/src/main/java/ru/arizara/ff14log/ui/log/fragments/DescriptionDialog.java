package ru.arizara.ff14log.ui.log.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
/**
 * Диалог описания мелодии
 */
public class DescriptionDialog extends DialogFragment {

    public static final String TAG = "DescriptionDialog";

    private Toolbar toolbar;
    private TextView tv_patch;
    private TextView tv_category;
    private TextView tv_description;
    private ImageView img_category;

    private Orchestrion orchestrion;

    public static DescriptionDialog display(FragmentManager fragmentManager, Orchestrion orchestrion) {
        DescriptionDialog descriptionDialog = new DescriptionDialog();
        descriptionDialog.setOrchestrion(orchestrion);
        descriptionDialog.show(fragmentManager, TAG);

        return descriptionDialog;
    }

    public void setOrchestrion(Orchestrion orchestrion) {
        this.orchestrion = orchestrion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_description, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tv_patch = (TextView) view.findViewById(R.id.tv_patch);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        img_category =  (ImageView) view.findViewById(R.id.img_location2);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_patch.setText(orchestrion.getPatch());
        tv_category.setText(orchestrion.getCategory().getName());
        tv_description.setText(orchestrion.getDescription());

        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(orchestrion.getName());
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
