package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button accountbtn;
    private Button loginbtn;
    private ProgressBar mprogress;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password_register);
        confirm_password = (EditText) findViewById(R.id.confirm_password_register);
        accountbtn = (Button) findViewById(R.id.accountbtn_register);
        loginbtn = (Button) findViewById(R.id.loginbtn_register);
        mprogress = (ProgressBar) findViewById(R.id.progressBar_register);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Registration.this, LoginActivity.class);
                startActivity(login);
            }
        });

        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString();
                String pass = password.getText().toString();
                String confirm_pass = confirm_password.getText().toString();

                if(!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)){

                    if(pass.equals(confirm_pass)){

                        mprogress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Intent intent = new Intent(Registration.this, SetupActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else
                                {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(Registration.this, "Error : "+error, Toast.LENGTH_LONG).show();

                                }
                                mprogress.setVisibility(View.INVISIBLE);
                            }
                        });

                    }else{

                        Toast.makeText(Registration.this, "Confirm Password and Password doesn't match", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
            gotoMain();
    }

    private void gotoMain() {
        Intent main = new Intent(Registration.this, MainActivity.class);
        startActivity(main);
        finish();
    }
}
