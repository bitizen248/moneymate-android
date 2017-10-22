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

import java.util.Calendar;
import java.util.List;

import ru.mtech.moneymate.R;
import ru.mtech.moneymate.object.Message;

/**
 * Created by Artyom Vlasov on 22.10.2017.
 * Адаптер RecyclerView списка друзей и комнат на соответствующем экране.
 */

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    List<Message> list;

    public MessageAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        return new MessageHolder(lf.inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Message object = list.get(position);
        ((MessageHolder) holder).name.setText(object.getName());
        ((MessageHolder) holder).msg.setText(object.getMsg());
        Glide.with(context)
                .load(object.getAvatar())
                .apply((RequestOptions.circleCropTransform()))
                .into(((MessageHolder) holder).avatar);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(object.getTime()*1000);
        String date = context.getResources().getString(R.string.time, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        ((MessageHolder) holder).time.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView name;
        TextView msg;
        TextView time;
        TextView newMsg;
        ViewGroup msgLayGroup;
        ImageView avatarGroup;
        TextView msgGroup;
        ViewGroup wrapper;

        public MessageHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            msg = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            newMsg = itemView.findViewById(R.id.new_msg);
            msgLayGroup = itemView.findViewById(R.id.group);
            avatarGroup = itemView.findViewById(R.id.avatar_group);
            msgGroup = itemView.findViewById(R.id.message_group);
            wrapper = itemView.findViewById(R.id.wrapper);
        }
    }

}
