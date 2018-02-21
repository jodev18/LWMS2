package dev.jojo.lwms2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.jojo.lwms2.core.Globals;
import dev.jojo.lwms2.database.WeatherManager;
import dev.jojo.lwms2.objects.WeatherObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WeatherMainActivity extends AppCompatActivity {

    private Handler h;

    static Integer counter = 1;
    Integer totalTemp = 0;
    Integer aveTemp = 0;
    Double totalAirP = 0d;
    Double aveAirP = 0d;
    Integer totalHum = 0;
    Integer aveHum = 0;

    boolean humUp = false;
    boolean humDown = false;
    boolean tempUp = false;
    boolean tempDown = false;
    boolean pressUp = false;
    boolean pressDown = false;



    @BindView(R.id.btnPastRecords) Button bPastRec;
    @BindView(R.id.tvListTempView) TextView tvTemp;
    @BindView(R.id.tvHumidityView) TextView tvHumidity;
    @BindView(R.id.tvAtmVal) TextView tvHPA;
    @BindView(R.id.tvAtmVal2) TextView tvAtm;

    @BindView(R.id.imgRainStat) ImageView imRainStat;
    @BindView(R.id.imgNightDay) ImageView imLightStat;
    @BindView(R.id.tvNightDayStat) TextView tvNightDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        h = new Handler(this.getMainLooper());
//
        ButterKnife.bind(this);
//
//
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fetchLatestWeatherData();
//
//                h.postDelayed(this,10000);
//            }
//        },10000);
//
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },500);

        fetchLatestWeatherData();
        setButtonRecords();

    }

    private void setButtonRecords(){

        bPastRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pRec = new Intent(getApplicationContext(),TemperatureRecords.class);
                startActivity(pRec);
            }
        });
    }

    private Runnable wCheck = new Runnable() {
        @Override
        public void run() {
            try {

                String url = Globals.ROOT_URL + Globals.FETCH_URL;

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.body() != null) {
                    try {

                        final String vars = response.body().string();

                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                //airP.setText(vars);
//                                Toast.makeText(WeatherMainActivity.this, vars, Toast.LENGTH_SHORT).show();
                            }
                        });

                        try{

                            //Process json array
                            JSONArray jsonArray = new JSONArray(vars);

                            final JSONObject jsonObject = jsonArray.getJSONObject(0);

                            final String temp = jsonObject.getString("temp");
                            final String humd = jsonObject.getString("humidity");
                            final String hpa = jsonObject.getString("hpa");
                            final String atm = jsonObject.getString("atm");
                            final String rainstat = jsonObject.getString("is_rain");
                            final String lightstat = jsonObject.getString("is_light");
                            final String timestamp = jsonObject.getString("timestamp");

                            WeatherObject weatherObject = new WeatherObject();

                            weatherObject.TEMPERATURE = temp;
                            weatherObject.HUMIDITY = humd;
                            weatherObject.HPA_PRESSURE = hpa;
                            weatherObject.ATM_PRESSURE = atm;
                            weatherObject.IS_LIGHT_STAT = rainstat;
                            weatherObject.IS_RAIN_STAT = rainstat;
                            weatherObject.TIMESTAMP = timestamp;

                            //Save to database
                            WeatherManager wman = new WeatherManager(WeatherMainActivity.this);

                            if(!wman.isPresent(weatherObject.TIMESTAMP)){

                                long stat = wman.insertWeatherData(weatherObject);

                                if(stat > 0){
                                    Log.d("DB","Saved to database!");
                                }
                                else{
                                    Log.d("DB","Failed to save!");
                                }
                            }
                            else{
                                Log.d("DB","Present in database. not going to insert");
                            }

                            wman.clearDB();

                            final Integer cTemp = Integer.parseInt(temp);
                            final Integer cHum = Integer.parseInt(humd);
                            final Double aPress = Double.parseDouble(hpa);



                            if(aveTemp != null && aveAirP != null){
                                if(aveTemp > 0 && aveAirP > 0){

                                    //Temoerature
                                    h.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(cTemp < aveTemp){
                                                Toast.makeText(WeatherMainActivity.this, "Temperature is decreasing", Toast.LENGTH_SHORT).show();
                                                tempDown = true;
                                                tempUp = false;
                                            }
                                            else if(cTemp == aveTemp){
                                                Toast.makeText(WeatherMainActivity.this,"Temperature is stable.",Toast.LENGTH_SHORT).show();
                                                tempDown = false;
                                                tempUp = false;
                                            }
                                            else{
                                                tempDown = false;
                                                tempUp = true;
                                                Toast.makeText(WeatherMainActivity.this, "Temperature is increasing " + cTemp.toString() + "|" + aveTemp.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    //Air Pressure
                                    h.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(aPress < aveAirP){
                                                pressDown = true;
                                                pressUp = false;
                                                Toast.makeText(WeatherMainActivity.this, "Pressure is decreasing", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(aPress == aveAirP){
                                                pressDown = false;
                                                pressUp = false;
                                                Toast.makeText(WeatherMainActivity.this,"Air pressure is stable.",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                pressUp = true;
                                                pressDown = false;
                                                Toast.makeText(WeatherMainActivity.this, "Pressure is increasing", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    //Humidity
                                    h.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(cHum < aveHum){
                                                humDown = true;
                                                humUp = false;
                                                Toast.makeText(WeatherMainActivity.this, "Humidity is decreasing", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(cHum == aveHum){
                                                humDown = false;
                                                humUp = false;
                                                Toast.makeText(WeatherMainActivity.this,"Humidity is stable.",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                humUp = true;
                                                humDown = false;
                                                Toast.makeText(WeatherMainActivity.this, "Humidity is increasing.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    if(humUp && pressDown){
//                                            wInfer.setText("High chances of rain");
                                    }
                                    else if(pressUp && humDown && tempUp){
//                                            wInfer.setText("High pressure. Less chances of rain.");
                                    }
                                }
                            }

                            totalTemp += cTemp;
                            totalAirP += aPress;
                            totalHum += cHum;

                            aveTemp = totalTemp / counter;
                            aveAirP = totalAirP / counter;
                            aveHum = totalHum / counter;

                            h.post(new Runnable() {
                                @Override
                                public void run() {

//                                        airP.setText(atm + " atm");
//                                        humD.setText(humd + "%");
//                                        temP.setText(temp + " C");
//                                        sunP.setText(lightstat.equals("0") ? "DARK" : "LIGHT");
//                                        isRain.setText(rainstat);

                                    //Set texts
                                    tvTemp.setText(temp);
                                    tvHumidity.setText(humd);
                                    tvHPA.setText(hpa);
                                    tvAtm.setText(atm);

                                    imLightStat.setImageDrawable((lightstat.equals("0") ? getDrawable(R.drawable.moon) :
                                            getDrawable(R.drawable.sun_outline)));

                                    tvNightDay.setText((lightstat.equals("0") ? "Night" :
                                            "Day"));


                                    if(rainstat.equals("3")){

//                                            Toast.makeText(WeatherMainActivity.this, "Dry", Toast.LENGTH_SHORT).show();

//                                            isRain.setText("Dry");

                                        imRainStat.setImageDrawable(getDrawable(R.drawable.sun_outline));

                                    }
                                    else if(rainstat.equals("2")){
//                                            Toast.makeText(WeatherMainActivity.this, "Light rain", Toast.LENGTH_SHORT).show();
//
//                                            isRain.setText("Light Rain");
                                        imRainStat.setImageDrawable(getDrawable(R.drawable.cloudyrain));
                                    }
                                    else if(rainstat.equals("1")){
//                                            Toast.makeText(WeatherMainActivity.this, "Rain", Toast.LENGTH_SHORT).show();
//
//                                            isRain.setText("Heavy Rain");
                                        imRainStat.setImageDrawable(getDrawable(R.drawable.cloudyrain));
                                    }
                                    else{
                                        imRainStat.setImageDrawable(getDrawable(R.drawable.cloudyrain));
//                                            isRain.setText("Heavy Rain");
//                                            Toast.makeText(WeatherMainActivity.this, "Rain", Toast.LENGTH_SHORT).show();
                                    }

//                                        try{
////                                            Toast.makeText(WeatherMainActivity.this, jsonObject.getString("temp"), Toast.LENGTH_SHORT).show();
//                                        }
//                                        catch(JSONException jEx){
////                                            Toast.makeText(WeatherMainActivity.this, "Error retrieving item", Toast.LENGTH_SHORT).show();
//                                        }
                                }
                            });

                            counter++;

                        }
                        catch (JSONException jEx){

                            h.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WeatherMainActivity.this, "There was a problem handling data.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    } catch (IOException ioex) {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WeatherMainActivity.this, "IO Exception encountered.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else {
                    h.post(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(WeatherMainActivity.this, "Response null!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (final IOException ex) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherMainActivity.this, "Exception: " + ex.getMessage() , Toast.LENGTH_SHORT).show();
                        Toast.makeText(WeatherMainActivity.this, "Response null!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    };

    private void fetchLatestWeatherData(){


        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                final Thread weatherCheck = new Thread(wCheck);

                weatherCheck.start();

                h.postDelayed(this,300000);
            }
        },10000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
