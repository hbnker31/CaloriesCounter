package com.example.hemant.caloriescounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.dataBaseHandler;
import model.Food;
import util.Util;

public class Display_food extends AppCompatActivity {
    private dataBaseHandler dba;
    private ArrayList<Food> dbfood=new ArrayList<>();
    private CustomListViewAdapter foodAdapter;
    private ListView listView;
    private Food mfood;
    private TextView totalcals,totalfoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);
        listView=(ListView)findViewById(R.id.list);
        totalcals=(TextView) findViewById(R.id.totalAmountTV);
        totalfoods=(TextView)findViewById(R.id.totalItem);
        refreshData();
        }

    private void refreshData() {
        dbfood.clear();
        dba=new dataBaseHandler(getApplicationContext());
        ArrayList<Food> foodfromDB=dba.allFood();
        int calvalue=dba.TotalCalories();
        int totalItems=dba.getTotalItems();

        String formattedValue= Util.formatnumber(calvalue);
        String formatteditems=Util.formatnumber(totalItems);

        totalcals.setText("Total Claories: " +formattedValue);
        totalfoods.setText("Total Items: "+formatteditems);

        for(int i=0;i<foodfromDB.size();i++)
        {
            String name=foodfromDB.get(i).getFoodName();
            String date=foodfromDB.get(i).getRecordDate();
            int cals=foodfromDB.get(i).getCalories();
            int foodid=foodfromDB.get(i).getFoodID();

            mfood=new Food();
            mfood.setFoodID(foodid);
            mfood.setFoodName(name);
            mfood.setCalories(cals);
            mfood.setRecordDate(date);


            dbfood.add(mfood);
        }
        dba.close();

        foodAdapter =new CustomListViewAdapter(Display_food.this,R.layout.list_row,dbfood);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();







    }
}
