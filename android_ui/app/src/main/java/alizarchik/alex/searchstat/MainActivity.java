package alizarchik.alex.searchstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by Александр on 07.01.2018.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buttonGS;
    private Button buttonDS;
    public static final String TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TokenStorage tokenStorage = TokenStorage.getInstance();
        if (tokenStorage.loadToken(this).isEmpty()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        Log.d(TAG, "token: " + tokenStorage.loadToken(this));
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonGS = findViewById(R.id.button_GS);
        buttonDS = findViewById(R.id.button_DS);
        buttonGS.setOnClickListener(view -> startNewActivity(GeneralStatActivity.class));
        buttonDS.setOnClickListener(view -> startNewActivity(DailyStatActivity.class));
    }

    private void startNewActivity(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        startActivity(intent);
    }
}



