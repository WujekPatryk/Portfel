package com.example.patryk.portfel.itemView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.patryk.portfel.R;
import com.example.patryk.portfel.data.Transaction;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.list_item)
public class TransactionItemView extends RelativeLayout {

    @ViewById
    TextView nazwa_transakcji;

    @ViewById
    TextView data_transakcji;

    @ViewById
    TextView wartosc_transakcji;

    @ViewById
    ImageView photo;

    public TransactionItemView(Context context) {
        super(context);
    }

    public void bind(Transaction transaction) {
            nazwa_transakcji.setText(transaction.nazwa_transakcji);
            data_transakcji.setText(transaction.data_transakcji);
            wartosc_transakcji.setText(transaction.wartosc_transakcji + " zł");
            checkWartoscTransakcji(transaction);
    }

    public void checkWartoscTransakcji(Transaction transaction){
        if (transaction.wartosc_transakcji<0) {
            photo.setImageResource(R.drawable.minus);
        } else {
            photo.setImageResource(R.drawable.plus);
        }
    }
}


