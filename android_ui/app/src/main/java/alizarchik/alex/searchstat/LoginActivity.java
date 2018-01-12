package alizarchik.alex.searchstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button signUpButton;
    private ImageView logoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();


        logoView.setOnClickListener(view -> startNewActivity(MainActivity.class));
        signUpButton.setOnClickListener(view -> startNewActivity(SignUpActivity.class));


    }

    private void initView() {
        logoView = findViewById(R.id.logoView);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);
    }

    private void startNewActivity(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        startActivity(intent);
    }
}
