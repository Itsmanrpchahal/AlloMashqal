package com.allomashqal.SignUp;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allomashqal.MapScreen.MapScreen;
import com.allomashqal.R;
import com.allomashqal.SignIn.SignIn;
import com.allomashqal.SignUp.response.SignUpResponse;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.sharedpref.BaseActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class SignUp extends BaseActivity implements View.OnClickListener, Controller.SignUpAPI {

    @BindView(R.id.signup_tv)
    TextView signup_tv;
    @BindView(R.id.username_et)
    EditText username_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.mobileno_et)
    EditText mobileno_et;
    @BindView(R.id.email_et)
    EditText email_et;
    @BindView(R.id.signup_bt)
    Button signup_bt;
    Controller controller;
    ProgressDialog pd;
    Utility utility;

    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locale = getStringVal(Constants.LOCALE);
        controller = new Controller(this);
        if (locale.equals("en")) {
            setContentView(R.layout.activity_sign_up);
        } else {
            setContentView(R.layout.activity_sign_up_arabic);
        }
        FirebaseApp.initializeApp(this);
        ButterKnife.bind(this);


        utility = new Utility();
        pd = new ProgressDialog(this);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);

        listeners();

        UpdateViews(locale);
    }

    public void listeners() {
        signup_bt.setOnClickListener(this);
    }

    private void UpdateViews(String locale) {
        Context context = LocaleHelper.setLocale(this, locale);
        Resources resources = context.getResources();
        signup_tv.setText(resources.getText(R.string.signup));
        username_et.setHint(resources.getText(R.string.username));
        password_et.setHint(resources.getText(R.string.password));
        mobileno_et.setHint(resources.getText(R.string.mobileno));
        email_et.setHint(resources.getText(R.string.email));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onClick(View view) {
        if (view == signup_bt) {
            if (username_et.getText().toString().isEmpty()) {
                username_et.setFocusable(true);
                username_et.setError("Enter username");
            } else if (password_et.getText().toString().isEmpty()) {
                password_et.setFocusable(true);
                password_et.setError("Enter password");
            } else if (mobileno_et.getText().toString().isEmpty()) {
                mobileno_et.setFocusable(true);
                mobileno_et.setError("Enter mobile number");
            } else if (email_et.getText().toString().isEmpty()) {
                email_et.setFocusable(true);
                email_et.setError("Enter email");
            } else {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String token = instanceIdResult.getToken();
                        Log.e("token", token);
                        pd.show();
                        if (utility.isConnectingToInternet(SignUp.this)) {
                            pd.setContentView(R.layout.loading);
                            controller.SetSignUp(
                                    username_et.getText().

                                            toString(),
                                    email_et.getText().

                                            toString(),
                                    mobileno_et.getText().

                                            toString(),

                                    token,
                                    "android",
                                    password_et.getText().toString()
                            );
                        } else {
                            Toast.makeText(SignUp.this,getString(R.string.nointernet),Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        }
    }

    @Override
    public void onSuccessSignUp(Response<SignUpResponse> success) {
        pd.dismiss();
        if (success.isSuccessful())
        {
            if (success.code()==200)
            {
                username_et.setText("");
                password_et.setText("");
                mobileno_et.setText("");
                email_et.setText("");
                Intent intent = new Intent(this, SignIn.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(SignUp.this,""+success.message(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(SignUp.this,""+error,Toast.LENGTH_SHORT).show();
    }
}