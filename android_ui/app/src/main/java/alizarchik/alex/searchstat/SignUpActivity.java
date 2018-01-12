package alizarchik.alex.searchstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by aoalizarchik.
 */

public class SignUpActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private EditText login;
    private EditText password;
    private EditText password2;
    private EditText email;
    private ImageView logoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        logoView.setOnClickListener(view -> startNewActivity(MainActivity.class));

    }

    private void initView() {
        logoView = findViewById(R.id.logoView);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password_2);
        email = findViewById(R.id.email);
    }

    private void startNewActivity(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        startActivity(intent);
    }

}
