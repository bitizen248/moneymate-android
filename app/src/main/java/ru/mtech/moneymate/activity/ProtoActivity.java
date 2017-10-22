package ru.mtech.moneymate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import ru.mtech.moneymate.R;
import ru.mtech.moneymate.helper.BottomNavigationViewHelper;
import ru.mtech.moneymate.helper.MyBottomNavigationView;

/**
 * Created by Artyom Vlasov on 20.10.2017.
 * Универсальное activity для управления общими элемантами интерфейса: тулбар, таббар.
 * MyBottomNavigationView и BottomNavigationViewHelper - кастомый таб, в котором отсутствуют
 * названия табов(в теории)
 */

public abstract class ProtoActivity extends AppCompatActivity {

    FrameLayout contentFrame;
    Toolbar toolbar;
    AppBarLayout appBar;
    protected MyBottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proto);

        contentFrame = findViewById(R.id.contentframe);
        toolbar = findViewById(R.id.toolbar);
        appBar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        getSupportActionBar().setTitle(resId);
    }
}
