package com.example.shoujiedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoujiedemo.R;
import com.example.shoujiedemo.entity.Set;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends BaseAdapter {

    private List<Set>  setList = new ArrayList<>();
    private Context context;

    public SetAdapter(List<Set> setList, Context context) {
        this.setList = setList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(setList != null){
            return setList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return setList.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_set_selector,viewGroup,false);

        ImageView img = view.findViewById(R.id.set_img);
        TextView title = view.findViewById(R.id.set_title);

        title.setText(setList.get(i).getName());
        Glide.with(context)
                .load( setList.get(i).getPic())
                .into(img);

        return view;
    }
}
