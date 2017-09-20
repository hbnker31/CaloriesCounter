package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hemant.caloriescounter.FoodItemDetail;
import com.example.hemant.caloriescounter.R;

import java.util.ArrayList;

import model.*;

/**
 * Created by Hemant on 27-07-2017.
 */

public class CustomListViewAdapter extends ArrayAdapter<Food> {
     private  int layoutResource;
    private Activity activity;
    private ArrayList<Food> foodlist=new ArrayList<>();
    public CustomListViewAdapter(Activity act,int resource,ArrayList<Food> data)
    {
        super(act, resource, data);
        layoutResource=resource;
        activity=act;
        foodlist=data;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return foodlist.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodlist.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        ViewHolder holder=null;
        if(row==null||(row.getTag()==null))
        {
            LayoutInflater inftater=LayoutInflater.from(activity);
            row=inftater.inflate(layoutResource,null);
            holder=new ViewHolder();
            holder.fname=(TextView)row.findViewById(R.id.name);
            holder.fcal=(TextView)row.findViewById(R.id.calories);
            holder.fdate=(TextView)row.findViewById(R.id.dateText);
            row.setTag(holder);
        }
        else {
            holder=(ViewHolder)row.getTag();
        }
        holder.food=getItem(position);
        holder.fname.setText(holder.food.getFoodName());
        holder.fdate.setText(holder.food.getRecordDate());
        holder.fcal.setText(String.valueOf(holder.food.getCalories()));
        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(activity, FoodItemDetail.class);
                Bundle mbundle=new Bundle();
                mbundle.putSerializable("userobj", finalHolder.food);
                i.putExtras(mbundle);

                activity.startActivity(i);

            }
        });







        return row;
    }
    public class ViewHolder{
        Food food;
        TextView fname;
        TextView fcal;
        TextView fdate;
    }
}
