package com.example.patryk.portfel;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;
import com.example.patryk.portfel.data.UserList;
import com.example.patryk.portfel.data.Wallet;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

@EBean
public class RestBackgroundTask {

    @RootContext
    WalletActivity activity;

    @RestService
    WalletRestClient restClient;

    @Background
    void getWallet(User user) {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "aib-android");
            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);
            Wallet wallet = restClient.getWallet();
            if (user != null) {
                List<Integer> list = new ArrayList<Integer>();
                String userIds = null;//get unique IDs
                for (Transaction transaction : wallet.records) {
                    if (!list.contains(transaction.ownerId)) {
                        list.add(transaction.ownerId);
                        userIds += transaction.ownerId + ",";
                    }
                }

                userIds += 3 + ",";
                //String preparation
                String ids = userIds.substring(0, userIds.length()-1);

                //get
                restClient.setHeader("X-Dreamfactory-Application-Name", "aib-android");
                restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);
                UserList userList = restClient.getUserId(ids);
                for (Transaction transaction : wallet.records) {
                    for (int i = 0; i < userList.records.size(); i++) {
                        if (userList.records.get(i).id.equals(transaction.ownerId))
                            transaction.displayName = userList.records.get(i).displayName;
                    }
                }
                publishResult(wallet);
            }

            }
        catch (Exception e) {
            publishError(e);
        }
    }

    @UiThread
        void publishResult(Wallet wallet) {

        activity.update(wallet);
        }


    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }
}
