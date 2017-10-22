package ru.mtech.moneymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mtech.moneymate.R;

/**
 * Created by Artyom Vlasov on 20.10.2017.
 * Activity приветствия и перехода к авторизации через профиль ВК.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    TextView loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginBtn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, AuthWebActivity.class)));
    }
}
