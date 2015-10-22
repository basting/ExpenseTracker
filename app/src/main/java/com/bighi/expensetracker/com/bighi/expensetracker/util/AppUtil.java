package com.bighi.expensetracker.com.bighi.expensetracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Bastin Gomez
 * */
public class AppUtil {
    public static String getSelectedCurrency(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("currency_pref","CAD");
    }
}
