package alizarchik.alex.searchstat.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import alizarchik.alex.searchstat.R;

/**
 * Created by Александр on 07.01.2018.
 */

public class MainActivity extends AppCompatActivity {

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button buttonGeneralStat = findViewById(R.id.button_General_Stat);
        Button buttonDailyStat = findViewById(R.id.button_Daily_Stat);
        buttonGeneralStat.setOnClickListener(view -> startNewActivity(GeneralStatActivity.class));
        buttonDailyStat.setOnClickListener(view -> startNewActivity(DailyStatActivity.class));
    }

    private void startNewActivity(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        startActivity(intent);
    }
}



