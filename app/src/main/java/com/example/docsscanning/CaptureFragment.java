package com.example.docsscanning;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
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
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureFragment extends Fragment {
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView click_image_id;
    private Button capture_button,gallery_button;
    FragmentActivity fragmentActivity = getActivity();
    public static final int CAMERA_PERM_CODE = 101;
    public static final int Gallery_PERM_CODE = 106;
    String currentPhotoPath;
    StorageReference storageReference;
    Fragment galleryFragment = new GalleryFragment();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static CaptureFragment fragment = new CaptureFragment();
    public CaptureFragment() {
        // Required empty public constructor

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
                Toast.makeText(fragmentActivity, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode == Gallery_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //noinspection deprecation
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }else{
                Toast.makeText(fragmentActivity, "Permission Denied", Toast.LENGTH_SHORT).show();
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
        storageReference = FirebaseStorage.getInstance().getReference();

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
                askPermission();

            }
        });

        return view;
    }

    private void askPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //noinspection deprecation
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Gallery_PERM_CODE);
        }
        else{
            Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //noinspection deprecation
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        }
    }

    @SuppressWarnings("deprecation")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Match the request 'pic id with requestCode
        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                click_image_id.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Uri of image is: "+ Uri.fromFile(f));

                Intent mediascan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediascan.setData(contentUri);
                getActivity().sendBroadcast(mediascan);
                //uploadToFirebase(f.getName(),contentUri);

            }
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "."+getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri: "+ imageFileName);
                click_image_id.setImageURI(contentUri);
                //uploadToFirebase(imageFileName,contentUri);

            }
        }
    }

    private void uploadToFirebase(String name, Uri contentUri) {
        StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        Log.d("tag", "Upload Image Uri is " + uri.toString());
                    }
                });
                Toast.makeText(fragmentActivity,"Image is Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(fragmentActivity,"Image Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException{
        // create an image  file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!= null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch(Exception e){
            }
            if(photoFile!= null){
                Uri photoURI = FileProvider.getUriForFile(getActivity(),"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //noinspection deprecation
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}