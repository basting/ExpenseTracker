package com.bighi.expensetracker;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * @author Bastin Gomez
 * @since 20-Oct-15
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
