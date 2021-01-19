package com.allomashqal.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.MapScreen.MapScreen;
import com.allomashqal.R;
import com.allomashqal.SignIn.response.SignInResponse;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class SignIn extends BaseActivity implements View.OnClickListener, Controller.SignInAPI {

    @BindView(R.id.signin_tv)
    TextView signin_tv;
    @BindView(R.id.username_et)
    EditText username_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.signin_bt)
    MaterialButton signin_bt;
    @BindView(R.id.forgotpassword)
    MaterialButton forgotpassword;
    ProgressDialog pd;
    Utility utility;
    String locale;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = getStringVal(Constants.LOCALE);
        if (locale.equals("en")) {
            setContentView(R.layout.activity_sign_in);
        } else {
            setContentView(R.layout.activity_sign_in_arabic);
        }
        ButterKnife.bind(this);
        utility = new Utility();
        pd = new ProgressDialog(this);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);
        controller = new Controller(this);

        listeners();
        UpdateViews(locale);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        Resources resources = context.getResources();
        signin_tv.setHint(resources.getText(R.string.signin));
        username_et.setHint(resources.getText(R.string.username));
        password_et.setHint(resources.getText(R.string.password));
        signin_bt.setText(resources.getText(R.string.signin));
        forgotpassword.setText(resources.getText(R.string.forgot));
    }

    private void listeners() {
        signin_bt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == signin_bt) {

            if (username_et.getText().toString().isEmpty()) {
                username_et.setFocusable(true);
                username_et.setError("Enter username");
            } else if (password_et.getText().toString().isEmpty()) {
                password_et.setFocusable(true);
                password_et.setError("Enter password");
            } else {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String token = instanceIdResult.getToken();
                        Log.e("token", token);
                        if (utility.isConnectingToInternet(SignIn.this)) {
                            pd.show();
                            pd.setContentView(R.layout.loading);
                            controller.SetSignIn(username_et.getText().toString(),
                                    password_et.getText().toString(),
                                    token,
                                    "android");
                        }

                    }
                });
            }
//            startActivity(new Intent(this, MapScreen.class));
        }
    }

    @Override
    public void onSuccessSignIn(Response<SignInResponse> success)
    {
        pd.dismiss();
        if (success.isSuccessful())
        {
            if (success.body().getSuccess()==true)
            {
                setStringVal(Constants.USERID,success.body().getData().getId().toString());
                Intent intent = new Intent(this,MapScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this,""+success.message(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
    }
}