package com.bighi.expensetracker.data;

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
    private String expenseTimeZoneId;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Expense() {
    }

    public Expense(String title,
                   String description,
                   String amount,
                   String currencyCode,
                   Date expenseDate,
                   String userName,
                   String expenseTimeZoneId) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.expenseDate = expenseDate;
        this.userName = userName;
        this.expenseTimeZoneId = expenseTimeZoneId;
    }

    @SuppressWarnings("unused")
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public String getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @SuppressWarnings("unused")
    public Date getExpenseDate() {
        return expenseDate;
    }

    @SuppressWarnings("unused")
    public String getUserName() {
        return userName;
    }

    @SuppressWarnings("unused")
    public String getExpenseTimeZoneId() {
        return expenseTimeZoneId;
    }
}
