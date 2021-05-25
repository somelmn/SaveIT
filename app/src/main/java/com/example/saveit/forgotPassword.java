package com.example.saveit;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class forgotPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.email);
        resetPasswordButton = (Button) findViewById(R.id.resetPassword);
        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }
         if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             emailEditText.setError("Please enter a valid email!");
             emailEditText.requestFocus();
             return;
         }

         auth.sendPasswordResetEmail(email)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull @NotNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(forgotPassword.this,"Check your email to reset your password!", Toast.LENGTH_LONG).show();
                         }else{
                             Toast.makeText(forgotPassword.this,"Something went wrong! Please try again.", Toast.LENGTH_LONG).show();
                         }

                     }
                 });

    }
}