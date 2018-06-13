package alizarchik.alex.searchstat.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import alizarchik.alex.searchstat.R;
import alizarchik.alex.searchstat.presenter.ISignUpPresenter;
import alizarchik.alex.searchstat.presenter.impl.SignUpPresenter;
import alizarchik.alex.searchstat.view.ISignUpView;

/**
 * Created by aoalizarchik.
 */

public class SignUpActivity extends AppCompatActivity implements ISignUpView{

    private ISignUpPresenter presenter;
    private EditText login;
    private EditText password;
    private EditText password2;
    private EditText email;
    private Button buttonSignUp;
    private ProgressBar progressBar;
    public static final String TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        buttonSignUp.setOnClickListener((v) -> onClick());

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            if (presenter == null) {
                presenter = new SignUpPresenter(this);
            }
        } else {
            showNoConnectionMessage();
        }
    }

    private void initView() {
        buttonSignUp = findViewById(R.id.sign_up_button_RS);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password_2);
        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar_Registration_Screen);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat
                .getColor(this, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
    }

    public void onClick() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.registration(login.getText().toString(), password.getText().toString(),
                password2.getText().toString(), email.getText().toString());
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionToTheServer() {
        Log.d(TAG, "Hет соединения с сервером");
        Toast.makeText(SignUpActivity.this, R.string.no_connection_to_the_server, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoConnectionMessage() {
        Log.d(TAG, "Подключите интернет");
        Toast.makeText(SignUpActivity.this, R.string.turn_on_internet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(SignUpActivity.this, R.string.enter_password_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
