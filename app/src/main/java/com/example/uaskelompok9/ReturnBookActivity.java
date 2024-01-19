package com.example.uaskelompok9;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;


import android.os.Bundle;

public class ReturnBookActivity extends AppCompatActivity {

    Database dbHelper;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);

        ListView listViewBooks = findViewById(R.id.listViewBooks);
//
//
        // Ambil data dari database menggunakan metode getAllBorrowedBooks()
        Cursor cursor = getAllBorrowedBooks();

        // Buat adapter untuk ListView menggunakan SimpleCursorAdapter
        String[] fromColumns = {"_id","member_name", "book_title", "status","borrow_date", "return_date"};
        int[] toViews = {R.id.textViewBorrowId, R.id.textViewMemberName, R.id.textViewBookTitle,R.id.textViewStatus, R.id.textViewBorrowDate, R.id.textViewReturnDate};

        CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item_book, cursor, fromColumns, toViews, 0);


        // Atur adapter ke ListView
        listViewBooks.setAdapter(adapter);

        btnUpdate = findViewById(R.id.buttonReturnBook);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic for updating the status for each item
                updateStatusForSelectedBooks();
            }
        });

    }
    private Cursor getAllBorrowedBooks() {

        dbHelper = new Database(getApplicationContext(), "uaskelompok9", null, 1);
        return dbHelper.getListBooks();
    }
private void updateStatusForSelectedBooks() {
    ListView listViewBooks = findViewById(R.id.listViewBooks);

    // Get the selected item position in the ListView
    int selectedPosition = listViewBooks.getCheckedItemPosition();

    // Ensure that an item is selected
    if (selectedPosition != ListView.INVALID_POSITION) {
        // Get the adapter associated with the ListView
        CursorAdapter cursorAdapter = (CursorAdapter) listViewBooks.getAdapter();

        // Get the Cursor for the selected item
        Cursor cursor = (Cursor) cursorAdapter.getItem(selectedPosition);

        // Extract information from the Cursor
        long bookId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        String currentStatus = cursor.getString(cursor.getColumnIndexOrThrow("status"));

        // Update the status (example: change 'Dipinjam' to 'Dikembalikan')
        String newStatus = (currentStatus.equals("Dipinjam")) ? "Dikembalikan" : currentStatus;

        // Update the status for the selected item in the database
        dbHelper.updateStatusForBook(bookId, newStatus);

        // Requery the data from the database
        Cursor newCursor = dbHelper.getListBooks();

        // Update the adapter with the new data
        cursorAdapter.changeCursor(newCursor);

        // Notify the adapter that the data set has changed
        cursorAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Returm Book Success.", Toast.LENGTH_SHORT).show();
    } else {
        // Handle the case where no item is selected
        Toast.makeText(this, "Please select an item to update.", Toast.LENGTH_SHORT).show();
    }
}




}
