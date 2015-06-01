package com.example.patryk.portfel;

import com.example.patryk.portfel.data.EmailAndPassword;
import com.example.patryk.portfel.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestRegisterBackgroundTask {

    @RootContext
    RegisterActivity activity;

    @RestService
    WalletRestClient restClient;

    @Background
    void register(EmailAndPassword emailAndPassword) {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "aib-android");
            User user = restClient.register(emailAndPassword);
            publishResult(user);
        } catch (Exception e) {
            publishError(e);
        }
    }

    @UiThread
    void publishResult(User user) {
        activity.registerSuccess(user);
    }

    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }

}