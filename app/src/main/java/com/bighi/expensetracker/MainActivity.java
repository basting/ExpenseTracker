package com.bighi.expensetracker;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bighi.expensetracker.data.Expense;
import com.bighi.expensetracker.util.AppUtil;
import com.bighi.expensetracker.util.DateDialog;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Bastin Gomez
 * @since 20-Oct-15
 *
 * Activity which shows the form to create a new expense
 */
public class MainActivity extends AppCompatActivity {

    private EditText txtDateOfExpense;
    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtAmount;
    private TextView txtCurrency;
    private SharedPreferences.OnSharedPreferenceChangeListener onChange;
    private Firebase mFirebaseRef;
    private Button btnToday;
    private static final String DUMMY_USER = "darsanab";

    public static final String FIREBASE_URL = "https://expensetrackerbase.firebaseio.com/";

    private static final String TAG = MainActivity.class.toString();

    /*private static class FirebaseHolder {
        public static final Firebase INSTANCE = new Firebase(FIREBASE_URL).child("expenses");
    }

    public static Firebase getFirebaseInstance() {
        return FirebaseHolder.INSTANCE;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseRef = new Firebase(FIREBASE_URL).child("expenses");
        //mFirebaseRef = getFirebaseInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDateOfExpense = (EditText) findViewById(R.id.txtDOfExpense);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtCurrency = (TextView) findViewById(R.id.txtCurrency);
        btnToday = (Button) findViewById(R.id.btnToday);

        btnToday.performClick(); // auto-populate today's date
        txtDateOfExpense.setKeyListener(null);
        txtDateOfExpense.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(v);
                    showDateDialog(v);
                }
            }
        });
        txtDateOfExpense.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                showDateDialog(v);
            }
        });

        String currCurrency = AppUtil.getSelectedCurrency(getBaseContext());

        if (currCurrency == null) {
            currCurrency = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
        }

        txtCurrency.setText(currCurrency);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick to open ExpensesListActivity");
                Snackbar.make(view, "Opening the Expense list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent homeIntent = new Intent(getApplicationContext(), ExpensesListActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            }
        });

        setPreferenceChangeListener();
    }

    private void hideSoftKeyboard(View view) {
        if (view != null) {
            Log.i(TAG, "Hiding soft keyboard");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showDateDialog(View v) {
        Log.i(TAG, "Showing date dialog");
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
        Log.i(TAG, "Clicking Add button");
        if(!isReadyToSubmit()) {
            Log.i(TAG, "Is not ready to Submit");
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("New Expense")
                .setMessage("Add this expense?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addExpenseToFirebase();
                        Toast.makeText(getApplicationContext(), "Expense saved", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Not saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public boolean isReadyToSubmit() {
        if(txtTitle.getText().toString().length() == 0 ) {
            txtTitle.setError("Title is required!");
            return false;
        }
        if(txtAmount.getText().toString().length() == 0 ) {
            txtAmount.setError("Amount is required!");
            return false;
        }
        return true;
    }

    private void addExpenseToFirebase() {
        Log.i(TAG, "Adding expense to Firebase");
        String title = txtTitle.getText().toString();
        String description = txtDescription.getText().toString();
        String amount = txtAmount.getText().toString();
        String currencyCode = txtCurrency.getText().toString();

        DateFormat df = new SimpleDateFormat(DateDialog.MMM_DD_YYYY_EEE);
        Date expenseDate = null;
        try {
            expenseDate = df.parse(txtDateOfExpense.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //TODO: to change after the login screen is placed
        String userName = DUMMY_USER;

        // Create our 'model', an Expense object
        Expense expense = new Expense(title, description, amount,
                                            currencyCode, expenseDate, userName,
                                                TimeZone.getDefault().getID());
        // Create a new, auto-generated child of expenses, and save the expense data there
        mFirebaseRef.push().setValue(expense);
        clearAllFields(false);
    }

    /**
     * @param promptUser indicates whether to ask user for confirmation before clearing fields
     */
    public void clearAllFields(boolean promptUser) {
        Log.i(TAG, "Clearing all fields");
        if (promptUser) {
            new AlertDialog.Builder(this)
                    .setMessage("Clear the fields?")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            nullifyAllFields();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Not cleared", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else {
            nullifyAllFields();
        }

    }

    private void nullifyAllFields() {
        Log.i(TAG, "Clearing all fields");
        final String EMPTY = "";
        txtTitle.setText(EMPTY);
        txtAmount.setText(EMPTY);
        txtDescription.setText(EMPTY);
        btnToday.performClick(); //reset date means set to today's date
    }

    public void btnClearClick(View view) {
        clearAllFields(true);
    }

    public void btnTodayClick(View view) {
        txtDateOfExpense.setText(getTodaysDate());
    }

    public String getTodaysDate() {
        Calendar today = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(DateDialog.MMM_DD_YYYY_EEE);
        return df.format(today.getTime());
    }
}

