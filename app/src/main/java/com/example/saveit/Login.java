package com.example.saveit;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userIdText = email.getText().toString();
                String passwordText = password.getText().toString();
                if(userIdText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                } else{
                    UserDatabase userDatabase = UserDatabase.getUserDatabase((getApplicationContext()));
                    UserDao userDao =userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity =userDao.login(userIdText, passwordText);
                            if (userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else{
                                String name = userEntity.name;
                                startActivity(new Intent(Login.this, MainActivity.class)
                                        .putExtra("name", name));
                            }
                        }
                    }).start();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}