package com.example.patryk.portfel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.awt.font.TextAttribute;
import java.util.Calendar;

@EActivity(R.layout.activity_add)
public class AddActivity extends ActionBarActivity {

    @Bean
    @NonConfigurationInstance
    RestWalletBackgroundTask restWalletBackgroundTask;

    @Extra("User")
    User user;

    @Extra("Transaction")
    Transaction transaction;

    @ViewById
    EditText nazwa_transakcji;

    @ViewById
    EditText wartosc_transakcji;

    @ViewById
    EditText data_transakcji;

    @ViewById
    Button confirm;

//    @ViewById
//    Button data_transakcji;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Dodawanie przepisu...");
        ringProgressDialog.setIndeterminate(true);
    }

    @Click(R.id.confirm)
    void confirmClicked() {
        ringProgressDialog.show();

        if (user == null) {
            Intent addwallet = new Intent(this, LoginActivity_.class);
            startActivityForResult(addwallet, 1);
            Toast.makeText(this, "Musisz się najpierw zalogować!", Toast.LENGTH_LONG).show();
        }
        else {
            Transaction transaction = new Transaction();
            transaction.ownerId = user.id;
            transaction.nazwa_transakcji = nazwa_transakcji.getText().toString();
            transaction.data_transakcji = data_transakcji.getText().toString();
            transaction.wartosc_transakcji = Double.parseDouble(wartosc_transakcji.getText().toString());

            if ((nazwa_transakcji.length() == 0) || (wartosc_transakcji.length() == 0) || (data_transakcji.length() == 0)) {
                ringProgressDialog.dismiss();
                Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_LONG).show();
            } else {
                restWalletBackgroundTask.addWalletEntry(user, transaction);
                }
            }
        }
    public void confirmSuccess(Transaction transaction) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, "Dodano transakcję!", Toast.LENGTH_LONG).show();
        WalletActivity_.intent(this).transaction(transaction).user(user).start();
        this.finish();
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    @OnActivityResult(1)
    protected void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User)data.getSerializableExtra("user");
        }
    }

    public void onStart(){
        super.onStart();
        EditText data_transakcji = (EditText)findViewById(R.id.data_transakcji);
        data_transakcji.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }
}
