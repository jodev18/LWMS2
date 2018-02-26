package dev.jojo.lwms2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.jojo.lwms2.adapter.WeatherAdapter;
import dev.jojo.lwms2.database.WeatherManager;
import dev.jojo.lwms2.objects.WeatherObject;

public class TemperatureRecords extends AppCompatActivity {

//    @BindView(R.id.lvTempRecords) ListView temperatureRecords;
//    @BindView(R.id.tvEmptyTemp) TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_records);
        setTitle("Temperature Records");


        ButterKnife.bind(this);

//        setListViewAdapter();

        setupTableView();

    }

//    private void setListViewAdapter(){
//
//        WeatherManager wm = new WeatherManager(TemperatureRecords.this);
//
//        List<WeatherObject> objects = wm.getAllWeatherData();
//
//        if(objects != null){
//
//            WeatherAdapter weatherAdapter
//                    = new WeatherAdapter(TemperatureRecords.this,objects);
//
//            temperatureRecords.setAdapter(weatherAdapter);
//
//            emptyList.setVisibility(TextView.GONE);
//
//        }
//        else{
//            emptyList.setVisibility(TextView.VISIBLE);
//        }
//
//    }


    private void setupTableView(){

    }
}
