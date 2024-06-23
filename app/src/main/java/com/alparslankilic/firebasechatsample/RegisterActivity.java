package com.alparslankilic.firebasechatsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUserName;
    private EditText registerPassword;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private String userName;
    private String userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        registerUserName = (EditText)findViewById(R.id.registerUserName);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        // register buton tiklaninca
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = registerUserName.getText().toString();
                userPassword = registerPassword.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();

                }else{

                    register();
                }

            }
        });
    }

    private void register() {

        mAuth.createUserWithEmailAndPassword(userName,userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }


                });

        mAuth.createUserWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                    Intent  intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Kayıt Başarısız",Toast.LENGTH_SHORT).show();}
            }
        });

    }



}
