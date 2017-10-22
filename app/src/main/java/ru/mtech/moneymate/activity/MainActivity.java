package ru.mtech.moneymate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mtech.moneymate.R;
import ru.mtech.moneymate.fragment.FeedFragment;
import ru.mtech.moneymate.fragment.FriendsFragment;
import ru.mtech.moneymate.fragment.StatsFragment;

/**
 * Created by Artyom Vlasov on 20.10.2017.
 * Activity главного экрана. Из него происходит управление фрагментами и таббаром.
 */

public class MainActivity extends ProtoActivity {

    private FragmentManager fragmentManager;
    public static final int FEED = 0;
    public static final int DIALOG = 1;
    public static final int STAT = 2;
    private int currentPage = 0;
    @BindView(R.id.fab_menu)
    public FloatingActionMenu floatingActionMenu;
    @BindView(R.id.button_1)
    public FloatingActionButton fabDialog;
    @BindView(R.id.button_2)
    public FloatingActionButton fabAddPerson;
    @BindView(R.id.button_3)
    public FloatingActionButton fabAddPersons;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrame);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new FeedFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.feed_menu:
                    if (currentPage != FEED) {
                        floatingActionMenu.setVisibility(View.GONE);
                        currentPage = 0;
                        getSupportActionBar().setTitle("Лента");
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction().replace(R.id.frame, new FeedFragment()).commit();
                    }
                    return true;
                case R.id.dialog_menu:
                    if (currentPage != DIALOG) {
                        currentPage = 1;
                        getSupportActionBar().setTitle("Друзья и комнаты");
                        floatingActionMenu.setVisibility(View.VISIBLE);
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction().replace(R.id.frame, new FriendsFragment()).commit();
                    }
                    return true;
                case R.id.stat_menu:
                    if (currentPage != STAT) {
                        floatingActionMenu.setVisibility(View.GONE);
                        currentPage = 2;
                        getSupportActionBar().setTitle("Статистика");
                        fragmentManager.popBackStack();
                        fragmentManager.beginTransaction().replace(R.id.frame, new StatsFragment()).commit();
                    }
                    return true;
            }
            return true;
        });
    }
}
