package dev.jojo.lwms2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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

    /**
     * fetches all data
     * @return
     */
    public List<WeatherObject> getAllWeatherData(){

        String q = "SELECT * FROM " + WeatherTable.TABLE_NAME
                + " ORDER BY " + WeatherTable.TIMESTAMP + " DESC LIMIT 20";

        this.c = this.sq.rawQuery(q,null);

        if(c.getCount() > 0){

            List<WeatherObject> wObj = new ArrayList<WeatherObject>();

            while(c.moveToNext()){

                WeatherObject cObj = new WeatherObject();

                cObj.ATM_PRESSURE = c.getString(c.getColumnIndex(WeatherTable.ATMOSPHERIC));
                cObj.HPA_PRESSURE = c.getString(c.getColumnIndex(WeatherTable.HPA_PRESSURE));
                cObj.HUMIDITY = c.getString(c.getColumnIndex(WeatherTable.HUMIDITY));
                cObj.IS_LIGHT_STAT = c.getString(c.getColumnIndex(WeatherTable.HAS_LIGHT));
                cObj.TEMPERATURE = c.getString(c.getColumnIndex(WeatherTable.TEMPERATURE));
                cObj.IS_RAIN_STAT = c.getString(c.getColumnIndex(WeatherTable.IS_RAINING));
                cObj.TIMESTAMP = c.getString(c.getColumnIndex(WeatherTable.TIMESTAMP));

                wObj.add(cObj);
            }

            return wObj;

        }

        else{
            return null;
        }

    }

    /**
     * Checks if an entry is present in the database.
     * @param timestamp
     * @return
     */
    public Boolean isPresent(String timestamp){

        String query = "SELECT * FROM " + WeatherTable.TABLE_NAME + " WHERE "
                + WeatherTable.TIMESTAMP + "='" + timestamp + "'";

        this.c = this.sq.rawQuery(query,null);

        return this.c.getCount() > 0;
    }


}
