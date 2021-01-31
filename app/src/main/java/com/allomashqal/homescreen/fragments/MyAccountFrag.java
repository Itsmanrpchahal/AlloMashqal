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
import android.util.Base64;
import android.util.Log;
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
import com.allomashqal.homescreen.fragments.response.UpdateProfileResponse;
import com.allomashqal.sharedpref.BaseFragment;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;

public class MyAccountFrag extends BaseFragment implements Controller.GetProfileAPI, Controller.UpdateProfileAPI {

    public static int RESULT_LOAD_IMAGE = 1001;
    public static int REQUEST_CAMERA = 1002;
    public static int MY_PERMISSIONS_REQUEST_CAMERA = 1255;
    String[] dialogOptions = new String[]{"Camera", "Gallery", "Cancel"};
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
    @BindView(R.id.edit_bt)
    MaterialButton edit_bt;
    ImageButton edit_image;
    RoundedImageView profile_picture;
    MultipartBody.Part part;
    Bitmap bitmap;
    private String path = "";
    static Uri uri;
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
        controller = new Controller(this, this);
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
                //setProfile_picture();
                showdialog();
            }
        });

        edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_bt.getText().toString().equals("Edit")) {
                    username_et.setEnabled(false);
                    number_et.setEnabled(true);
                    password_et.setEnabled(true);
                    email_et.setEnabled(true);
                    edit_bt.setText("Update");
                    edit_image.setVisibility(View.VISIBLE);
                } else if (edit_bt.getText().toString().equals("Update")) {
                    username_et.setEnabled(false);
                    number_et.setEnabled(false);
                    password_et.setEnabled(false);
                    password_et.setText("");
                    email_et.setEnabled(false);
                    edit_bt.setText("Edit");
                    edit_image.setVisibility(View.GONE);


                    if (email_et.getText().toString().toString().equals("") && number_et.getText().toString().equals("")) {
                        number_et.requestFocus();
                        number_et.setError("Enter error");

                        email_et.requestFocus();
                        email_et.setError("Enter error");

                    } else if (number_et.getText().toString().equals("")) {
                        number_et.requestFocus();
                        number_et.setError("Enter error");
                    } else if (email_et.getText().toString().toString().equals("")) {
                        email_et.requestFocus();
                        email_et.setError("Enter error");
                    }
                    else {
                        pd.show();
                        controller.updateProfile(getStringVal(Constants.USERID),
                                password_et.getText().toString(),
                                email_et.getText().toString(),
                                number_et.getText().toString(),
                                part);
                    }

                }
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Activity.RESULT_OK) {
//            String fileUri = String.valueOf(data.getData());
//            File file = ImagePicker.Companion.getFile(data);
//            String filePath = ImagePicker.Companion.getFilePath(data);
//            path = filePath;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(fileUri));
//                profile_picture.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                part = Utility.sendImageFileToserver(getContext().getFilesDir(), bitmap, "image");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    @Override
    public void onSuccessProfile(Response<GetProfileResponse> success) {
        pd.dismiss();
        if (success.isSuccessful()) {
            if (success.body().getSuccess() == true) {

                username_et.setText(success.body().getData().getUsername());
                email_et.setText(success.body().getData().getEmail());
                number_et.setText(success.body().getData().getPhone());
                Glide.with(getContext()).load(success.body().getData().getImage()).into(profile_picture);
                new getImagefromURL(profile_picture).execute(success.body().getData().getImage());
            } else {
                Toast.makeText(getContext(), "" + success.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "" + success.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessUpdateProfile(Response<UpdateProfileResponse> success) {
        pd.dismiss();
        if (success.isSuccessful()) {
            if (success.body().getSuccess() == true) {
                username_et.setText(success.body().getData().getUsername());
                email_et.setText(success.body().getData().getEmail());
                password_et.setText("************");
                number_et.setText(success.body().getData().getPhone().toString());
                Glide.with(getContext()).load(success.body().getData().getImage()).into(profile_picture);
            }
        } else {
            Toast.makeText(getContext(), "" + success.message(), Toast.LENGTH_SHORT).show();
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
                part = Utility.sendImageFileToserver(getContext(),bitmap,"image");

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

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Images");
        builder.setCancelable(false);

        builder.setItems(dialogOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ("Camera".equals(dialogOptions[which])) {

                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.CAMERA)) {

                            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        } else {
                            ActivityCompat.requestPermissions((Activity) getContext(),
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }

                    } else {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }

                } else if ("Gallery".equals(dialogOptions[which])) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);

                } else if ("Cancel".equals(dialogOptions[which])) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {


            uri = data.getData();

            try {

                CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).start((Activity) getContext());
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                profile_picture.setImageBitmap(bitmap);
                part = Utility.sendImageFileToserver(getContext(),bitmap,"image");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Uri imageURI = result.getUri();
            Log.d("uri", imageURI.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageURI);
                profile_picture.setImageBitmap(bitmap);
                part = Utility.sendImageFileToserver(getContext(),bitmap,"image");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA}, requestCode);

            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");

            //CropImage.activity(uri).setAspectRatio(1,1).start(getActivity());
            profile_picture.setImageBitmap(bitmap);
            encodeTobase64(bitmap);

            try {
                part = Utility.sendImageFileToserver(getContext(),bitmap,"image");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}