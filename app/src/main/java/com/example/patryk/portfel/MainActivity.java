package com.example.patryk.portfel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import com.example.patryk.portfel.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @Extra("User")
    User user;

    @ViewById
    Button wallet;

    @Click(R.id.wallet)
    public void walletClicked() {
        if (user == null) {
            Intent enterwallet = new Intent(this, LoginActivity_.class);
            startActivityForResult(enterwallet, 1);
        } else {
            WalletActivity_.intent(this).user(user).start();
            finish();
        }
    }

    @OnActivityResult(1)
    protected void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User)data.getSerializableExtra("user");
        }
    }
}
