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
import ru.mtech.moneymate.object.Feed;

/**
 * Created by Artyom Vlasov on 22.10.2017.
 * Адаптер для RecyclerView фида на начальном экране.
 */

public class FeedAdapter extends RecyclerView.Adapter {

    Context context;
    List<Feed> list;

    public FeedAdapter(Context context, List<Feed> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        return new FeedHolder(lf.inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Feed object = list.get(position);
        ((FeedHolder) holder).name.setText(object.getName());
        ((FeedHolder) holder).description.setText(object.getDescription());
        ((FeedHolder) holder).amount.setText(object.getAmount() + " \u20BD");
        Glide.with(context).load(object.getAvatar())
                .apply((RequestOptions.circleCropTransform())).into(((FeedHolder) holder).avatar);

        if (object.getType().equals("room")) {
            Glide.with(context).load(R.drawable.check)
                    .apply((RequestOptions.circleCropTransform())).into(((FeedHolder) holder).transaction);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FeedHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView avatar;
        TextView amount;
        ImageView transaction;
        public FeedHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.message);
            avatar = itemView.findViewById(R.id.avatar);
            amount = itemView.findViewById(R.id.amount);
            transaction = itemView.findViewById(R.id.transaction);
        }
    }
}
