package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button logoutbtn;
    private Button settingsbtn;
    private Button postbtn;
    private Button homeButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    List<String> sports_list;
    List<String> desc_list;
    List<String> date_list;
    List<String> time_list;
    List<String> venue_list;
    List<String> email_list;
    List<String> name_list;
    List<String> hall_list;
    List<String> mobile_list;

    String[] sports;
    String[] descriptions;
    String[] dates;
    String[] times;
    String[] venues;
    String[] emails;
    String[] names;
    String[] halls;
    String[] mobiles;

    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        logoutbtn = (Button) findViewById(R.id.logoutbtn);
        settingsbtn = (Button) findViewById(R.id.settings_mainbtn);
        postbtn = (Button) findViewById(R.id.postbtn);
        homeButton = (Button) findViewById(R.id.homeButton);

        sports_list = new ArrayList<String>();
        desc_list = new ArrayList<String>();
        date_list = new ArrayList<String>();
        time_list = new ArrayList<String>();
        venue_list = new ArrayList<String>();
        email_list = new ArrayList<String>();
        name_list = new ArrayList<String>();
        hall_list = new ArrayList<String>();
        mobile_list = new ArrayList<String>();
        myListView = (ListView) findViewById(R.id.myListView);


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(DocumentSnapshot doc : task.getResult()){
                                if(doc.get("desc") != null);
                                    desc_list.add(doc.getString("desc"));
                                if(doc.get("sport") != null)
                                    sports_list.add(doc.getString("sport"));
                                if(doc.get("date") != null)
                                    date_list.add(doc.getString("date"));
                                if(doc.get("time") != null)
                                    time_list.add(doc.getString("time"));
                                if(doc.get("venue") != null)
                                    venue_list.add(doc.getString("venue"));
                                if(doc.get("email") != null)
                                    email_list.add(doc.getString("email"));
                                if(doc.get("name") != null)
                                    name_list.add(doc.getString("name"));
                                if(doc.get("hall") != null)
                                    hall_list.add(doc.getString("hall"));
                                if(doc.get("mobile") != null)
                                    mobile_list.add(doc.getString("mobile"));
                            }

                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, "Error Message : " + error, Toast.LENGTH_LONG).show();
                        }
                    }
                });


                sports = sports_list.toArray(new String[0]);
                descriptions = desc_list.toArray(new String[0]);
                dates = date_list.toArray(new String[0]);
                times = time_list.toArray(new String[0]);
                venues = venue_list.toArray(new String[0]);
                emails = email_list.toArray(new String[0]);
                names = name_list.toArray(new String[0]);
                halls = hall_list.toArray(new String[0]);
                mobiles = mobile_list.toArray(new String[0]);

                ItemAdapter itemAdapter = new ItemAdapter(MainActivity.this, sports, descriptions, dates, times, venues, emails, names, halls, mobiles);
                myListView.setAdapter(itemAdapter);

            }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post = new Intent(MainActivity.this, PostActivity.class);
                startActivity(post);
            }
        });

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent settings = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(settings);
                finish();
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
