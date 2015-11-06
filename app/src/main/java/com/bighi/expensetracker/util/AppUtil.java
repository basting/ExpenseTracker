package com.bighi.expensetracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Bastin Gomez
 * @since 22-Oct-15
 */
public class AppUtil {
    public static String getSelectedCurrency(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("currency_pref", "CAD");
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
