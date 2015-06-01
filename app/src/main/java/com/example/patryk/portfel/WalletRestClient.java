package com.example.patryk.portfel;

import com.example.patryk.portfel.data.EmailAndPassword;
import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.User;
import com.example.patryk.portfel.data.UserList;
import com.example.patryk.portfel.data.Wallet;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Rest(rootUrl = "https://dsp-patryk-tolodziecki-45894.cloud.dreamfactory.com:443/rest",
        converters = { MappingJackson2HttpMessageConverter.class })
@RequiresHeader({"X-Dreamfactory-Application-Name"})
public interface WalletRestClient extends RestClientHeaders {

    @Post("/user/register/?login=true")
    User register(EmailAndPassword emailAndPassword);

    @RequiresHeader({"X-Dreamfactory-Application-Name", "X-Dreamfactory-Session-Token"})
    @Get("/db/portfel/")
    Wallet getWallet();

    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name"})
    @Post("/db/portfel/")
    void addWalletEntry(Transaction transaction);

    @Delete("/db/portfel/{id_transakcji}")
    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name" })
    void deleteTransaction(Integer id_transakcji);

    @RequiresHeader({"X-Dreamfactory-Application-Name","X-Dreamfactory-Session-Token"})
    @Get("/system/user?ids={id}")
    UserList getUserId(String id);

    @Post("/user/session")
    User login(EmailAndPassword emailAndPassword);
}