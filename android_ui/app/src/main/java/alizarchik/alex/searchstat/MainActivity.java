package alizarchik.alex.searchstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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



