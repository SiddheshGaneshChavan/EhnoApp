package com.example.ehnoapp;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.UUID;

public class UploadtoServer extends AppCompatActivity {
    ImageView selectedImage;
    Button galleryBtn,cameraback;
    FirebaseStorage storage;
    Uri imageURI;
    ProgressDialog dialog;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadto_server);
        selectedImage=findViewById(R.id.Camera_photo);
        galleryBtn=findViewById(R.id.buttongallery);
        cameraback=findViewById(R.id.buttoncameraback);
        storage=FirebaseStorage.getInstance();
        dialog=new ProgressDialog(UploadtoServer.this);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK&&result.getData()!=null){
                    Bundle bundle=result.getData().getExtras();
                    Bitmap finalPhoto=(Bitmap) bundle.get("data");
                    selectedImage.setImageBitmap(finalPhoto);
                }
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        cameraback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(UploadtoServer.this,Camera_Act.class);
                startActivity(intent2);
            }
        });
    }

    private void uploadImage() {
        dialog.setMessage("Uploading....");
        dialog.show();
        String data;
        data=UUID.randomUUID().toString();
        if (imageURI!=null) {
            StorageReference reference = storage.getReference().child("image/*" +data);
            reference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        dialog.dismiss();
                        Toast.makeText(UploadtoServer.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(UploadtoServer.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    ActivityResultLauncher<String> mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result!=null){
                selectedImage.setImageURI(result);
                imageURI=result;
            }
        }
    });
}