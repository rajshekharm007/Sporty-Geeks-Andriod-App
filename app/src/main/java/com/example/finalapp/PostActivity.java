package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    private Button postbtn;
    private EditText sport;
    private EditText desc;
    private EditText date_post;
    private EditText time_post;
    private EditText venue_post;
    private EditText nameAgain;
    private EditText hallAgain;
    private EditText mobileAgain;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String user_id;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postbtn = (Button) findViewById(R.id.postblogbtn);
        sport = (EditText) findViewById(R.id.sport);
        desc = (EditText) findViewById(R.id.desc);
        date_post = (EditText) findViewById(R.id.date_post);
        time_post = (EditText) findViewById(R.id.time_post);
        venue_post = (EditText) findViewById(R.id.venue_post);
        nameAgain = (EditText) findViewById(R.id.nameAgain);
        hallAgain = (EditText) findViewById(R.id.hallAgain);
        mobileAgain = (EditText) findViewById(R.id.mobileAgain);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser();

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Desc = desc.getText().toString();
                String Sport = sport.getText().toString();
                String Date = date_post.getText().toString();
                String Time = time_post.getText().toString();
                String Venue = venue_post.getText().toString();
                String Email = user.getEmail();
                String Name = nameAgain.getText().toString();
                String Hall = hallAgain.getText().toString();
                String Mobile = mobileAgain.getText().toString();

                if(!TextUtils.isEmpty(Desc) && !TextUtils.isEmpty(Sport) && !TextUtils.isEmpty(Date) && !TextUtils.isEmpty(Time) && !TextUtils.isEmpty(Venue) && !TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Hall) && !TextUtils.isEmpty(Mobile)) {


                    Map<String, Object> m = new HashMap<>();
                    m.put("desc", Desc);
                    m.put("sport", Sport);
                    m.put("date", Date);
                    m.put("time", Time);
                    m.put("venue", Venue);
                    m.put("email", Email);
                    m.put("name", Name);
                    m.put("hall", Hall);
                    m.put("mobile", Mobile);

                    firebaseFirestore.collection("Posts").add(m).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(PostActivity.this, "Post was added", Toast.LENGTH_LONG).show();
                                Intent main = new Intent(PostActivity.this, MainActivity.class);
                                startActivity(main);
                                finish();
                            }else{


                                Toast.makeText(PostActivity.this, "Error made while adding post", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }


}
