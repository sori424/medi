package com.example.medi;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<Medicine> medList = new ArrayList<>();
    private DatabaseHelper db;
    ListView medicineListView;
    HistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        medicineListView = findViewById(R.id.list);

        db = new DatabaseHelper(this);
        medList.addAll(db.getAllMed());

        if(medList.isEmpty() == false){
            medicineListView.setAdapter(adapter);
        }

    }

}
