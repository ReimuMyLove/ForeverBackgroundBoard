package com.example.shoujiedemo.home.recommen.presenter;

import com.example.shoujiedemo.entity.Content;
import com.example.shoujiedemo.entity.Day;
import com.example.shoujiedemo.home.recommen.model.TimeModel;
import com.example.shoujiedemo.home.recommen.model.TimeModelImpl;
import com.example.shoujiedemo.home.recommen.view.RecommenView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TimePresenterImpl implements TimePresenter,TimePresenterListener{

    private TimeModel model;
    private RecommenView view;

    public TimePresenterImpl(RecommenView view) {
        this.view = view;
        model = new TimeModelImpl();
    }

    @Override
    public void loadByTime(Day day) {
        model.loadByTime(day);
    }

    @Override
    public void onLoadByTimeSuccess(String jsons) {
        List<Content> contents = new ArrayList<>();
        Gson gson = new Gson();
        contents = gson.fromJson(jsons, new TypeToken<List<Content>>() {}.getType());
        String dateInEnglish = null;
        for(Content content:contents){
            String day = content.getTime().substring(8,10);
            String month = content.getTime().substring(5,7);
            String year = content.getTime().substring(0,4);
            switch(month) {
                case "1":
                    dateInEnglish = "January. " + year;
                    break;
                case "2":
                    dateInEnglish = "February. " + year;
                    break;
                case "3":
                    dateInEnglish = "March. " + year;
                    break;
                case "4":
                    dateInEnglish = "April. " + year;
                    break;
                case "5":
                    dateInEnglish = "May. " + year;
                    break;
                case "6":
                    dateInEnglish = "June. " + year;
                    break;
                case "7":
                    dateInEnglish = "July. " + year;
                    break;
                case "8":
                    dateInEnglish = "August. " + year;
                    break;
                case "9":
                    dateInEnglish = "September. " + year;
                    break;
                case "10":
                    dateInEnglish = "October. " + year;
                    break;
                case "11":
                    dateInEnglish = "November. " + year;
                    break;
                case "12":
                    dateInEnglish = "December. " + year;
                    break;
            }
            content.setDateEnglish(dateInEnglish);
            content.setDay(month + "." + day);
        }
        view.loadByTime(contents);
    }

    @Override
    public void onLoadByTimeError() {

    }
}
