package dev.jojo.lwms2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by myxroft on 21/02/2018.
 */

public class WeatherDB extends SQLiteOpenHelper {

    public WeatherDB(Context ct){
        super(ct,"weather_db",null,1);
    }

    protected class WeatherTable{
        public static final String TABLE_NAME = "tbl_weather";
        public static final String ID = "_id";
        public static final String TEMPERATURE = "temperature";
        public static final String HUMIDITY = "humidity";
        public static final String HPA_PRESSURE = "hpa_press";
        public static final String ATMOSPHERIC = "atm_press";
        public static final String IS_RAINING = "is_raining";
        public static final String HAS_LIGHT = "has_light";
        public static final String TIMESTAMP = "w_timestamp";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TEMPERATURE + " TEXT,"
                + HUMIDITY + " TEXT,"
                + HPA_PRESSURE + " TEXT,"
                + ATMOSPHERIC + " TEXT,"
                + IS_RAINING + " TEXT,"
                + HAS_LIGHT + " TEXT,"
                + TIMESTAMP + " TEXT);";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(WeatherTable.TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
