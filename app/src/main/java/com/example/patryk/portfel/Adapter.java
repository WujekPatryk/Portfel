package com.example.patryk.portfel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.patryk.portfel.data.Transaction;
import com.example.patryk.portfel.data.Wallet;
import com.example.patryk.portfel.itemView.TransactionItemView;
import com.example.patryk.portfel.itemView.TransactionItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Collections;
import java.util.List;

@EBean
public class Adapter extends BaseAdapter {

    private List<Transaction> lista;

    public void setList(List<Transaction> lista) {

        this.lista = lista;
    }

    @RootContext
    Context context;


    public Adapter() {
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Transaction getItem(int i) {
        return lista.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TransactionItemView transactionItemView;

        if (view == null) {
            transactionItemView = TransactionItemView_.build(context);
        } else {
            transactionItemView = (TransactionItemView) view;
        }

        transactionItemView.bind(getItem(i));

        return transactionItemView;

    }

    public void update(Wallet wallet) {

        lista.addAll(wallet.records);
        Collections.sort(lista);
        notifyDataSetChanged();

    }

}
