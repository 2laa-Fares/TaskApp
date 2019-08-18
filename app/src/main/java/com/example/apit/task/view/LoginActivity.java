package com.example.apit.task.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.presenter.LoginPresenter;
import com.example.apit.task.repositories.imp.LoginRepositoryImp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity  extends AppCompatActivity implements LoginInterface{

    private static final String TAG = "LOGIN";
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    AppCompatButton login;
    @BindView(R.id.root_view)
    ConstraintLayout rootView;
    ProgressDialog progressDialog;
    LoginPresenter loginPresenter;
    LoginInterface loginInterface;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        App.setContext(this);

        loginInterface = this;
        loginPresenter = new LoginPresenter(loginInterface, new LoginRepositoryImp());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        login.setEnabled(false);
        progressDialog= new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تسجيل الدخول...");
        progressDialog.show();
        String username = userName.getText().toString();
        String pass = password.getText().toString();
        try {
            loginPresenter.loginUser(URLEncoder.encode(username, "utf-8"), pass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public boolean validate() {
        boolean valid = true;
        String name = userName.getText().toString();
        String pass = password.getText().toString();
        if (name.isEmpty()) {
            userName.setError("من فضلك ادخل الاسم");
            valid = false;
        } else {
            userName.setError(null);
        }
        if (pass.isEmpty()) {
            password.setError("من فضلك ادخل البسورد");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    public void onLoginSuccess() {
        login.setEnabled(true);
    }

    public void onLoginFailed() {
        login.setEnabled(true);
    }

    @Override
    public void RetrieveUser(String user) {
        progressDialog.dismiss();
        this.user = user;
        onLoginSuccess();
        Intent i = new Intent(LoginActivity.this, TasksActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void NotUserFound(String error) {
        progressDialog.dismiss();
        onLoginFailed();
        Snackbar.make(rootView, error, Snackbar.LENGTH_LONG).show();
    }



}

