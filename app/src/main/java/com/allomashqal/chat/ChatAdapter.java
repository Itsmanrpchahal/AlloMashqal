package com.allomashqal.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allomashqal.R;
import com.allomashqal.chat.response.ChatResponse;
import com.allomashqal.offers.adpater.OffersAdapter;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    String senderID;
    ArrayList<ChatResponse.Data.Chat> chatArrayList = new ArrayList<>();

    public ChatAdapter(Context context,ArrayList<ChatResponse.Data.Chat> arrayList,String senderID) {
        this.context = context;
        this.chatArrayList = arrayList;
        this.senderID = senderID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (chatArrayList.get(position).getSenderId().equals(senderID))
        {
            holder.sendertv.setVisibility(View.VISIBLE);
            holder.recivertv.setVisibility(View.GONE);
            holder.sendertv.setText(chatArrayList.get(position).getMessage());
        } else {
            holder.sendertv.setVisibility(View.GONE);
            holder.recivertv.setVisibility(View.VISIBLE);
            holder.recivertv.setText(chatArrayList.get(position).getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recivertv,sendertv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recivertv = itemView.findViewById(R.id.recivertv);
            sendertv = itemView.findViewById(R.id.sendertv);
        }
    }
}
