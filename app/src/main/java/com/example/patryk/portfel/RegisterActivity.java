package com.example.patryk.portfel;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patryk.portfel.data.EmailAndPassword;
import com.example.patryk.portfel.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends ActionBarActivity {

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    @ViewById
    EditText first_name;

    @ViewById
    EditText last_name;

    @Bean
    @NonConfigurationInstance
    RestRegisterBackgroundTask restRegisterBackgroundTask;

    ProgressDialog ringProgressDialog;

    @Click(R.id.register2)
    void register2Clicked() {
        ringProgressDialog.show();
        EmailAndPassword emailAndPassword = new EmailAndPassword();
        emailAndPassword.email = email.getText().toString();
        emailAndPassword.password = password.getText().toString();
        emailAndPassword.first_name = first_name.getText().toString();
        emailAndPassword.last_name = last_name.getText().toString();

        if (email.getText().length()==0||password.getText().length()==0||first_name.getText().length()==0||
                last_name.getText().length()==0)
        {
            ringProgressDialog.dismiss();
            Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_LONG).show();
        }
        else
        {
            restRegisterBackgroundTask.register(emailAndPassword);}
    }

    @AfterViews
    void init() {
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Rejestrowanie...");
        ringProgressDialog.setIndeterminate(true);
    }

    public void registerSuccess(User user) {
        ringProgressDialog.dismiss();
        AddActivity_.intent(this).user(user).start();
        Toast.makeText(this, "Zarejestrowano!", Toast.LENGTH_LONG).show();
        this.finish();
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }
}
