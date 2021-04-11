package com.example.saveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends AppCompatActivity {

    EditText username, name, email, password;
    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(username.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setEmail(email.getText().toString());
                userEntity.setName(name.getText().toString());
                if(validateInout(userEntity)){
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"User Registered!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();

                }else {
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
    private Boolean validateInout(UserEntity userEntity){
        if(userEntity.getName().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                userEntity.getName().isEmpty()) {
            return false;
        }
        return true;
    }
}