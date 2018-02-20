package dev.jojo.lwms2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.cunoraz.gifview.library.GifView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.jojo.lwms2.core.Globals;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WeatherMainActivity extends AppCompatActivity {

    private Handler h;

//    @BindView(R.id.tvAirPressure) TextView airP;
//    @BindView(R.id.tvHumidity) TextView humD;
//    @BindView(R.id.tvTemp) TextView temP;
//    @BindView(R.id.tvSunPresence) TextView sunP;
//    @BindView(R.id.tvIsRaining) TextView isRain;
//
//    @BindView(R.id.tvWeatherInference) TextView wInfer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        h = new Handler(this.getMainLooper());
//
//        ButterKnife.bind(this);
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



    }

    private void fetchLatestWeatherData(){

        new Thread(new Runnable() {
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
//                                    Toast.makeText(WeatherMainActivity.this, vars, Toast.LENGTH_SHORT).show();
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


                                        if(rainstat.equals("3")){

//                                            Toast.makeText(WeatherMainActivity.this, "Dry", Toast.LENGTH_SHORT).show();

//                                            isRain.setText("Dry");

                                        }
                                        else if(rainstat.equals("w_clouddd")){
//                                            Toast.makeText(WeatherMainActivity.this, "Light rain", Toast.LENGTH_SHORT).show();
//
//                                            isRain.setText("Light Rain");
                                        }
                                        else if(rainstat.equals("1")){
//                                            Toast.makeText(WeatherMainActivity.this, "Rain", Toast.LENGTH_SHORT).show();
//
//                                            isRain.setText("Heavy Rain");
                                        }
                                        else{
//
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

                } catch (final Exception ex) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WeatherMainActivity.this, "Exception: " + ex.getMessage() , Toast.LENGTH_SHORT).show();
                            Toast.makeText(WeatherMainActivity.this, "Response null!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
