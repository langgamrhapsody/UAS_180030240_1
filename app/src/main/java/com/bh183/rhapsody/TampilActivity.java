package com.bh183.rhapsody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgLirik;
    private TextView tvJudul, tvTanggal, tvAlbum, tvPenyanyi, tvIsiLirik;
    private String linkLirik;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgLirik = findViewById(R.id.iv_lirik);
        tvJudul = findViewById(R.id.tv_judul);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvAlbum = findViewById(R.id.tv_album);
        tvPenyanyi = findViewById(R.id.tv_penyanyi);
        tvIsiLirik = findViewById(R.id.tv_isi_lirik);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvAlbum.setText(terimaData.getStringExtra("ALBUM"));
        tvPenyanyi.setText(terimaData.getStringExtra("PENYANYI"));
        tvIsiLirik.setText(terimaData.getStringExtra("ISI_LIRIK"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgLirik.setImageBitmap(bitmap);
            imgLirik.setContentDescription(imgLocation);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal Mengambil Gambar Dari Media Penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkLirik = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==  R.id.item_bagikan){
            Intent bagikanLirik = new Intent(Intent.ACTION_SEND);
            bagikanLirik.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanLirik.putExtra(Intent.EXTRA_SUBJECT, linkLirik);
            bagikanLirik.setType("text/plain");
            startActivity(Intent.createChooser(bagikanLirik, "Bagikan Lirik"));
        }

        return super.onOptionsItemSelected(item);
    }
}
