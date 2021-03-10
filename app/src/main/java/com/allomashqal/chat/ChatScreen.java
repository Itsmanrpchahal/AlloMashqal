package com.allomashqal.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.chat.response.ChatResponse;
import com.allomashqal.chat.response.SendChatResponse;
import com.allomashqal.controller.Controller;
import com.allomashqal.offers.adpater.OffersAdapter;
import com.allomashqal.offers.adpater.Offers_IF;
import com.allomashqal.sharedpref.BaseActivity;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

public class ChatScreen extends BaseActivity implements Controller.ChatAPI ,Controller.SendChatAPI{

    String vendorID = "";
    Intent intent;
    ImageButton back;
    RecyclerView chat_recycler;
    ChatAdapter adapter;
    ArrayList<ChatResponse.Data.Chat> chatArrayList = new ArrayList<>();
    ProgressDialog pd;
    Utility utility;
    Controller controller;
    ImageButton sendmesg_bt;
    EditText entermesg_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        controller = new Controller(this,this);
        intent = getIntent();
        vendorID = intent.getStringExtra("vendorID");

        utility = new Utility();
        pd = new ProgressDialog(this);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);
        controller.Chat(getStringVal(Constants.USERID),vendorID);
        pd.show();
        pd.setContentView(R.layout.loading);
        findIds();
        listeners();

    }

    private void setData(ArrayList<ChatResponse.Data.Chat> chat) {
        Collections.reverse(chat);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chat_recycler.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this,chat,getStringVal(Constants.USERID));
        chat_recycler.setAdapter(adapter);
    }

    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sendmesg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!entermesg_et.getText().toString().equals(""))
                {
                   controller.SendChat(getStringVal(Constants.USERID),vendorID,entermesg_et.getText().toString());
                }

            }
        });
    }

    private void findIds() {
        back = findViewById(R.id.back);
        chat_recycler = findViewById(R.id.chat_recycler);
        entermesg_et = findViewById(R.id.entermesg_et);
        sendmesg_bt = findViewById(R.id.sendmesg_bt);
    }

    @Override
    public void onSuccessChat(Response<ChatResponse> success) {
        pd.dismiss();
        if (success.isSuccessful())
        {
           if (success.body().getSuccess()==true)
           {
               setData((ArrayList<ChatResponse.Data.Chat>) success.body().getData().getChat());
           } else  {
               Toast.makeText(this,""+success.message(),Toast.LENGTH_SHORT).show();
           }
        } else  {
            Toast.makeText(this,""+success.message(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessSendChat(Response<SendChatResponse> success) {
        entermesg_et.setText("");
        if (success.isSuccessful())
        {
            if (success.body().getSuccess()==true)
            {
                controller.Chat(getStringVal(Constants.USERID),vendorID);
            } else  {
                pd.dismiss();
                Toast.makeText(this,""+success.message(),Toast.LENGTH_SHORT).show();
            }
        } else  {
            pd.dismiss();
            Toast.makeText(this,""+success.message(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
    }
}