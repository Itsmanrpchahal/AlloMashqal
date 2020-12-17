package com.allomashqal.sharedpref;

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


    public AlertDialog alertDialog;
    public Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences=context.getSharedPreferences("UserToken", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return super.onCreateView(inflater, container, savedInstanceState);
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