package com.example.patryk.portfel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;
import com.example.patryk.portfel.data.Wallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_transaction)
public class TransactionActivity extends ActionBarActivity {

    @Extra("Transaction")
    Transaction transaction = new Transaction();

    @Extra("User")
    User user = new User();

    @Bean
    @NonConfigurationInstance
    RestDeleteBackgroundTask restDeleteBackgroundTask;

    @ViewById
    TextView wartosc_transakcji;

    @ViewById
    TextView nazwa_transakcji;

    @ViewById
    TextView opis_transakcji;

    @ViewById
    TextView data_transakcji;

    @ViewById
    Button delete;

    @ViewById
    Button add;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init (){
        nazwa_transakcji.setText(transaction.nazwa_transakcji);
        opis_transakcji.setText(transaction.opis_transakcji);
        data_transakcji.setText(transaction.data_transakcji);
        wartosc_transakcji.setText(String.valueOf(transaction.wartosc_transakcji)+"zł");
    }

    @Click(R.id.delete)
    void deleteTransaction() {
        if(transaction.ownerId == user.id){
        ringProgressDialog.show();
        restDeleteBackgroundTask.delTransaction(user, transaction);
        ringProgressDialog.setMessage("Trwa usuwanie..."); }
        else {
            Toast.makeText(this, "Nie można usunąć transakcji", Toast.LENGTH_LONG).show();
        }
    }


    @Click(R.id.add)
    void addClicked(){
        AddActivity_.intent(this).user(user).start();
        finish();
    }

    public void deleteSuccess() {
        ringProgressDialog.dismiss();
        WalletActivity_.intent(this).extra("User", user).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();
        finish();
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @OnActivityResult(1)
    protected void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User)data.getSerializableExtra("user");
        }
    }
}
