package model;

import java.io.Serializable;

/**
 * Created by Hemant on 27-07-2017.
 */

public class Food implements Serializable{
    private static final long serialVersionUID=10L;
    private String foodName;
    private int calories;
    private  int foodID;
    private String recordDate;
    public Food(String food,int cals,int id, String date)
    {
        foodName=food;
        calories=cals;
        foodID=id;
        recordDate=date;
    }
    public Food(){

    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }


}
