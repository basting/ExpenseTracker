package com.bighi.expensetracker;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.bighi.expensetracker.adapter.ExpensesListAdapter;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

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
    private ListView listViewExpenses;
    private ExpensesListAdapter mExpenseListAdapter;
    private ValueEventListener mConnectedListener;

    private static final String TAG = ExpensesListActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseRef = new Firebase(MainActivity.FIREBASE_URL).child("expenses");
        mFirebaseRef.keepSynced(true); // keep the expenses list in sync

        setContentView(R.layout.activity_expenses_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewExpenses = (ListView) findViewById(R.id.listViewExpenses);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Opening Expense creation window", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = listViewExpenses;
        // Tell our list adapter that we only want 50 messages at a time
        mExpenseListAdapter = new ExpensesListAdapter(mFirebaseRef.limitToFirst(50), this,
                                            R.layout.expense_item, "darsanab");
        listView.setAdapter(mExpenseListAdapter);
        mExpenseListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mExpenseListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        /*mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
                    return;
                }
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ExpensesListActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExpensesListActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });*/
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "Removing listener");
        mFirebaseRef.getRoot().child("expenses").removeEventListener(mConnectedListener);
        mExpenseListAdapter.cleanup();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
