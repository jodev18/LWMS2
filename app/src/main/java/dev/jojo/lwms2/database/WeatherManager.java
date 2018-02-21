package dev.jojo.lwms2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import dev.jojo.lwms2.objects.WeatherObject;

/**
 * Created by myxroft on 21/02/2018.
 */

public class WeatherManager extends WeatherDB{

    private ContentValues cv;
    private SQLiteDatabase sq;
    private Cursor c;

    public WeatherManager(Context ct) {
        super(ct);

        this.cv = new ContentValues();
        this.sq = getWritableDatabase();
    }

    public void clearDB(){

        if(this.sq != null){
            if(this.sq.isOpen()){
                this.sq.close();
            }
        }

        if(this.c != null){
            if(!this.c.isClosed()){
                this.c.close();
            }
        }

        if(this.cv != null){
            this.cv.clear();
        }
    }

    /**
     * Inserts weather data into the database.
     * @param weatherObject
     * @return
     */
    public long insertWeatherData(WeatherObject weatherObject){

        //To be safe
        this.cv.clear();

        this.cv.put(WeatherTable.ATMOSPHERIC,weatherObject.ATM_PRESSURE);
        this.cv.put(WeatherTable.HAS_LIGHT,weatherObject.IS_LIGHT_STAT);
        this.cv.put(WeatherTable.HPA_PRESSURE,weatherObject.HPA_PRESSURE);
        this.cv.put(WeatherTable.HUMIDITY,weatherObject.HUMIDITY);
        this.cv.put(WeatherTable.IS_RAINING,weatherObject.IS_RAIN_STAT);
        this.cv.put(WeatherTable.TEMPERATURE,weatherObject.TEMPERATURE);
        this.cv.put(WeatherTable.TIMESTAMP,weatherObject.TIMESTAMP);

        long insTat = this.sq.insert(WeatherTable.TABLE_NAME,WeatherTable.ID,this.cv);

        return insTat;
    }

//    public List<WeatherObject> getAllWeatherData(){
//
//        String q = "SELECT * FROM " + WeatherTable.TABLE_NAME
//                + " ORDER BY " + WeatherTable.TIMESTAMP + " DESC";
//
//
//    }


}
