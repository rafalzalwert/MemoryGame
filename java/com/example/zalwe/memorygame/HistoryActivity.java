package com.example.zalwe.memorygame;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;

    Button buttonClear;

    DbOperations dbOperations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DbHelper dbHelper = new DbHelper(getBaseContext());
        dbOperations = new DbOperations(dbHelper);

        listView = findViewById(R.id.list);
        buttonClear = findViewById(R.id.buttonClear);
        List<Image> imageArrayList = dbOperations.getImageFromDb();

        final HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this,
                R.layout.history_adapter,imageArrayList);

        listView.setAdapter(historyAdapter);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOperations.deleteData();
                listView.setAdapter(historyAdapter);
            }
        });
    }


}
