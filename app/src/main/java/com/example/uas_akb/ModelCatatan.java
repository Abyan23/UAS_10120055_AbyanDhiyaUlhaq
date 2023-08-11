package com.example.uas_akb;
//NIM : 10120055
//Nama : Abyan Dhiya Ulhaq
//Kelas : IF-2
public class ModelCatatan {
    private String judul;
    private String isi;
    private String key;

    public ModelCatatan(){

    }

    public ModelCatatan(String judul, String isi) {
        this.judul = judul;
        this.isi = isi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
