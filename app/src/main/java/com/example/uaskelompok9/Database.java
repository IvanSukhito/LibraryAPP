package com.example.uaskelompok9;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.uaskelompok9.model.Book;
import com.example.uaskelompok9.model.Member;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "create table users(id integer primary key autoincrement, nama text,phone text, email text, password text, role text) ";
        String query2 = "create table books(id integer primary key autoincrement, name text, author text, publisher text, year text, stock int) ";
        String query3 = "create table borrow_books(id integer primary key autoincrement, users_id integer, books_id integer,member_name text, book_title text, borrow_date date,return_date date, status string)";
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query2 = "DROP TABLE IF EXISTS books";
        db.execSQL(query2);
        onCreate(db);
    }

    public void register(String nama, String phone, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("role", "admin");
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
        //nama tablenya users
    }

    public int login(String email, String password) {

        int result = 0;
        String str[] = new String[2];
        str[0] = email;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("select * from users where email=? and password=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        return result;
    }

    public boolean createBook(String name, String author, String publisher, String year, String stock) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("author", author);
        cv.put("publisher", publisher);
        cv.put("year", year);
        cv.put("stock", stock);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean create = db.insert("books", null, cv) > 0;
        db.close();
        return create;
    }

    @SuppressLint("Range")
    public ArrayList<Book> getAllBook() {
        ArrayList<Book> books = new ArrayList<Book>();
        String selectQuery = "SELECT * FROM books";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(c.getInt(c.getColumnIndex("id")));
                book.setName(c.getString(c.getColumnIndex("name")));
                book.setAuthor(c.getString(c.getColumnIndex("author")));
                book.setPublisher(c.getString(c.getColumnIndex("publisher")));
                book.setYear(c.getString(c.getColumnIndex("year")));
                book.setStock(c.getString(c.getColumnIndex("stock")));
                books.add(book);
            } while (c.moveToNext());
        }
        return books;
    }

    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("books", "id = ?", new String[]{String.valueOf(id)});
    }

    public int updateBook(int id, String name, String author, String publisher, String year, String stock) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("author", author);
        cv.put("publisher", publisher);
        cv.put("year", year);
        cv.put("stock", stock);
        SQLiteDatabase db = this.getWritableDatabase();
        int update = db.update("books", cv, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return update;
    }

    public boolean createMember(String nama, String phone, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("role", "member");
        SQLiteDatabase db = this.getWritableDatabase();
        boolean create = db.insert("users", null, cv) > 0;
        db.close();
        return create;
    }

    @SuppressLint("Range")
    public ArrayList<Member> getAllMember() {
        ArrayList<Member> members = new ArrayList<Member>();
        String selectQuery = "SELECT * FROM users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Member member = new Member();
                member.setId(c.getInt(c.getColumnIndex("id")));
                member.setNama(c.getString(c.getColumnIndex("nama")));
                member.setPhone(c.getString(c.getColumnIndex("phone")));
                member.setEmail(c.getString(c.getColumnIndex("email")));
                members.add(member);
            } while (c.moveToNext());
        }
        return members;
    }

    public void deleteMember(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "id = ?", new String[]{String.valueOf(id)});
    }

    public int updateMember(int id, String nama, String phone, String email) {
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("role", "admin");
        SQLiteDatabase db = this.getWritableDatabase();
        int update = db.update("users", cv, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return update;
    }

    public long addBorrow(String memberName, String bookName, String borrowDate, String returnDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        long memberId = getMemberID(memberName);
        long booksId = getBookID(bookName);
        ContentValues values = new ContentValues();
        values.put("users_id", memberId);
        values.put("books_id", booksId);
        values.put("member_name", memberName);
        values.put("book_title", bookName);
        values.put("borrow_date", borrowDate);
        values.put("return_date", returnDate);
        values.put("status", "Dipinjam");

        long borrowID = db.insert("borrow_books", null, values);

        if (borrowID != -1){
            updateStock(booksId, "pinjam");
        }
        db.close();



        return borrowID;

    }

    @SuppressLint("Range")
    private long getMemberID(String memberName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE nama=?", new String[]{memberName});

        long memberID = -1;

        if (cursor.moveToFirst()) {
            memberID = cursor.getLong(cursor.getColumnIndex("id"));
        }

        cursor.close();
        return memberID;
    }

    @SuppressLint("Range")
    private long getBookID(String bookTitle) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM books WHERE name=?", new String[]{bookTitle});

        long bookID = -1;

        if (cursor.moveToFirst()) {
            bookID = cursor.getLong(cursor.getColumnIndex("id"));
        }

        cursor.close();
        return bookID;
    }

    public Cursor searchMember(String member) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT nama FROM users WHERE nama LIKE ?";
        String[] selectionArgs = new String[]{"%" + member + "%"};

        return db.rawQuery(query, selectionArgs);
    }

    public Cursor searchBook(String bookname) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name FROM books WHERE name LIKE ?";
        String[] selectionArgs = new String[]{"%" + bookname + "%"};

        return db.rawQuery(query, selectionArgs);

    }

    @SuppressLint("Range")


    public Cursor getListBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id as _id, member_name, book_title, status, borrow_date, return_date " +
                "FROM borrow_books " +
                "WHERE status = 'Dipinjam'", null);
    }
    public void updateStatusForBook(long bookId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(bookId)};

        long success = db.update("borrow_books", values, whereClause, whereArgs);
        if (success != -1){
            updateStock(bookId, "kembali");
        }
    }

    @SuppressLint("Range")
    private void updateStock(long bookId, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT stock from books where id=?", new String[]{String.valueOf(bookId)});
        int currStock = 0;
        if (cursor.moveToFirst()) {

            currStock = cursor.getInt(cursor.getColumnIndex("stock"));
        }

        cursor.close();
        if (currStock > 0) {
            ContentValues updateValues = new ContentValues();
            if(status == "pinjam"){
                updateValues.put("stock", currStock - 1);
            } else if (status == "kembali") {
                updateValues.put("stock", currStock + 1);
            }

            db.update("books", updateValues, "id=?", new String[]{String.valueOf(bookId)});
        }


    }


}
