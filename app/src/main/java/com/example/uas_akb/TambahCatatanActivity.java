package com.example.uas_akb;
//NIM : 10120055
//Nama : Abyan Dhiya Ulhaq
//Kelas : IF-2
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class TambahCatatanActivity extends AppCompatActivity {


    private EditText judulEditText;
    private EditText isiEditText;
    private Button tambahButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);

        judulEditText = (EditText) findViewById(R.id.judulcatatan);
        isiEditText = (EditText) findViewById(R.id.isicatatan);
        tambahButton = (Button) findViewById(R.id.tambahCatatanbtn);

        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = judulEditText.getText().toString();
                String isi = isiEditText.getText().toString();

                if (judul.isEmpty()){
                    judulEditText.setError("Judul Tidak Boleh Kosong!");
                    return;
                }
                if (isi.isEmpty()){
                    isiEditText.setError("Isi Tidak Boleh Kosong!");
                    return;
                }
                addCatatanToDB(judul,isi);
            }
        });

    }
    private void addCatatanToDB(String Judul, String Isi) {
        HashMap<String, Object> catatanHashMap= new HashMap<>();
        catatanHashMap.put("judul",Judul);
        catatanHashMap.put("isi",Isi);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference catatanRef = database.getReference("Catatan");

        String key = catatanRef.push().getKey();
        catatanHashMap.put("key",key);

        catatanRef.child(key).setValue(catatanHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(TambahCatatanActivity.this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                judulEditText.getText().clear();
                isiEditText.getText().clear();
                startActivity(new Intent(TambahCatatanActivity.this, MainActivity.class));
                finish();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TambahCatatanActivity.this, "Gagal Menyimpan Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}