package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class SetupActivity extends AppCompatActivity {

    private EditText name;
    private EditText hall;
    private EditText mobileEditText;
    private Button savebtn;
    private String user_id;


    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        name = (EditText) findViewById(R.id.nameSetup);
        hall = (EditText) findViewById(R.id.hall_setup);
        mobileEditText = (EditText) findViewById(R.id.mobEditText);
        savebtn = (Button) findViewById(R.id.saveSetupbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user_id = firebaseAuth.getCurrentUser().getUid();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = name.getText().toString();
                String Hall = hall.getText().toString();
                String Mobile = mobileEditText.getText().toString();

                if(!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(Hall) ){



                    Map <String, String> m = new HashMap<>();
                    //m.put("userID", user_id);
                    m.put("name", user_name);
                    m.put("hall", Hall);
                    m.put("mobile", Mobile);

                    firebaseFirestore.collection("Users").document(user_id).set(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SetupActivity.this, "Account has been updated", Toast.LENGTH_LONG).show();
                                Intent main = new Intent(SetupActivity.this, MainActivity.class);
                                startActivity(main);
                                finish();
                            }else{
                                Toast.makeText(SetupActivity.this, "Error took place", Toast.LENGTH_LONG).show();
                            }
                        }
                    });





                }else{
                    Toast.makeText(SetupActivity.this, "Fill the contents first", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}
