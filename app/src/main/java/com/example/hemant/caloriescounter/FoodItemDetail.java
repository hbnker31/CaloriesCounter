package com.example.hemant.caloriescounter;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.dataBaseHandler;
import model.Food;

public class FoodItemDetail extends AppCompatActivity {
    private TextView foodName,calories,dateTaken;
    private Button share;
    private int foodID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_detail);
        foodName=(TextView)findViewById(R.id.detsFoodName);
        calories=(TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken=(TextView) findViewById(R.id.detsDateText);
        share=(Button) findViewById(R.id.shareB);

        Food food=(Food) getIntent().getSerializableExtra("userobj");
       foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());
        foodID=food.getFoodID();

        calories.setTextSize(34.9f);
        calories.setTextColor(Color.RED);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharecals();
            }
        });

    }
    public void sharecals(){
        StringBuilder dataString=new StringBuilder();
        String name=foodName.getText().toString();
        String cals=calories.getText().toString();
        String date=dateTaken.getText().toString();
        dataString.append("Food: "+name+".\n");
        dataString.append("Calories "+cals+".\n");
        dataString.append("Taken on: "+date+".\n");
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT,"My caloric Intake");
        i.putExtra(Intent.EXTRA_EMAIL,new String[]{"hbnker31@gmail.com"});
        i.putExtra(Intent.EXTRA_TEXT,dataString.toString());
        try{
            startActivity(Intent.createChooser(i,"Send Mail..."));
        }catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Please install Email Client before Sending", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_item_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.deleteItem){
            AlertDialog.Builder alert=new AlertDialog.Builder(FoodItemDetail.this);
            alert.setTitle("Delete?");
            alert.setMessage("Are you sure you want to delete this item?");
            alert.setNegativeButton("No",null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dataBaseHandler dba=new dataBaseHandler(getApplicationContext());
                    dba.Delete(foodID);
                    Toast.makeText(getApplicationContext(),"Food Item Deleted!!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FoodItemDetail.this,Display_food.class));
                    FoodItemDetail.this.finish();
                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}

