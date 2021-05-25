package com.example.saveit;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView register,loginUser, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener( this);

        loginUser= (Button) findViewById(R.id.login);
        loginUser.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener( this);
        

        




    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.login:
                loginUser();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, forgotPassword.class));
                break;


        }


    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Password has to be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){


                            startActivity(new Intent(Login.this, MainActivity.class));
                        }else{
                                user.sendEmailVerification();
                                Toast.makeText(Login.this,"Check your email to verify your account", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(Login.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}
