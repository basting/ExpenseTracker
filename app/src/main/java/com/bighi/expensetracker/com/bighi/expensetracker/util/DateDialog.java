package com.bighi.expensetracker.com.bighi.expensetracker.util;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * @author Bastin Gomez
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView dateTextView;

    /*public DateDialog() {

    }*/

    public void setView(View view) {
        dateTextView = (TextView)view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return(new DatePickerDialog(getActivity(), this, year, month, day));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
        dateTextView.setText(date);
    }
}
