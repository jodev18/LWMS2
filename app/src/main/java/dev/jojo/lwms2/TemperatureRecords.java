package dev.jojo.lwms2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemperatureRecords extends AppCompatActivity {

    @BindView(R.id.lvTempRecords) ListView temperatureRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_records);


        ButterKnife.bind(this);

        setListViewAdapter();

    }

    private void setListViewAdapter(){

    }
}
