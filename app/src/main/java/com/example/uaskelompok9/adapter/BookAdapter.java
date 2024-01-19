package com.example.uaskelompok9.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uaskelompok9.BookListActivity;
import com.example.uaskelompok9.BookUpdateActivity;
import com.example.uaskelompok9.Database;
import com.example.uaskelompok9.R;
import com.example.uaskelompok9.model.Book;
import java.io.Serializable;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> listBook = new ArrayList<>();
    private Activity activity;
    private Database database;

    public BookAdapter(Activity activity) {
        this.activity = activity;
        database = new Database(activity.getApplicationContext(), "uaskelompok9", null, 1);
    }

    public ArrayList<Book> getListBook() {
        return listBook;
    }

    public void setListBook(ArrayList<Book> item) {
        if (item.size() > 0) {
            this.listBook.clear();
        }
        this.listBook.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.textViewName.setText(listBook.get(position).getName());
        holder.textViewAuthor.setText(listBook.get(position).getAuthor());
        holder.textViewPublisher.setText(listBook.get(position).getPublisher());

        holder.buttonEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, BookUpdateActivity.class);
            intent.putExtra("book", (Serializable) listBook.get(position));
            activity.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Hapus Buku");
            builder.setMessage("Apakah yakin akan dihapus?");

            builder.setPositiveButton("YA", (dialog, which) -> {
                database.deleteBook(listBook.get(position).getId());
                Toast.makeText(activity, "Buku berhasil dihapus!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, BookListActivity.class);
                activity.startActivity(myIntent);
                activity.finish();
            });

            builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewName, textViewAuthor, textViewPublisher;
        final Button buttonEdit, buttonDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewPublisher = itemView.findViewById(R.id.textViewPublisher);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
