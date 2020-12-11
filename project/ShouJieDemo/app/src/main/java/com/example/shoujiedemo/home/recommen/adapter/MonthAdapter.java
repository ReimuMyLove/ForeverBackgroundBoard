package com.example.shoujiedemo.home.recommen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Day;
import com.example.shoujiedemo.entity.Month;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Month> monthList = new ArrayList<>();

    public MonthAdapter(Context context,List<Month> monthList){
        this.context = context;
        this.monthList = monthList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date_month,parent,false);
        ViewHolder viewHold = new ViewHolder(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder)holder;

        if(monthList.get(position).isSelect() ==true)
            viewHolder.left.setVisibility(View.VISIBLE);
        else
            viewHolder.left.setVisibility(View.INVISIBLE);

        viewHolder.month.setText(new StringBuilder(monthList.get(position).getMonth()).append("月"));

        viewHolder.month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.left.setVisibility(View.VISIBLE);

                Day day = new Day();
                day.setYear(monthList.get(position).getYear());
                day.setMonth(monthList.get(position).getMonth());
                EventBus.getDefault().postSticky(day);

                for(Month month :monthList)
                    month.setSelect(false);

                monthList.get(position).setSelect(true);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView left;
        TextView month;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.iv_month_left);
            month = itemView.findViewById(R.id.item_month);

        }
    }
}
