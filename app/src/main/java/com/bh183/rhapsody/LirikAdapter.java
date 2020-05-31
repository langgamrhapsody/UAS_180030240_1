package com.bh183.rhapsody;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LirikAdapter extends RecyclerView.Adapter<LirikAdapter.LirikViewHolder> {

    private Context context;
    private ArrayList<Lirik> dataLirik;
    private SimpleDateFormat sdFormat= new SimpleDateFormat("dd/MM/yyyy");

    public LirikAdapter(Context context, ArrayList<Lirik> dataLirik) {
        this.context = context;
        this.dataLirik = dataLirik;
    }

    @NonNull
    @Override
    public LirikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_lirik, parent, false);
        return new LirikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LirikViewHolder holder, int position) {
        Lirik tempLirik = dataLirik.get(position);
        holder.idLirik = tempLirik.getIdLirik();
        holder.tvJudul.setText(tempLirik.getJudul());
        holder.tvHeadline.setText(tempLirik.getIsiLirik());
        holder.tanggal = sdFormat.format(tempLirik.getTanggal());
        holder.gambar = tempLirik.getGambar();
        holder.album = tempLirik.getAlbum();
        holder.penyanyi = tempLirik.getPenyanyi();
        holder.link = tempLirik.getLink();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgLirik.setImageBitmap(bitmap);
            holder.imgLirik.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal Mengambil Gambar Dari Media Penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataLirik.size();
    }

    public class LirikViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{

        private ImageView imgLirik;
        private TextView tvJudul, tvHeadline;
        private int idLirik;
        private String tanggal, gambar, album, penyanyi, link;

        public LirikViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLirik = itemView.findViewById(R.id.iv_lirik);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHeadline = itemView.findViewById(R.id.tv_headline);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent bukaLirik = new Intent(context, TampilActivity.class);
            bukaLirik.putExtra("ID", idLirik);
            bukaLirik.putExtra("JUDUL", tvJudul.getText().toString());
            bukaLirik.putExtra("TANGGAL", tanggal);
            bukaLirik.putExtra("GAMBAR", gambar);
            bukaLirik.putExtra("ALBUM", album);
            bukaLirik.putExtra("PENYANYI", penyanyi);
            bukaLirik.putExtra("ISI_LIRIK", tvHeadline.getText().toString());
            bukaLirik.putExtra("LINK", link);
            context.startActivity(bukaLirik);

        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idLirik);
            bukaInput.putExtra("JUDUL", tvJudul.getText().toString());
            bukaInput.putExtra("TANGGAL", tanggal);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("ALBUM", album);
            bukaInput.putExtra("PENYANYI", penyanyi);
            bukaInput.putExtra("ISI_LIRIK", tvHeadline.getText().toString());
            bukaInput.putExtra("LINK", link);
            context.startActivity(bukaInput);
            return true;
        }
    }
}
