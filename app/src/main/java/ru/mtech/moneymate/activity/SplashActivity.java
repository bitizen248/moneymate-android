package ru.mtech.moneymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.mtech.moneymate.helper.Settings;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Загрузочный сплэш, основная задача которого, в определении, за счет наличия сохраненного токена,
 * того, какой экран загружать(экран логина или основной)
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE).getString(Settings.TOKEN, " ").equals(" ")
                && getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE).getBoolean(Settings.IF_AUTH, false)) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }
}
