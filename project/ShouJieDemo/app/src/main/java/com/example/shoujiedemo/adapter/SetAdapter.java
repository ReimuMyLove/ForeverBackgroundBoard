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
import com.example.shoujiedemo.util.ConfigUtil;

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
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_set_selector,viewGroup,false);

        ImageView img = view.findViewById(R.id.set_img);
        TextView title = view.findViewById(R.id.set_title);
        final View view1 = view.findViewById(R.id.set_rl);


        title.setText(setList.get(i).getName());
        Glide.with(context)
                .load(ConfigUtil.BASE_WENJI_URL + setList.get(i).getPic())
                .into(img);

        if(setList.get(i).isSelect())
            view.setBackgroundColor(0x30CFCFCF);
        else
            view.setBackgroundColor(0xffffff);

        /*view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Set set :setList)
                    set.setSelect(false);

                setList.get(i).setSelect(true);

                notifyDataSetChanged();
            }
        });*/


        return view;
    }
}
