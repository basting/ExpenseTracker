package com.bighi.expensetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.firebase.client.Firebase;

/**
 * @author Bastin Gomez
 * @since 01-Nov-15
 */

/*
 * TODO:
 *  * Login screen
 *  * List all expenses (all, daily, weekly, monthly, custom)
 *  * Email with various types of reports
 *  * Send SMS to add to expenses (future)
 *  * Item wise reports (future)
 *  * Website for accessing expenses (future)
 */
public class ExpensesListActivity extends AppCompatActivity {

    private Firebase mFirebaseRef;
    ListView listViewExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseRef = MainActivity.getFirebaseInstance();

        setContentView(R.layout.activity_expenses_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewExpenses = (ListView) findViewById(R.id.listViewExpenses);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
