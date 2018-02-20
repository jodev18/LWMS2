package dev.jojo.lwms2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


}
