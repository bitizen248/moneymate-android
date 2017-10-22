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
import ru.mtech.moneymate.adapter.MessageAdapter;
import ru.mtech.moneymate.helper.Settings;
import ru.mtech.moneymate.object.Message;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Фрагмент спска комнат и друзей. Получается запросом к серверу с помощью токена, передаваемого в
 * header запроса. На выходе - массив объектов.
 */

public class FriendsFragment extends Fragment {

    private RecyclerView messages;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messages = view.findViewById(R.id.msg_view);

        List<Message> list = new ArrayList<>();
        MessageAdapter adapter = new MessageAdapter(getContext(), list);
        messages.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        messages.setHasFixedSize(true);
        messages.setLayoutManager(linearLayoutManager);

        String token = getContext().getSharedPreferences(Settings.SETTINGS, Context.MODE_PRIVATE).getString(Settings.TOKEN, " ");
        MoneyMateApplication.response().getFriendsAndRooms(token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (!response.body().equals(null)) {
                    Log.e("responseSend", new Gson().toJson(response.body()));
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("responseSend", t.toString());

            }
        });
    }
}
