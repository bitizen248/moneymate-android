package ru.mtech.moneymate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mtech.moneymate.MoneyMateApplication;
import ru.mtech.moneymate.R;
import ru.mtech.moneymate.adapter.FeedAdapter;
import ru.mtech.moneymate.adapter.MainFriendsAdapter;
import ru.mtech.moneymate.helper.Settings;
import ru.mtech.moneymate.object.Feed;
import ru.mtech.moneymate.object.Friend;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Экран со списками обновлений и фида.
 * Внутри класса выполняются запросы на получения фида и списка обновлений друзей. Для их выполнения
 * в шапке запроса используется внутренний токен, полученный ранее. На выходе получаем нужные нам
 * списки. Получения происходят через Retrofit 2 и парсятся автоматически библиотекой Gson.
 */

public class FeedFragment extends Fragment {

    private MainFriendsAdapter fAdapter;
    private RecyclerView friends;
    private RecyclerView feed;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friends = view.findViewById(R.id.friends_view);
        feed = view.findViewById(R.id.feed_view);

        List<Friend> friendList = new ArrayList<>();
        List<Feed> feedList = new ArrayList<>();
        FeedAdapter feedAdapter = new FeedAdapter(getContext(), feedList);
        fAdapter = new MainFriendsAdapter(getContext(), friendList);
        feed.setAdapter(feedAdapter);
        feed.setNestedScrollingEnabled(false);
        friends.setAdapter(fAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManagerV = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        feed.setLayoutManager(layoutManagerV);
        feed.setHasFixedSize(false);

        friends.setLayoutManager(layoutManager);
        friends.setNestedScrollingEnabled(false);

        String token = getContext().getSharedPreferences(Settings.SETTINGS, Context.MODE_PRIVATE).getString(Settings.TOKEN, " ");
        MoneyMateApplication.response().getLastFriendList(token).enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (!response.body().isEmpty()) {
                    Log.e("responseSend", new Gson().toJson(response.body()));
                    friendList.addAll(response.body());
                    fAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.e("responseSend", t.toString());

            }
        });

        MoneyMateApplication.response().getFeed(token).enqueue(new Callback<List<Feed>>() {
            @Override
            public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                Log.e("feed", response.toString());
                feedList.addAll(response.body());
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Feed>> call, Throwable t) {

            }
        });
    }
}
