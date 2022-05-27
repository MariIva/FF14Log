package ru.arizara.ff14log.ui.setting;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.arizara.ff14log.R;

public class SetFragment extends Fragment {

    SharedPreferences prefs;
    SettingFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set, container, false);

        fragment = new SettingFragment();
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listFragment, fragment)
                    .commit();
        }


        return view;

    }


    public static class SettingFragment extends PreferenceFragmentCompat {
        /*SwitchPreferenceCompat switchPreference1;
        SwitchPreferenceCompat switchPreference2;
        SwitchPreferenceCompat switchPreference3;
        SwitchPreferenceCompat switchPreference4;
        SwitchPreferenceCompat switchPreference5;*/

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            final SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity());
           /* switchPreference1 = (SwitchPreferenceCompat) findPreference("endwalker");
            switchPreference2 = (SwitchPreferenceCompat) findPreference("shadowbringers");
            switchPreference3 = (SwitchPreferenceCompat) findPreference("stormblood");
            switchPreference4 = (SwitchPreferenceCompat) findPreference("heavensward");
            switchPreference5 = (SwitchPreferenceCompat) findPreference("reborn");

            preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("endwalker")) {
                        boolean choose = sharedPreferences.getBoolean("endwalker", false);
                        if (choose) {
                            sharedPreferences.edit().putBoolean("shadowbringers", true).apply();
                            switchPreference2.setChecked(true);
                        } else {

                        }
                    }
                    if (key.equals("shadowbringers")) {
                        boolean choose = sharedPreferences.getBoolean("shadowbringers", false);
                        if (choose) {
                            sharedPreferences.edit().putBoolean("stormblood", true).apply();
                            switchPreference3.setChecked(true);
                        } else {
                            sharedPreferences.edit().putBoolean("endwalker", false).apply();
                            switchPreference1.setChecked(false);
                        }
                    }
                    if (key.equals("stormblood")) {
                        boolean choose = sharedPreferences.getBoolean("stormblood", false);
                        if (choose) {
                            sharedPreferences.edit().putBoolean("heavensward", true).apply();
                            switchPreference4.setChecked(true);

                        } else {
                            sharedPreferences.edit().putBoolean("shadowbringers", false).apply();
                            switchPreference2.setChecked(false);
                        }
                    }
                    if (key.equals("heavensward")) {
                        boolean choose = sharedPreferences.getBoolean("heavensward", false);
                        if (choose) {
                            sharedPreferences.edit().putBoolean("reborn", true).apply();
                            switchPreference5.setChecked(true);
                        } else {
                            sharedPreferences.edit().putBoolean("stormblood", false).apply();
                            switchPreference3.setChecked(false);
                        }
                    }
                    if (key.equals("reborn")) {
                        boolean choose = sharedPreferences.getBoolean("reborn", false);
                        if (choose) {

                        } else {
                            sharedPreferences.edit().putBoolean("heavensward", false).apply();
                            switchPreference4.setChecked(false);
                        }
                    }
                }
            });*/
        }


    }

}