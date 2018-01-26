package alizarchik.alex.searchstat;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Olesia on 16.01.2018.
 */

public class TokenStorage {

    private SharedPreferences sPref;
    private final String SAVED_TOKEN = "saved_token";

    private static TokenStorage instance;

    private TokenStorage() {
    }

    static TokenStorage getInstance() {
        if (instance == null) {
            instance = new TokenStorage();
        }
        return instance;
    }

    void saveToken(String token, Context context) {
        sPref = context.getSharedPreferences(SAVED_TOKEN, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TOKEN, token);
        ed.apply();
    }

    public String loadToken(Context context) {
        sPref = context.getSharedPreferences(SAVED_TOKEN, MODE_PRIVATE);
        return sPref.getString(SAVED_TOKEN, "");
    }
}
