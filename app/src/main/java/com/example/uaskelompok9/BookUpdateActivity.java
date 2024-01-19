package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uaskelompok9.model.Book;

public class BookUpdateActivity extends AppCompatActivity {

    private Database database;
    private EditText editTextName, editTextAuthor, editTextPublisher, editTextYear, editTextStock;
    private Button buttonUpdate, buttonList;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_update);

        database = new Database(getApplicationContext(),"uaskelompok9",null,1);

        editTextName      = findViewById(R.id.editTextName);
        editTextAuthor    = findViewById(R.id.editTextAuthor);
        editTextPublisher = findViewById(R.id.editTextPublisher);
        editTextYear      = findViewById(R.id.editTextYear);
        editTextStock     = findViewById(R.id.editTextStock);
        buttonUpdate      = findViewById(R.id.buttonUpdate);
        buttonList        = findViewById(R.id.buttonList);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");

        editTextName.setText(book.getName());
        editTextAuthor.setText(book.getAuthor());
        editTextPublisher.setText(book.getPublisher());
        editTextYear.setText(book.getYear());
        editTextStock.setText(book.getStock());

        buttonUpdate.setOnClickListener((View v) -> {
            if(editTextName.getText().toString().isEmpty()) {
                Toast.makeText(BookUpdateActivity.this, "Nama buku harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextAuthor.getText().toString().isEmpty()) {
                Toast.makeText(BookUpdateActivity.this, "Penulis buku harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextPublisher.getText().toString().isEmpty()) {
                Toast.makeText(BookUpdateActivity.this, "Penerbit harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextYear.getText().toString().isEmpty()) {
                Toast.makeText(BookUpdateActivity.this, "Tahun terbit harus diisi!", Toast.LENGTH_SHORT).show();
            } else if(editTextStock.getText().toString().isEmpty()) {
                Toast.makeText(BookUpdateActivity.this, "Stok harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                database.updateBook(book.getId(), editTextName.getText().toString(), editTextAuthor.getText().toString(), editTextPublisher.getText().toString(), editTextYear.getText().toString(), editTextStock.getText().toString());
                Toast.makeText(BookUpdateActivity.this, "Informasi buku berhasil diubah!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonList.setOnClickListener(v -> {

            startActivity(new Intent(BookUpdateActivity.this, BookListActivity.class));
        });
    }
}