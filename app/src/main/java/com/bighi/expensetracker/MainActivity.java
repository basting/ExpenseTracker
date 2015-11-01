package com.bighi.expensetracker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bighi.expensetracker.com.bighi.expensetracker.util.AppUtil;
import com.bighi.expensetracker.com.bighi.expensetracker.util.DateDialog;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity{

    private EditText txtDateOfExpense;
    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtAmount;
    private TextView txtCurrency;
    private SharedPreferences.OnSharedPreferenceChangeListener onChange;
    private Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firebase setup code
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://expensetrackerbase.firebaseio.com/");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDateOfExpense = (EditText) findViewById(R.id.txtDateOfExpense);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtCurrency = (TextView) findViewById(R.id.txtCurrency);

        txtDateOfExpense.setKeyListener(null);
        txtDateOfExpense.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDateDialog(v);
                }
            }
        });
        txtDateOfExpense.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDateDialog(v);
            }
        });

        String currCurrency = AppUtil.getSelectedCurrency(getBaseContext());

        if(currCurrency == null) {
            currCurrency = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
        }

        txtCurrency.setText(currCurrency);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setPreferenceChangeListener();
    }

    private void showDateDialog(View v) {
        DateDialog dialog = new DateDialog();
        dialog.setView(v);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, "DatePicker");
    }

    private void setPreferenceChangeListener() {
        if (onChange == null) {
            onChange = new SharedPreferences.OnSharedPreferenceChangeListener() {

                @Override
                public void onSharedPreferenceChanged(SharedPreferences sp, String s) {
                    // TODO: to convert short currency code to full description
                    String currShort = sp.getString("currency_pref", "CAD");
                    txtCurrency.setText(currShort);
                }
            };
        }
        SharedPreferences sp = AppUtil.getSharedPreferences(getBaseContext());
        sp.registerOnSharedPreferenceChangeListener(onChange);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnAddClick(View view) {
        Toast.makeText(getApplicationContext(), txtDescription.getText() + ":" + txtAmount.getText(), Toast.LENGTH_LONG).show();
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Go ahead and add expense", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "cancelled action", Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }*/

    public void btnClearClick(View view) {
        final String EMPTY = "";
        txtDateOfExpense.setText(EMPTY);
        txtTitle.setText(EMPTY);
        txtAmount.setText(EMPTY);
        txtDescription.setText(EMPTY);
    }

    public void btnTodayClick(View view) {
        txtDateOfExpense.setText(getTodaysDate());
    }

    public String getTodaysDate() {
        Calendar today = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(DateDialog.MMM_DD_YYYY_EEE);
        String todayStr = df.format(today.getTime());
        return todayStr;
    }
}

