package com.example.patryk.portfel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;
import com.example.patryk.portfel.Adapter;
import com.example.patryk.portfel.data.Wallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EActivity(R.layout.activity_wallet)
public class WalletActivity extends ActionBarActivity {

    @Extra("Transaction")
    Transaction transaction = new Transaction();

    @Extra("User")
    User user = new User();

    @ViewById
    TextView srodki;

    @ViewById
    Button add;

    @ViewById
    Button delete;

    @ViewById
    ListView listaoperacji;

    @Bean
    Adapter adapter;

    @Bean
    @NonConfigurationInstance
    RestBackgroundTask restBackgroundTask;

    private ProgressDialog ringProgressDialog;

    private List<Transaction> lista;

    @AfterViews
    void init() {
        lista = new ArrayList<Transaction>();
        adapter.setList(lista);
        listaoperacji.setAdapter(adapter);
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Ładowanie listy transakcji...");
        ringProgressDialog.setIndeterminate(true);
        restBackgroundTask.getWallet(user);
    }

    public void update(Wallet wallet) {
        ringProgressDialog.dismiss();
        Collections.reverse(lista);
        adapter.update(wallet);
        List<Double> wartosci = new ArrayList<Double>();
        double saldo = 0;//get unique IDs
        for (Transaction transaction : wallet.records) {
            while (!wartosci.contains(transaction.wartosc_transakcji)) {
                wartosci.add(transaction.wartosc_transakcji);
                saldo += transaction.wartosc_transakcji;
            }
        }
        srodki.setText(saldo + " zł");
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    @Click(R.id.add)
    void addClicked(){
        AddActivity_.intent(this).user(user).start();
    }

    @ItemClick
    void listaoperacjiItemClicked(Transaction transaction){
        TransactionActivity_.intent(this).user(user).transaction(transaction).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 0) {
                user = (User) data.getSerializableExtra("User");
            }
        }
    }
}
