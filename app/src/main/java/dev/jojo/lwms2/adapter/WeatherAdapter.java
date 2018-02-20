package dev.jojo.lwms2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import dev.jojo.lwms2.objects.WeatherObject;

/**
 * Created by myxroft on 20/02/2018.
 */

public class WeatherAdapter extends BaseAdapter {

    private Activity act;
    private WeatherObject wO;

    public WeatherAdapter(Activity actx, WeatherObject wObj){
        this.act = actx;
        this.wO = wObj;
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
