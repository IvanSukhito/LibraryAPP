package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uaskelompok9.model.Book;
import com.example.uaskelompok9.model.Member;

public class MemberUpdateActivity extends AppCompatActivity {

    private Database database;
    private EditText edName, edPhone, edEmail;
    private Button buttonUpdate, buttonList;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_update);

        database = new Database(getApplicationContext(),"uaskelompok9",null,1);

        edName      = findViewById(R.id.editTextNamaMember);
        edPhone    = findViewById(R.id.editTextPhone);
        edEmail = findViewById(R.id.editTextEmail);
        buttonUpdate      = findViewById(R.id.buttonUpdate);
        buttonList        = findViewById(R.id.buttonList);

        Intent intent = getIntent();
        member = (Member) intent.getSerializableExtra("member");

        edName.setText(member.getNama());
        edPhone.setText(member.getPhone());
        edEmail.setText(member.getEmail());

        buttonUpdate.setOnClickListener((View v) -> {
            if(edName.getText().toString().isEmpty()) {
                Toast.makeText(MemberUpdateActivity.this, "Nama member harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(edPhone.getText().toString().isEmpty()) {
                Toast.makeText(MemberUpdateActivity.this, "Phone member harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(edEmail.getText().toString().isEmpty()) {
                Toast.makeText(MemberUpdateActivity.this, "Email harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                database.updateMember(member.getId(),  edName.getText().toString(), edPhone.getText().toString(), edEmail.getText().toString());
                Toast.makeText(MemberUpdateActivity.this, "Informasi buku berhasil diubah!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonList.setOnClickListener(v -> {

            startActivity(new Intent(MemberUpdateActivity.this, MemberListActivity.class));
        });
    }
}