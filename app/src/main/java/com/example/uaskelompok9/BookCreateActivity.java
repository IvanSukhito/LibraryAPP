package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookCreateActivity extends AppCompatActivity {

    Database database;
    private EditText editTextName, editTextAuthor, editTextPublisher, editTextYear, editTextStock;
    private Button buttonCreate, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_create);

        database = new Database(getApplicationContext(),"uaskelompok9",null,1);

        editTextName      = findViewById(R.id.editTextName);
        editTextAuthor    = findViewById(R.id.editTextAuthor);
        editTextPublisher = findViewById(R.id.editTextPublisher);
        editTextYear      = findViewById(R.id.editTextYear);
        editTextStock     = findViewById(R.id.editTextStock);
        buttonCreate      = findViewById(R.id.buttonCreate);
        buttonList        = findViewById(R.id.buttonList);

        buttonCreate.setOnClickListener(v -> {
            if(editTextName.getText().toString().isEmpty()) {
                Toast.makeText(BookCreateActivity.this, "Nama buku harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextAuthor.getText().toString().isEmpty()) {
                Toast.makeText(BookCreateActivity.this, "Penulis buku harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextPublisher.getText().toString().isEmpty()) {
                Toast.makeText(BookCreateActivity.this, "Penerbit harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextYear.getText().toString().isEmpty()) {
                Toast.makeText(BookCreateActivity.this, "Tahun terbit harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextStock.getText().toString().isEmpty()) {
                Toast.makeText(BookCreateActivity.this, "Stok harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                database.createBook(editTextName.getText().toString(), editTextAuthor.getText().toString(), editTextPublisher.getText().toString(), editTextYear.getText().toString(), editTextStock.getText().toString());
                editTextName.setText("");
                editTextAuthor.setText("");
                editTextPublisher.setText("");
                editTextYear.setText("");
                editTextStock.setText("");
                Toast.makeText(BookCreateActivity.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookCreateActivity.this, BookListActivity.class));
            }
        });

        buttonList.setOnClickListener(v -> {
            Intent intent = new Intent(BookCreateActivity.this, BookListActivity.class);
            startActivity(intent);
        });
    }
}