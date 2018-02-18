package dev.jojo.lwms2.core;

import android.app.Application;

import dev.jojo.lwms2.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by MAC on 18/02/2018.
 */

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
