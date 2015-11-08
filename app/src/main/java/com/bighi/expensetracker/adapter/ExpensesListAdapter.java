package com.bighi.expensetracker.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bighi.expensetracker.R;
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
    protected void populateView(View view, Expense expense) {
        // Map a Expense object to an entry in our listview
        String userName = expense.getUserName();
        TextView userNameText = (TextView) view.findViewById(R.id.userExpenseItem);
        userNameText.setText(userName + ": ");
        // If the message was sent by this user, color it differently
        if (userName != null && userName.equals(mUsername)) {
            userNameText.setTextColor(Color.RED);
        } else {
            userNameText.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.titleExpenseItem)).setText(expense.getTitle());
        ((TextView) view.findViewById(R.id.amountExpenseItem)).setText(expense.getAmount());
    }
}
