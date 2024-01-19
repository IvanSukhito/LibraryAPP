package com.example.uaskelompok9.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaskelompok9.MemberListActivity;
import com.example.uaskelompok9.Database;
import com.example.uaskelompok9.MemberUpdateActivity;
import com.example.uaskelompok9.R;
import com.example.uaskelompok9.model.Member;

import java.io.Serializable;
import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private ArrayList<Member> listMember = new ArrayList<>();

    private Activity activity;
    private Database database;



    public MemberAdapter(Activity activity) {
        this.activity = activity;
        database = new Database(activity.getApplicationContext(), "uaskelompok9", null, 1);
    }

    public ArrayList<Member> getListMember() {
        return listMember;
    }

    public void setListMember(ArrayList<Member> item) {
        if (item.size() > 0) {
            this.listMember.clear();
        }
        this.listMember.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);

        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.textViewName.setText(listMember.get(position).getNama());
        holder.textViewEmail.setText(listMember.get(position).getEmail());
        holder.textViewPhone.setText(listMember.get(position).getPhone());

        holder.buttonEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, MemberUpdateActivity.class);
            intent.putExtra("member", (Serializable) listMember.get(position));
            activity.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Hapus Member");
            builder.setMessage("Apakah yakin akan dihapus?");

            builder.setPositiveButton("YA", (dialog, which) -> {
                database.deleteMember(listMember.get(position).getId());
                Toast.makeText(activity, "Member berhasil dihapus!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, MemberListActivity.class);
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
        return listMember.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewName, textViewEmail, textViewPhone;
        final Button buttonEdit, buttonDelete;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNama);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }


    }
}
