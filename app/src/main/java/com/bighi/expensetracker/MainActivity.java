package com.bighi.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bighi.expensetracker.com.bighi.expensetracker.util.DateFormatTextWatcher;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText txtDateOfExpense;
    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtAmount;
    private TextView txtCurrency;
    private final String EMPTY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        txtDateOfExpense = (EditText) findViewById(R.id.txtDateOfExpense);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtCurrency = (TextView) findViewById(R.id.txtCurrency);


        EditText editTextDoExp = txtDateOfExpense;
        editTextDoExp.addTextChangedListener(new DateFormatTextWatcher(editTextDoExp));

        txtCurrency.setText(NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    public void btnClearClick(View view) {
        txtDateOfExpense.setText(EMPTY);
        txtTitle.setText(EMPTY);
        txtAmount.setText(EMPTY);
        txtDescription.setText(EMPTY);
    }
}
