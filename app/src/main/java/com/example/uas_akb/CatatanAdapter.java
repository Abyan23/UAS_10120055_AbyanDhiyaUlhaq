package com.example.uas_akb;
//NIM : 10120055
//Nama : Abyan Dhiya Ulhaq
//Kelas : IF-2
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.MyViewHolder> {
    private List<ModelCatatan> mList;
    private Activity activity;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public CatatanAdapter(List<ModelCatatan> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelCatatan data = mList.get(position);
        holder.tv_isi.setText("Isi: " + data.getIsi());
        holder.tv_judul.setText("Judul: " + data.getJudul());

        holder.editButton.setOnClickListener(view -> {
            Intent intent = new Intent(activity, EditCatatanActivity.class);
            intent.putExtra("catatan_key", data.getKey());
            activity.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(view -> {
            // Handle delete action here
            database.child("Catatan").child(data.getKey()).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(activity, "Catatan dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(activity, "Gagal menghapus catatan", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_judul, tv_isi;
        CardView card_hasil;
        Button editButton, deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_isi = itemView.findViewById(R.id.tv_isi);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            card_hasil = itemView.findViewById(R.id.card_hasil);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
