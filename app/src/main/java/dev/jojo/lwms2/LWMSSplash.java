package dev.jojo.lwms2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.cunoraz.gifview.library.GifView;

public class LWMSSplash extends AppCompatActivity {

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lwmssplash);


        h = new Handler(this.getMainLooper());

        AlertDialog.Builder lat = new AlertDialog.Builder(LWMSSplash.this);
        lat.setTitle("Latest");
        lat.setMessage("This is the latest weather debug 8");

        lat.create().show();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent weatherMain = new Intent(getApplicationContext(),WeatherMainActivity.class);

                startActivity(weatherMain);
                finish();

            }
        },5000);

    }
}
