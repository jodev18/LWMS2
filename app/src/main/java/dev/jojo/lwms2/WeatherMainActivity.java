package dev.jojo.lwms2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cunoraz.gifview.library.GifView;

public class WeatherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GifView gifView1 = (GifView)findViewById(R.id.gif1);
        gifView1.setVisibility(View.VISIBLE);
        gifView1.play();
        gifView1.pause();
        gifView1.setGifResource(R.drawable.giphy);
        gifView1.getGifResource();
        gifView1.play();
        gifView1.play();



    }

}
