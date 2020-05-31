package com.bh183.rhapsody;

import java.util.Date;

public class Lirik {

    private int idLirik;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String album;
    private String penyanyi;
    private String isiLirik;
    private String link;

    public Lirik(int idLirik, String judul, Date tanggal, String gambar, String album, String penyanyi, String isiLirik, String link) {
        this.idLirik = idLirik;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.album = album;
        this.penyanyi = penyanyi;
        this.isiLirik = isiLirik;
        this.link = link;
    }

    public int getIdLirik() {
        return idLirik;
    }

    public void setIdLirik(int idLirik) {
        this.idLirik = idLirik;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPenyanyi() {
        return penyanyi;
    }

    public void setPenyanyi(String penyanyi) {
        this.penyanyi = penyanyi;
    }

    public String getIsiLirik() {
        return isiLirik;
    }

    public void setIsiLirik(String isiLirik) {
        this.isiLirik = isiLirik;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
