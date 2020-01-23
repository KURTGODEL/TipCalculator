package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class DBListView extends AppCompatActivity {

    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dblist_view);

        ListView listView = findViewById(R.id.listView);


        DatabaseHelper db = new DatabaseHelper(this);
        Cursor res = db.getAllData();

        ArrayList arrayList = new ArrayList();

        if(res.getCount() == 0){
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        }else {
            while (res.moveToNext()) {
                //arrayList.add(res.getString(1));

                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put(FIRST_COLUMN, res.getString(0));
                hashMap.put(SECOND_COLUMN, res.getString(1));
                hashMap.put(THIRD_COLUMN, res.getString(2));
                hashMap.put(FOURTH_COLUMN, res.getString(3));

                arrayList.add(hashMap);

            }

            ListViewAdapter listViewAdapter = new ListViewAdapter(this, arrayList);

            LayoutInflater inflater = this.getLayoutInflater();
            View headerView = inflater.inflate(R.layout.column_row_header, null);

            listView.addHeaderView(headerView);
            listView.setAdapter(listViewAdapter);


            //ListAdapter listAdapter = new ArrayAdapter<HashMap<String, String>>(this, android.R.layout.simple_list_item_1, arrayList);

            //listView.setAdapter(listAdapter);
        }
    }
}
