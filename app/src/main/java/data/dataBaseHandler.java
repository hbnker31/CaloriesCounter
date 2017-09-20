package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by Hemant on 27-07-2017.
 */

public class dataBaseHandler extends SQLiteOpenHelper {
    private final ArrayList<Food> foodlist=new ArrayList<>();

    public dataBaseHandler(Context context) {
        super(context,constants.DATABASE_NAME,null,constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE "+constants.TABLE_NAME+"( "+constants.KEY_ID+
                " INTEGER PRIIMARY KEY, "+constants.FOOD_NAME+ " TEXT, "+constants.FOOD_CALORIES_NAME+
                " TEXT,"+constants.DATE_NAME+" LONG);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+constants.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public int getTotalItems(){
        int totalItems=0;
        String query="SELECT * FROM "+constants.TABLE_NAME;
        SQLiteDatabase dba=this.getReadableDatabase();
        Cursor cursor=dba.rawQuery(query,null);
        totalItems=cursor.getCount();
        cursor.close();
        dba.close();
        return totalItems;

    }
    public int TotalCalories(){
        int cals=0;
        SQLiteDatabase dba=this.getReadableDatabase();
        String query="SELECT SUM("+constants.FOOD_CALORIES_NAME+") FROM "
                +constants.TABLE_NAME;
        Cursor cursor=dba.rawQuery(query,null);
        if(cursor.moveToFirst())
            cals=cursor.getInt(0);
        cursor.close();
        dba.close();
        return cals;

    }
    public  void Delete(int id)
    {
        SQLiteDatabase dba=this.getWritableDatabase();
        dba.delete(constants.TABLE_NAME,constants.KEY_ID+"= ?",new String[]{String.valueOf(id)});
        dba.close();
    }
    public void addFood(Food food)
    {
        SQLiteDatabase dba=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(constants.FOOD_NAME,food.getFoodName());
        values.put(constants.FOOD_CALORIES_NAME,food.getCalories());
        values.put(constants.DATE_NAME,System.currentTimeMillis());

        dba.insert(constants.TABLE_NAME,null,values);
        Log.v("Added Food item","YEss");
        dba.close();

    }
    public ArrayList<Food> allFood()
    {
        foodlist.clear();
        SQLiteDatabase dba=this.getReadableDatabase();
        Cursor cursor=dba.query(constants.TABLE_NAME,new String[]{constants.KEY_ID,
                constants.FOOD_NAME,constants.FOOD_CALORIES_NAME,constants.DATE_NAME},
                null,null,null,null,constants.DATE_NAME+" DESC");
        if(cursor.moveToFirst())
        {
            do {
                Food food=new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(constants.FOOD_CALORIES_NAME)));
                food.setFoodID(cursor.getInt(cursor.getColumnIndex(constants.KEY_ID)));

                DateFormat dateFormat=DateFormat.getDateInstance();
                String date=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(constants.DATE_NAME))).getTime());
                food.setRecordDate(date);
                foodlist.add(food);
            }while (cursor.moveToNext());


        }
        cursor.close();
        dba.close();



        return foodlist;
    }


}
