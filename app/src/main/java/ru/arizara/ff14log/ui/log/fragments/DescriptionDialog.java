package ru.arizara.ff14log.ui.log.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.subEntities.OrchestrionWithCategory;

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

    private OrchestrionWithCategory orchestrion;

    public static DescriptionDialog display(FragmentManager fragmentManager, OrchestrionWithCategory orchestrion) {
        DescriptionDialog descriptionDialog = new DescriptionDialog();
        descriptionDialog.setOrchestrion(orchestrion);
        descriptionDialog.show(fragmentManager, TAG);

        return descriptionDialog;
    }

    public void setOrchestrion(OrchestrionWithCategory orchestrion) {
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

        tv_patch.setText(orchestrion.getOrchestrion().getPatch());
        tv_description.setText(orchestrion.getOrchestrion().getDescription());
        tv_category.setText(orchestrion.getCategoryLog().get(0).getName());

        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = getActivity().openFileInput(orchestrion.getCategoryLog().get(0).getIcon());
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(bitmap!=null) {
            img_category.setImageBitmap(bitmap);
        }

        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(orchestrion.getOrchestrion().getName());
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
