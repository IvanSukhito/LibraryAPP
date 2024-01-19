package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uaskelompok9.adapter.BookAdapter;
import com.example.uaskelompok9.model.Book;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ArrayList<Book> books;
    private Database database;

    Button buttonCreate;
    TextView TV;

    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        recyclerView = (RecyclerView) findViewById(R.id.rview);
        adapter = new BookAdapter(this);
        database = new Database(getApplicationContext(), "uaskelompok9", null, 1);
        books = database.getAllBook();
        adapter.setListBook(books);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        logout = findViewById(R.id.imageLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookListActivity.this, SplashActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        books = database.getAllBook();
        adapter.setListBook(books);
        adapter.notifyDataSetChanged();

        TV = findViewById(R.id.textCreateBook);

        TV.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, BookCreateActivity.class);
            startActivity(intent);
        });
    }
}