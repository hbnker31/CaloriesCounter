package com.example.hemant.caloriescounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.dataBaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodname,foodcals;
    private Button submit;
    private dataBaseHandler dba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dba=new dataBaseHandler(MainActivity.this);
        foodname=(EditText)findViewById(R.id.foodET);
        foodcals=(EditText)findViewById(R.id.caloriesET);
        submit=(Button)findViewById(R.id.submitB);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        Food food=new Food();
        String name=foodname.getText().toString().trim();
        String calsstring=foodcals.getText().toString().trim();
        int cals=Integer.parseInt(calsstring);
        if(name.equals("")||calsstring.equals(""))
        {
            Toast.makeText(getApplicationContext(),"No Empty Field Allowed!!!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            food.setFoodName(name);
            food.setCalories(cals);
            dba.addFood(food);
            dba.close();

            foodname.setText("");
            foodcals.setText("");
            startActivity(new Intent(MainActivity.this,Display_food.class));
        }
    }
}
