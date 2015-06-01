package com.example.patryk.portfel;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestDeleteBackgroundTask
{
    @RootContext
    TransactionActivity activity;

    @RestService
    WalletRestClient restClient;

    @Background
    public void delTransaction(User user,Transaction transaction){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name","cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token",user.sessionId);
            restClient.deleteTransaction(transaction.id_transakcji);
            publishResult();
        }
        catch(Exception e){
            publishError(e);
        }
    }

    @UiThread
    void publishResult(){
        activity.deleteSuccess();
    }

    @UiThread
    void publishError(Exception e){
        activity.showError(e);
    }


}

