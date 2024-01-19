package com.example.uaskelompok9;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class BorrowBookActivity extends AppCompatActivity {

    private Database dbHelper;

    Button btnSubmit, btnBack;
    EditText editTextBorrowDate, editTextReturnDate;
    private ArrayAdapter<String> memberAdapter;
    private ArrayAdapter bookAdapter;
    AutoCompleteTextView autoCompleteMember,autoCompleteBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);
        dbHelper = new Database(getApplicationContext(), "uaskelompok9", null, 1);
        btnSubmit = findViewById(R.id.buttonBorrow);
        btnBack = findViewById(R.id.buttonHome);
        editTextBorrowDate = findViewById(R.id.editTextBorrowDate);
        editTextReturnDate = findViewById(R.id.editTextReturnDate);
        // Inisialisasi AutoCompleteTextView untuk pencarian anggota
        autoCompleteMember = findViewById(R.id.autoCompleteMember);
        memberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getMemberSuggestions());
        autoCompleteMember.setAdapter(memberAdapter);

        // Inisialisasi AutoCompleteTextView untuk pencarian buku
        autoCompleteBook = findViewById(R.id.autoCompleteBook);
        bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getBookSuggestions());
        autoCompleteBook.setAdapter(bookAdapter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memberName = autoCompleteMember.getText().toString();
                String bookTitle = autoCompleteBook.getText().toString();
                String borrowDate = editTextBorrowDate.getText().toString();
                String returnDate = editTextReturnDate.getText().toString();
                //startActivity(new Intent(BorrowBookActivity.this, LoginActivity.class));
                //dbHelper.
                if (memberName.length() == 0 || bookTitle.length() == 0 || borrowDate.length() == 0 || returnDate.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Fill All Required Data", Toast.LENGTH_SHORT).show();
                } else {

                    dbHelper.addBorrow(memberName, bookTitle, borrowDate, returnDate);
                    //db.register(name, phone, email, password);
                    Toast.makeText(getApplicationContext(), "Insert Data Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BorrowBookActivity.this, HomeActivity.class));


                }
            }


        });
    }


    //DatePicker Tgl Peminjaman
    public void showDatePickerDialog(View view) {
        EditText editText = (EditText) view;
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                editText.setText(dateFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(
                this,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

@SuppressLint("Range")
private String[] getMemberSuggestions() {
    // Ambil hasil pencarian anggota dari database
    Cursor cursor = dbHelper.searchMember("");
    String[] suggestions = new String[cursor.getCount()];

    if (cursor.moveToFirst()) {
        int i = 0;
        do {
            suggestions[i] = cursor.getString(cursor.getColumnIndex("nama"));
            i++;
        } while (cursor.moveToNext());
    }

    cursor.close();
    return suggestions;
}

    @SuppressLint("Range")
    private String[] getBookSuggestions() {
        // Ambil hasil pencarian buku dari database
        Cursor cursor = dbHelper.searchBook("");
        String[] suggestions = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                suggestions[i] = cursor.getString(cursor.getColumnIndex("name"));
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return suggestions;
    }


}