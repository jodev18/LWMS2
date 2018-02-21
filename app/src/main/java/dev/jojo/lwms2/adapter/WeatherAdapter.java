package dev.jojo.lwms2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dev.jojo.lwms2.R;
import dev.jojo.lwms2.objects.WeatherObject;

/**
 * Created by myxroft on 20/02/2018.
 */

public class WeatherAdapter extends BaseAdapter {

    private Activity act;
    private List<WeatherObject> wO;

    public WeatherAdapter(Activity actx, List<WeatherObject> wObjs){
        this.act = actx;
        this.wO = wObjs;
    }


    @Override
    public int getCount() {
        return wO.size();
    }

    @Override
    public WeatherObject getItem(int position) {
        return wO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WeatherObject cWObj = wO.get(position);

        if(convertView == null){
            convertView = this.act.getLayoutInflater().inflate(R.layout.list_item_weather,null);
        }

        //Load views
        TextView tvTemp = (TextView)convertView.findViewById(R.id.tvListTempView);
        TextView tvHum = (TextView)convertView.findViewById(R.id.tvListHum);
        TextView tvHPA = (TextView)convertView.findViewById(R.id.tvListHPA);
        TextView tvATM = (TextView)convertView.findViewById(R.id.tvListATM);

        ImageView imRaining = (ImageView)convertView.findViewById(R.id.imgRainStat);
        ImageView imLight = (ImageView)convertView.findViewById(R.id.imgLightStat);

        tvTemp.setText(cWObj.TEMPERATURE);
        tvHum.setText(cWObj.HUMIDITY);
        tvHPA.setText(cWObj.HPA_PRESSURE);
        tvATM.setText(cWObj.ATM_PRESSURE);

        imRaining.setImageDrawable((Integer.parseInt(cWObj.IS_RAIN_STAT) < 3) ? this.act.getDrawable(R.drawable.cloudyrain) :
                this.act.getDrawable(R.drawable.sun_outline));
        imLight.setImageDrawable((Integer.parseInt(cWObj.IS_LIGHT_STAT) == 0) ? this.act.getDrawable(R.drawable.moon) :
                this.act.getDrawable(R.drawable.sun_outline));

        return convertView;

    }
}
