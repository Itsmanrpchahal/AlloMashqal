package com.allomashqal.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public AlertDialog alertDialog;
    public Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("UserToken", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void showErrorDialog() {
        if (!isFinishing()) {
            alertDialog.show();
        }
    }

    public void setStringVal(String key,String val){
        editor.putString(key,val);
        editor.apply();

    }

    public void clearStringVal(String key)
    {
        editor.clear();
        editor.apply();
    }

    public String getStringVal(String key){
        return sharedPreferences.getString(key,"");
    }
}