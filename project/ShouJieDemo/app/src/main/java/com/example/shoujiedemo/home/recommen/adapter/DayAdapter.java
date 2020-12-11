package com.example.shoujiedemo.home.recommen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Day;
import com.example.shoujiedemo.home.recommen.activity.DateActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends BaseAdapter {

    private Context context;
    private List<Day> monthList = new ArrayList<>();

    public DayAdapter(Context context,List<Day> monthList){
        this.context = context;
        this.monthList = monthList;
    }
    @Override
    public int getCount() {
        return monthList.size();
    }

    @Override
    public Object getItem(int i) {
        return monthList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_date_day, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.day = view.findViewById(R.id.item_date_day);
            viewHolder.month = view.findViewById(R.id.item_date_month);
            viewHolder.item = view.findViewById(R.id.day_item_card);
            view.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder)view.getTag();

        viewHolder.day.setText(new StringBuilder().append(monthList.get(i).getDay()));
        viewHolder.month.setText(monthList.get(i).getMonthInEnglish() +". " + monthList.get(i).getYear() +"");

        return view;
    }

    class ViewHolder{
        TextView day;
        TextView month;
        View item;

    }
}
