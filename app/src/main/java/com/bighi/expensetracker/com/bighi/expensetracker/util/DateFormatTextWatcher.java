package com.bighi.expensetracker.com.bighi.expensetracker.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

/**
 * @since 20-Oct-15.
 * @author Bastin Gomez
 */
public class DateFormatTextWatcher implements TextWatcher{
    private String current = "";
    public final static String mmddyyyy = "MMM-dd, yyyy";
    private Calendar cal = Calendar.getInstance();
    private EditText dateText;
    private final String REGEX1 = "[^\\d.]";

    public DateFormatTextWatcher(EditText text){
        this.dateText = text;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            String clean = s.toString().replaceAll(REGEX1, "");
            String cleanC = current.replaceAll(REGEX1, "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            //Fix for pressing delete next to a forward slash
            if (clean.equals(cleanC)) sel--;

            if (clean.length() < 8){
                clean = clean + mmddyyyy.substring(clean.length());
            }else{
                //This part makes sure that when we finish entering numbers
                //the date is correct, fixing it otherwise
                int mon  = Integer.parseInt(clean.substring(0,2));
                int day  = Integer.parseInt(clean.substring(2,4));
                int year = Integer.parseInt(clean.substring(4,8));

                if(mon > 12) mon = 12;
                cal.set(Calendar.MONTH, mon-1);
                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                year = (year<1900)?1900:(year>2100)?2100:year;
                clean = String.format("%02d%02d%02d",mon, day, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clean;
            dateText.setText(current);
            dateText.setSelection(sel < current.length() ? sel : current.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
