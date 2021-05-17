package lk.nibm.lecturerportal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper
{
    public AppDatabase(Context context)
    {
        super( context, "DocAppSQLDB", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( "CREATE TABLE IF NOT EXISTS Channels(monNo Text, yearNo Text, channelType Text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS Channels" );
    }

    public boolean insertData(String monthNo, String yearNo, String channelType)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put( "monNo", monthNo );
        cv.put( "yearNo", yearNo );
        cv.put( "channelType", channelType );

        long result = DB.insert( "Channels", null, cv );

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData(int year)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery( "SELECT monNo, count(yearNo) as tot FROM Channels group by monNo", null );
        return cursor;
    }
}
