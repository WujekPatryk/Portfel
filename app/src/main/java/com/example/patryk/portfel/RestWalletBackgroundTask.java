package com.example.patryk.portfel;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestWalletBackgroundTask {

    @RootContext
    AddActivity activity;

    @RestService
    WalletRestClient restClient;

    @Background
    void addWalletEntry(User user, Transaction transaction) {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "aib-android");
            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);
            restClient.addWalletEntry(transaction);
            publishResult(transaction);
        } catch (Exception e) {
            publishError(e);
        }
    }

    @UiThread
    void publishResult(Transaction transaction) {
        activity.confirmSuccess(transaction);
    }

    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }

}
