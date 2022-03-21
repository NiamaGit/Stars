package com.example.stars;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.stars.adapter.StarAdapter;
import com.example.stars.beans.Star;
import com.example.stars.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recycle;
    private List<Star> stars;
    private StarAdapter starAdapter = null;
    private StarService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recycle = findViewById(R.id.recycle_view);

        starAdapter = new StarAdapter(this, service.findAll());
        recycle.setAdapter(starAdapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
    }

    public void init(){
        service.create(new Star("Tom Holland", "https://i.ibb.co/23Tc7Q6/tom.png", 3.5f));
        service.create(new Star("Zendaya", "https://i.ibb.co/s2bs2P3/zind.jpg", 3.5f));
        service.create(new Star("Timothée Chalamet", "https://i.ibb.co/MhCWkrS/Tim.png", 3.5f));
        service.create(new Star("Tom Hardy", "https://i.ibb.co/5r2QKnm/hardy.png", 3.5f));
        service.create(new Star("Michael B. Jordan", "https://i.ibb.co/0rrym6z/mbj.jpg", 3.5f));
        service.create(new Star("Chadwick Boseman", "https://i.ibb.co/CHf3nKY/chad.png", 3.5f));

        service.create(new Star("Rayan Reynolds", "https://i.ibb.co/5nCrWkX/rayan.jpg", 3.5f));
        service.create(new Star("Balke Lively", "https://i.ibb.co/d7m0kB1/blake-lively.jpg", 3.5f));
        service.create(new Star("Timothée Chalamet", "https://i.ibb.co/MhCWkrS/Tim.png", 3.5f));
        service.create(new Star("Tom Hardy", "https://i.ibb.co/5r2QKnm/hardy.png", 3.5f));
        service.create(new Star("Michael B. Jordan", "https://i.ibb.co/0rrym6z/mbj.jpg", 3.5f));
        service.create(new Star("Chadwick Boseman", "https://i.ibb.co/CHf3nKY/chad.png", 3.5f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }



}