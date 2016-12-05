package homemade.pjmg;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vuljo6rmp on 12/5/16.
 */

public class LocalDBConnection extends SQLiteOpenHelper {

    private String TASK_TABLE_NAME = "task";

    public LocalDBConnection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("SQLITE", "SQL onCreate()");
        String sql;

        //Create task table
        sql = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL,"
                + "activate INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  TASK_TABLE_NAME);
    }
}
