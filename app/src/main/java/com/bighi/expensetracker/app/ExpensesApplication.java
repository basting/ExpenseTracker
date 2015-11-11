package com.bighi.expensetracker.app;

import com.firebase.client.Firebase;

/**
 * @author Bastin Gomez
 * @since 2015-11-10.
 */
public class ExpensesApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}