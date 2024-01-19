package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    LinearLayout layoutBook;
    LinearLayout layoutMember;
    LinearLayout layoutPinjam, layoutReturn;


    ImageView logout;
   // Button buttonBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutBook = findViewById(R.id.LayoutBook);
        layoutMember = findViewById(R.id.LayoutMembers);
        layoutPinjam = findViewById(R.id.LayoutPinjam);
        layoutReturn = findViewById(R.id.LayoutReturn);
        logout = findViewById(R.id.imageLogout);

        //menu book
        layoutBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, BookListActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SplashActivity.class));
            }
        });
        layoutMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MemberListActivity.class));
            }
        });
        layoutPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, BorrowBookActivity.class));
            }
        });
        layoutReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ReturnBookActivity.class));
            }
        });


    }
}