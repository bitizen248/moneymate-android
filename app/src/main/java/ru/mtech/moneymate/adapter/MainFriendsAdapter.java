package ru.mtech.moneymate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ru.mtech.moneymate.R;
import ru.mtech.moneymate.object.Friend;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Адаптер RecyclerView списка последних обновлений.
 */

public class MainFriendsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Friend> list;

    public MainFriendsAdapter(Context context, List<Friend> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        return new FriendsHolder(lf.inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Friend object = list.get(position);
        if (object.getName().length() < 10) {
            ((FriendsHolder) holder).name.setText(object.getName());
        } else {
            ((FriendsHolder) holder).name.setText(object.getName().substring(0, 8) + "...");
        }
        Glide.with(context)
                .load(object.getAvatar())
                .apply((RequestOptions.circleCropTransform()))
                .into(((FriendsHolder) holder).avatar);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class FriendsHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView name;
        public FriendsHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
        }
    }
}
