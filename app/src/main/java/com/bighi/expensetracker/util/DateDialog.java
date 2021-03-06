package com.bighi.expensetracker.util;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Bastin Gomez
 * @since 29-Oct-15
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;
    public static final String MMM_DD_YYYY_EEE = "MMM-dd, yyyy (EEE)";

    public void setView(View view) {
        dateTextView = (TextView) view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return (new DatePickerDialog(getActivity(), this, year, month, day));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        //String date = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
        DateFormat df = new SimpleDateFormat(MMM_DD_YYYY_EEE);
        String todayStr = df.format(cal.getTime());
        dateTextView.setText(todayStr);
    }

}
