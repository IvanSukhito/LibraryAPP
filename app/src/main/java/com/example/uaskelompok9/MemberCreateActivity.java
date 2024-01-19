package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MemberCreateActivity extends AppCompatActivity {

    Database database;
    EditText edNama, edEmail, edPhone, edPassword, edValidatePassword;
    Button buttonCreate, buttonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_create);

        database = new Database(getApplicationContext(),"uaskelompok9",null,1);

        edNama      = findViewById(R.id.editTextName);
        edEmail    = findViewById(R.id.editTextEmail);
        edPhone = findViewById(R.id.editTextPhone);
        edPassword      = findViewById(R.id.editTextPassword);
        edValidatePassword     = findViewById(R.id.editTextValidatePassword);
        buttonCreate      = findViewById(R.id.buttonCreate);
        buttonList        = findViewById(R.id.buttonList);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edNama.getText().toString();
                String phone = edPhone.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String ValidatePassword = edValidatePassword.getText().toString();


                if(email.length() == 0 || phone.length() == 0 || name.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please Fill Personal Data", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(ValidatePassword)) {
                    Toast.makeText(getApplicationContext(),"Password not matched", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0 || ValidatePassword.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Please Fill Your Password", Toast.LENGTH_SHORT).show();
                } else{
                    if(isValid(password)){
                        database.createMember(name, phone, email, password);
                        Toast.makeText(getApplicationContext(),"Add Member Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MemberCreateActivity.this, MemberListActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Password must contain at least 8 characters, having letter, digit, special simbols", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
    public static boolean isValid(String passwordhere){
        int f1=0, f2=0, f3=0;
        if(passwordhere.length() < 8){
            return false;
        } else{
            for (int p=0; p < passwordhere.length(); p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1 = 1;
                }
            }
            for (int r=0; r < passwordhere.length(); r++){
                if(Character.isDigit(passwordhere.charAt(r))){
                    f2=1;
                }
            }
            for (int s=0; s < passwordhere.length(); s++){
                char c = passwordhere.charAt(s);
                if(c >= 33 && c<=46 || c==64){
                    f3=1;
                }
            }
            if(f1==1 && f2==1 && f3==1)
                return true;
            return false;
        }
    }
}