package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uaskelompok9.adapter.MemberAdapter;
import com.example.uaskelompok9.model.Member;

import java.util.ArrayList;

public class MemberListActivity extends AppCompatActivity {

    private ArrayList<Member> members;
    TextView TV;

    Database database;
    RecyclerView recyclerView;

    MemberAdapter memberAdapter;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        recyclerView = findViewById(R.id.rviewMember);

        memberAdapter = new MemberAdapter(this);
        database = new Database(getApplicationContext(), "uaskelompok9", null, 1);
        members = database.getAllMember();
        memberAdapter.setListMember(members);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MemberListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memberAdapter);

        logout = findViewById(R.id.imageLogout);

        //menu book

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MemberListActivity.this, SplashActivity.class));
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        members = database.getAllMember();
        memberAdapter.setListMember(members);
        memberAdapter.notifyDataSetChanged();

        TV = findViewById(R.id.textCreateMember);
        TV.setOnClickListener(v -> {
            Intent intent = new Intent(MemberListActivity.this, MemberCreateActivity.class);
            startActivity(intent);
        });
    }
}