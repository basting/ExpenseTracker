package com.bighi.expensetracker.adapter;

import android.app.Activity;
import android.view.View;

import com.bighi.expensetracker.data.Expense;
import com.firebase.client.Query;

/**
 * @author BastinGomez
 * @since 2015-11-07.
 */
public class ExpensesListAdapter extends FirebaseListAdapter<Expense> {

    // The mUsername for this client. We use this to indicate which expenses originated from this user
    private String mUsername;

    public ExpensesListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Expense.class, layout, activity);
        this.mUsername = mUsername;
    }

    @Override
    protected void populateView(View v, Expense model) {

    }
}
