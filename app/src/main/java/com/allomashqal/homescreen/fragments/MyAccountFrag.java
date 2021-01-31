package com.allomashqal.homescreen.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allomashqal.MainActivity;
import com.allomashqal.R;
import com.allomashqal.Utils.Constants;
import com.allomashqal.Utils.Utility;
import com.allomashqal.controller.Controller;
import com.allomashqal.helper.LocaleHelper;
import com.allomashqal.homescreen.fragments.response.GetProfileResponse;
import com.allomashqal.sharedpref.BaseFragment;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class MyAccountFrag extends BaseFragment implements Controller.GetProfileAPI {

    String locale;
    View view;
    MaterialButton logout;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.username_et)
    EditText username_et;
    @BindView(R.id.number_et)
    EditText number_et;
    @BindView(R.id.email_et)
    EditText email_et;
    ImageButton edit_image;
    RoundedImageView profile_picture;
    MultipartBody.Part part;
    Bitmap bitmap;
    private String path = "";
    Controller controller;
    ProgressDialog pd;
    Utility utility;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        locale = getStringVal(Constants.LOCALE);
        if (locale.equals("ar")) {
            view = inflater.inflate(R.layout.fragment_my_account_arabic, container, false);

        } else {
            view = inflater.inflate(R.layout.fragment_my_account, container, false);
        }
        logout = view.findViewById(R.id.logout);
        edit_image = view.findViewById(R.id.edit_image);
        profile_picture = view.findViewById(R.id.profile_picture);

        ButterKnife.bind(this, view);
        edit_image.setVisibility(View.GONE);
        utility = new Utility();
        pd = new ProgressDialog(getContext());
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        pd.isIndeterminate() = true;
        pd.setCancelable(false);
        controller = new Controller(this);
        controller.GetProfile(getStringVal(Constants.USERID));
        pd.show();
        pd.setContentView(R.layout.loading);

        listeners();

        return view;
    }

    private void listeners() {

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStringVal(Constants.USERID);
                Intent intent = new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent
                        .FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
                getActivity().finish();

            }
        });

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfile_picture();
            }
        });
    }

    private void setProfile_picture() {
        LinearLayout camera, gallery;
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.imagepicker);
        camera = dialog.findViewById(R.id.linear_camera);
        gallery = dialog.findViewById(R.id.linear_gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with((Activity) getContext())
                        .cameraOnly()
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with((Activity) getContext())
                        .galleryOnly()
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            String fileUri = String.valueOf(data.getData());
            File file = ImagePicker.Companion.getFile(data);
            String filePath = ImagePicker.Companion.getFilePath(data);
            path = filePath;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(fileUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                part = Utility.sendImageFileToserver(getContext().getFilesDir(), bitmap, "image");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onSuccessProfile(Response<GetProfileResponse> success) {
        pd.dismiss();
        if (success.isSuccessful()) {
            if (success.body().getSuccess() == true) {

                username_et.setText(success.body().getData().getUsername());
                email_et.setText(success.body().getData().getEmail());
                number_et.setText("+971"+success.body().getData().getPhone());
                Glide.with(getContext()).load(success.body().getData().getImage()).into(profile_picture);
            } else {
                Toast.makeText(getContext(), "" + success.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "" + success, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        pd.dismiss();
        Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
    }

    //Get Image from Url
    public class getImagefromURL extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        public getImagefromURL(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {

            String urlimage = url[0];

            bitmap = null;


            try {
                InputStream stream = new URL(urlimage).openStream();
                bitmap = BitmapFactory.decodeStream(stream);
                part = Utility.sendImageFileToserver(getContext().getFilesDir(), bitmap, "image");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}