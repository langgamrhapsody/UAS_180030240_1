package com.bh183.rhapsody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Lirik> dataLirik = new ArrayList<>();
    private RecyclerView rvLirik;
    private LirikAdapter lirikAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLirik = findViewById(R.id.rv_tampil_lirik);
        dbHandler = new DatabaseHandler(this);
        tampilDataLirik();
    }

    private void tampilDataLirik(){
        dataLirik = dbHandler.getAllLirik();
        lirikAdapter = new LirikAdapter(this, dataLirik);
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(MainActivity.this);
        rvLirik.setLayoutManager(layManager);
        rvLirik.setAdapter(lirikAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.item_menu_tambah){
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert");
            startActivity(bukaInput);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDataLirik();
    }
}
