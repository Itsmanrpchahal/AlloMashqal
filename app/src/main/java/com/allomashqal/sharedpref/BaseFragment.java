package com.allomashqal.sharedpref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getContext().getSharedPreferences("UserToken", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    void setStringVal(String key,String val){
        editor.putString(key,val);
        editor.apply();
    }

    public String getStringVal(String key){
        return sharedPreferences.getString(key,"");
    }

    public void clearStringVal(String key)
    {
        editor.clear();
        editor.apply();
    }
}