package com.example.docsscanning;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureFragment extends Fragment {
    public static final int CAMERA_REQUEST_CODE = 102;
    ImageView click_image_id;
    private Button capture_button,gallery_button;
    FragmentActivity fragmentActivity = getActivity();
    public static final int CAMERA_PERM_CODE = 101;
    String currentPhotoPath;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static CaptureFragment fragment = new CaptureFragment();
    public CaptureFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static CaptureFragment newInstance(String param1, String param2) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(fragmentActivity,"Camera Opened", Toast.LENGTH_SHORT).show();
                askCameraPermission();
            }
        });

        gallery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(fragmentActivity,"Phone Gallery Opened", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //noinspection deprecation
            requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }

    /*private void openCamera() {
        Toast.makeText(fragmentActivity, "Camera Opened", Toast.LENGTH_SHORT).show();
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //noinspection deprecation
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }*/

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(fragmentActivity, "Camera Permission is required", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_capture, container, false);
        click_image_id = view.findViewById(R.id.img_view_cap);
        capture_button = view.findViewById(R.id.capture_button);
        gallery_button = view.findViewById(R.id.gallery_button);

        return view;
    }

    @SuppressWarnings("deprecation")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Match the request 'pic id with requestCode
        if (requestCode == CAMERA_REQUEST_CODE) {
            // BitMap is data structure of image file
            // which stor the image in memory
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            // Set the image in imageview for display
            click_image_id.setImageBitmap(photo);

        }
    }

    private File createImageFile() throws IOException{
        // create an image  file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = fragmentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(fragmentActivity.getPackageManager())!= null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch(Exception e){
            }
            if(photoFile!= null){
                Uri photoURI = FileProvider.getUriForFile(fragmentActivity,"", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //noinspection deprecation
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}