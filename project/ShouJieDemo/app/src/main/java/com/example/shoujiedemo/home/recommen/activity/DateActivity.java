package com.example.shoujiedemo.home.recommen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Day;
import com.example.shoujiedemo.entity.Month;
import com.example.shoujiedemo.home.recommen.adapter.DayAdapter;
import com.example.shoujiedemo.home.recommen.adapter.MonthAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateActivity extends AppCompatActivity {

    private RecyclerView monthListView;
    private GridView dayListView;
    private int day;
    private int month;
    private int year;
    private DayAdapter dayAdapter;
    private List<Day> dayList = new ArrayList<>();
    private String monthInEngLish = null;
    private String monthNum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        EventBus.getDefault().register(this);

        monthListView = findViewById(R.id.date_list);
        dayListView = findViewById(R.id.date_grid);

        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        year = calendar.get(Calendar.YEAR);
        //获取当前月
        month = calendar.get(Calendar.MONTH) + 1;
        // 获取当前日
        day = calendar.get(Calendar.DATE);

        List<Month> list = new ArrayList<>();
        dayList = new ArrayList<>();
        for(int i = month;i > 0;--i){
            if(i == month)
                list.add(new Month(true,i + "",year + ""));
            else
                list.add(new Month(false,i + "",year + ""));
        }
        for(int i = 12;i>=1;--i){
            list.add(new Month(false,i + "",(year-1) + ""));
        }
        switch(month){
            case 1:
                monthInEngLish = "Jan";
                monthNum = "1";
                break;
            case 2:
                monthInEngLish = "Feb";
                monthNum = "2";
                break;
            case 3:
                monthInEngLish = "Mar";
                monthNum = "3";
                break;
            case 4:
                monthInEngLish = "Apr";
                monthNum = "4";
                break;
            case 5:
                monthInEngLish = "May";
                monthNum = "5";
                break;
            case 6:
                monthInEngLish = "Jun";
                monthNum = "6";
                break;
            case 7:
                monthInEngLish = "Jul";
                monthNum = "7";
                break;
            case 8:
                monthInEngLish = " Aug";
                monthNum = "8";
                break;
            case 9:
                monthInEngLish = "Sept";
                monthNum = "9";
                break;
            case 10:
                monthInEngLish = "Oct";
                monthNum = "10";
                break;
            case 11:
                monthInEngLish = "Nov";
                monthNum = "11";
                break;
            case 12:
                monthInEngLish = "Dec";
                monthNum = "12";
                break;
        }
        for(int j = day;j>0;--j)
            dayList.add(new Day(j,year + "",monthNum,monthInEngLish));


        dayAdapter = new DayAdapter(this,dayList);
        MonthAdapter monthAdapter = new MonthAdapter(this,list);
        monthListView.setLayoutManager(new LinearLayoutManager(this));
        monthListView.setAdapter(monthAdapter);
        dayListView.setAdapter(dayAdapter);

        dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().postSticky(dayAdapter.getItem(i));
                finish();
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onDayChangeMain(Day selectDay){
        dayList.clear();
        int smonth = Integer.parseInt(selectDay.getMonth());
        int syear = Integer.parseInt(selectDay.getYear());
        switch(smonth){
            case 1:
                monthInEngLish = "Jan";
                monthNum = "1";
                break;
            case 2:
                monthInEngLish = "Feb";
                monthNum = "2";
                break;
            case 3:
                monthInEngLish = "Mar";
                monthNum = "3";
                break;
            case 4:
                monthInEngLish = "Apr";
                monthNum = "4";
                break;
            case 5:
                monthInEngLish = "May";
                monthNum = "5";
                break;
            case 6:
                monthInEngLish = "Jun";
                monthNum = "6";
                break;
            case 7:
                monthInEngLish = "Jul";
                monthNum = "7";
                break;
            case 8:
                monthInEngLish = " Aug";
                monthNum = "8";
                break;
            case 9:
                monthInEngLish = "Sept";
                monthNum = "9";
                break;
            case 10:
                monthInEngLish = "Oct";
                monthNum = "10";
                break;
            case 11:
                monthInEngLish = "Nov";
                monthNum = "11";
                break;
            case 12:
                monthInEngLish = "Dec";
                monthNum = "12";
                break;
        }
        if(smonth == month && syear == year){
            for(int j = day;j>0;--j)
                dayList.add(new Day(j,"" + syear,monthNum,monthInEngLish));
        }else if(smonth == 1 || smonth == 3|| smonth == 5|| smonth == 7|| smonth == 8|| smonth == 10|| smonth == 12){
            for(int j = 31;j >= 1;--j)
                dayList.add(new Day(j,"" + syear,monthNum,monthInEngLish));
        }else if(smonth == 4|| smonth == 6|| smonth == 9|| smonth == 11){
            for(int j = 30;j >= 1;--j)
                dayList.add(new Day(j,"" + syear,monthNum,monthInEngLish));
        }else if(syear % 400 != 0 && syear % 4== 0 && syear % 100 != 0 && smonth == 2){
            for(int j = 29;j >= 1;--j)
                dayList.add(new Day(j,"" + syear,monthNum,monthInEngLish));
        }else {
            for(int j = 28;j >= 1;--j)
                dayList.add(new Day(j,"" + syear,monthNum,monthInEngLish));
        }
        dayAdapter.notifyDataSetChanged();
    }
}