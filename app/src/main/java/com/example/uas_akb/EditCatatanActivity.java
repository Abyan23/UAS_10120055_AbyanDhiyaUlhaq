package com.example.uas_akb;
//NIM : 10120055
//Nama : Abyan Dhiya Ulhaq
//Kelas : IF-2
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCatatanActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextContent;
    private Button buttonSave;

    private DatabaseReference database;
    private String catatanKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_catatan);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        database = FirebaseDatabase.getInstance().getReference();

        catatanKey = getIntent().getStringExtra("catatan_key");

        database.child("Catatan").child(catatanKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ModelCatatan catatan = snapshot.getValue(ModelCatatan.class);
                    if (catatan != null) {
                        editTextTitle.setText(catatan.getJudul());
                        editTextContent.setText(catatan.getIsi());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        buttonSave.setOnClickListener(view -> {
            String newTitle = editTextTitle.getText().toString();
            String newContent = editTextContent.getText().toString();

            if (!TextUtils.isEmpty(newTitle) && !TextUtils.isEmpty(newContent)) {
                database.child("Catatan").child(catatanKey).child("judul").setValue(newTitle);
                database.child("Catatan").child(catatanKey).child("isi").setValue(newContent)
                        .addOnSuccessListener(aVoid -> {
                            // Data updated successfully
                            Toast.makeText(EditCatatanActivity.this, "Catatan diperbarui", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                            Toast.makeText(EditCatatanActivity.this, "Gagal memperbarui catatan", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
