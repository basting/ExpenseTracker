package com.bighi.expensetracker.com.bighi.expensetracker.data;

import java.util.Date;

/**
 * @author Bastin Gomez
 * @since 01-Nov-15
 */
public class Expense {
    private String title;
    private String description;
    private String amount;
    private String currencyCode;
    private Date expenseDate;
    private String userName;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Expense() {
    }

    public Expense(String title,
                   String description,
                   String amount,
                   String currencyCode,
                   Date expenseDate,
                   String userName) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.expenseDate = expenseDate;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public String getUserName() {
        return userName;
    }
}
